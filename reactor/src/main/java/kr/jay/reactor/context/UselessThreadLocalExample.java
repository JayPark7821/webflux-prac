package kr.jay.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * UselessThreadLocalExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class UselessThreadLocalExample {
	public static void main(String[] args) throws InterruptedException {
		final ThreadLocal<String> threadLocal = new ThreadLocal<>();
		threadLocal.set("hello");

		Flux.create(sink -> {
				log.info("threadLocal : {}", threadLocal.get());
				sink.next(1);
			})
			.publishOn(Schedulers.parallel())
			.map(value -> {
				log.info("threadLocal : {}", threadLocal.get());
				return value;
			}).publishOn(Schedulers.boundedElastic())
			.map(value -> {
				log.info("threadLocal : {}", threadLocal.get());
				return value;
			}).subscribeOn(Schedulers.single())
			.subscribe();
		Thread.sleep(1000);
	}
}
