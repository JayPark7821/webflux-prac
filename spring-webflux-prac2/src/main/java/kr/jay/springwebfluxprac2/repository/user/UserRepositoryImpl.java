package kr.jay.springwebfluxprac2.repository.user;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserRepositoryImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
	private final ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();
	private AtomicLong sequence = new AtomicLong(1L);

	@Override
	public Mono<User> save(final User user) {
		final LocalDateTime now = LocalDateTime.now();
		if (user.getId() == null) {
			user.setId(sequence.getAndAdd(1));
			user.setCreatedAt(now);
		}
		user.setUpdatedAt(now);
		userMap.put(user.getId(), user);
		return Mono.just(user);
	}

	@Override
	public Flux<User> findAll() {
		return Flux.fromIterable(userMap.values());
	}

	@Override
	public Mono<User> findById(final Long id) {
		return Mono.justOrEmpty(userMap.getOrDefault(id, null));
	}

	@Override
	public Mono<Integer> deleteById(final Long id) {
		final User user = userMap.getOrDefault(id,null);
		if (user == null) {
			return Mono.just(0);
		}
		userMap.remove(id);
		return Mono.just(1);
	}
}
