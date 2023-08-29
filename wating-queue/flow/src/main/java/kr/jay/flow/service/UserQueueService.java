package kr.jay.flow.service;

import static kr.jay.flow.exception.ErrorCode.*;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * UserQueueService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueueService {
	private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
	private static final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait";
	private static final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "users:queue:*:wait";
	private static final String USER_QUEUE_PROCEED_KEY = "users:queue:%s:proceed";

	@Value("${scheduler.enable}")
	private Boolean scheduling = false;

	// 대기열 등록 API
	public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
		final long timeStamp = Instant.now().getEpochSecond();
		return reactiveRedisTemplate.opsForZSet()
			.add(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString(), timeStamp)
			.filter(i -> i)
			.switchIfEmpty(Mono.error(QUEUE_ALREADY_REGISTERED_USER.build()))
			.flatMap(
				i -> reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString()))
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

	public Mono<Boolean> isAllowed(final String queue, final Long userId) {
		return reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_PROCEED_KEY.formatted(queue), userId.toString())
			.defaultIfEmpty(-1L)
			.map(rank -> rank >= 0);
	}

	public Mono<Long> getRank(final String queue, final Long userId) {
		return reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString())
			.defaultIfEmpty(-1L)
			.map(rank -> rank >= 0 ? rank + 1 : rank);
	}

	@Scheduled(initialDelay = 5000, fixedDelay = 3000L)
	public void scheduleAllowUser() {
		if(!scheduling){
			log.info("scheduler is disabled");
			return;
		}

		log.info("called schedule....");
		final long maxAllowUserCount = 3L;
		reactiveRedisTemplate.scan(ScanOptions.scanOptions()
				.match(USER_QUEUE_WAIT_KEY_FOR_SCAN)
				.count(100)
				.build())
			.map(key -> key.split(":")[2])
			.flatMap(queue -> allowUser(queue, maxAllowUserCount).map(allowed -> Tuples.of(queue, allowed)))
			.doOnNext(tuple -> log.info("Tried %d and allowed %d members of %s queue".formatted(maxAllowUserCount, tuple.getT2(), tuple.getT1())))
			.subscribe();
	}

}
