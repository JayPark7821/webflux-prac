package kr.jay.springwebfluxprac2.repository.user;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public interface UserRepository {
	Mono<User> save(User user);
	Flux<User> findAll();
	Mono<User> findById(Long id);
	Mono<Integer> deleteById(Long id);
}
