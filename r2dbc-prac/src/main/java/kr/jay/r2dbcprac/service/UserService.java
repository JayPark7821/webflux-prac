package kr.jay.r2dbcprac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import kr.jay.r2dbcprac.common.EmptyImage;
import kr.jay.r2dbcprac.common.Image;
import kr.jay.r2dbcprac.common.User;
import kr.jay.r2dbcprac.repository.UserReactorRepository;
import reactor.core.publisher.Mono;

/**
 * UserService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */
@Component
public class UserService {
	private WebClient webClient = WebClient.create("http://localhost:8081");
	private final UserReactorRepository userRepository = new UserReactorRepository();

	public Mono<User> findById(String userId) {
		return userRepository.findById(userId)
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
						return new User(
							userEntity.getId().toString(),
							userEntity.getName(),
							userEntity.getAge(),
							profileImage,
							List.of(),
							0L
						);
					});

				}
			);
	}
}
