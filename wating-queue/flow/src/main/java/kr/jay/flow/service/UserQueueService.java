package kr.jay.flow.service;

import static kr.jay.flow.exception.ErrorCode.*;

import java.time.Instant;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * UserQueueService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */

@Service
@RequiredArgsConstructor
public class UserQueueService {
	private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
	private static final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait";
	private static final String USER_QUEUE_PROCEED_KEY = "users:queue:%s:proceed";

	// 대기열 등록 API
	public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
		final long timeStamp = Instant.now().getEpochSecond();
		return reactiveRedisTemplate.opsForZSet().add(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString(), timeStamp)
			.filter(i -> i)
			.switchIfEmpty(Mono.error(QUEUE_ALREADY_REGISTERED_USER.build()))
			.flatMap(i -> reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString()))
			.map(i -> i >= 0 ? i + 1 : i);

	}

	// 진입 허용
	public Mono<Long> allowUser(final String queue, final Long count) {
		return reactiveRedisTemplate.opsForZSet().popMin(USER_QUEUE_WAIT_KEY.formatted(queue), count)
			.flatMap(member -> reactiveRedisTemplate.opsForZSet()
				.add(
					USER_QUEUE_PROCEED_KEY.formatted(queue),
					member.getValue(),
					Instant.now().getEpochSecond()
				)
			).count();
	}


}
