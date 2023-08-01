package kr.jay.springwebfluxprac2.repository.user;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserR2dbcRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
	Flux<User> findByName(String name);
	Flux<User> findByNameOrderByIdDesc(String name);

	@Modifying
	@Query("delete from user where name = :name")
	Mono<Void> deleteByName(String name);


}
