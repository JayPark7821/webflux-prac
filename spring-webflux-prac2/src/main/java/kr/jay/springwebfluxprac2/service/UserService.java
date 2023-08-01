package kr.jay.springwebfluxprac2.service;

import org.springframework.stereotype.Service;

import kr.jay.springwebfluxprac2.repository.user.User;
import kr.jay.springwebfluxprac2.repository.user.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserR2dbcRepository userRepository;

	public Mono<User> save(final String name , final String email){
		return userRepository.save(
			User.builder()
			.name(name)
			.email(email)
			.build()
		);
	}

	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<User> findById(final Long id) {
		return userRepository.findById(id);
	}

	public Mono<Void> deleteById(final Long id) {
		return userRepository.deleteById(id);
	}

	public Mono<User> update(final Long id, final String name, final String email) {
		return userRepository.findById(id)
			.flatMap(user -> {
				user.setName(name);
				user.setEmail(email);
				return userRepository.save(user);
			});
	}
}
