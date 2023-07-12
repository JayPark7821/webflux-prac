package kr.jay.reactor.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * PublishOnSchedulerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/11
 */

@Slf4j
public class PublishOnSchedulerExample {
	@SneakyThrows
	public static void main(String[] args) {
		Flux.create(sink->{
			for (int i = 0; i < 5; i++) {
				log.info("next : {}", i);
				sink.next(i);
			}
		}).publishOn(Schedulers.single())
			.doOnNext(item -> log.info("doOnNext : {}", item))
			.publishOn(Schedulers.boundedElastic())
			.doOnNext(item -> log.info("doOnNext2 : {}", item))
			.subscribe(value -> log.info("value : {}", value));

		Thread.sleep(100);
	}
}
