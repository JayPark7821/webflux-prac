package kr.jay.r2dbcprac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import kr.jay.r2dbcprac.common.EmptyImage;
import kr.jay.r2dbcprac.common.Image;
import kr.jay.r2dbcprac.common.User;
import kr.jay.r2dbcprac.common.repository.AuthEntity;
import kr.jay.r2dbcprac.common.repository.UserEntity;
import kr.jay.springcoroutineprac.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * UserService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private WebClient webClient = WebClient.create("http://localhost:8081");
	private final UserR2dbcRepository userRepository ;
	private final R2dbcEntityTemplate r2dbcEntityTemplate;

	public Mono<User> findById(String userId) {
		return userRepository.findById(Long.parseLong(userId))
			.flatMap(userEntity -> {
					final String imageId = userEntity.getProfileImageId();
				return webClient.get().uri("/api/images/{imageId}" , imageId)
					.retrieve()
					.toEntity(ImageResponse.class)
					.map(response -> response.getBody())
					.map(imageResponse -> new Image(
						imageResponse.getId(),
						imageResponse.getName(),
						imageResponse.getUrl()
					)).switchIfEmpty(Mono.just(new EmptyImage()))
					.map(image -> {
						  Optional<Image> profileImage = Optional.empty();
						if(!(image instanceof EmptyImage))
							profileImage = Optional.of(image);
						return map(userEntity, profileImage);
					});

				}
			);
	}

	@Transactional
	public Mono<User> createUser(
		final String name,
		final Integer age,
		final String profileImageId,
		final String password
	){

		final UserEntity newUser = new UserEntity(name, age, profileImageId, password);

		return userRepository.save(newUser)
			.flatMap(userEntity -> r2dbcEntityTemplate.insert(new AuthEntity(newUser.getId(), generateRandomToken()))
				.map(authEntity -> userEntity )
			)
			.map(entity -> map(entity, Optional.of(new EmptyImage())));
	}

	private String generateRandomToken() {
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			char item = (char)('A' + (Math.random() * 26));
			token.append(item);
		}
		return token.toString();
	}

	private User map(UserEntity entity,   Optional<Image> profileImage) {
		return new User(
			entity.getId().toString(),
			entity.getName(),
			entity.getAge(),
			profileImage,
			List.of(),
			0L
		);
	}
}
