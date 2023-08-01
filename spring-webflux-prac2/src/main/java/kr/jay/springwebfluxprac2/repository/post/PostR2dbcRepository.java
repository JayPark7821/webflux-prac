package kr.jay.springwebfluxprac2.repository.post;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

/**
 * PostR2dbcRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long>, PostCustomR2dbcRepository {
	Flux<Post> findAllByUserId(Long userId);
}
