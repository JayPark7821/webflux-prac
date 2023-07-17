package kr.jay.webflux.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import kr.jay.webflux.common.User;
import kr.jay.webflux.repository.UserReactorRepository;
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

	private final UserReactorRepository userRepository = new UserReactorRepository();

	public Mono<User> findById(String userId) {
		return userRepository.findById(userId)
			.map(userEntity -> new User(
					userEntity.getId(),
					userEntity.getName(),
					userEntity.getAge(),
					Optional.empty(),
					List.of(),
					0L
				)
			);
	}
}
