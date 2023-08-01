package kr.jay.springwebfluxprac2.repository.post;

import reactor.core.publisher.Flux;

/**
 * PostCustomR2dbcRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
public interface PostCustomR2dbcRepository {

	Flux<Post> findAllByUserId(Long userId);
}
