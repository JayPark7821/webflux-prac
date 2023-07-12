package kr.jay.reactor.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * SingleSchedulerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/11
 */
@Slf4j
public class SingleSchedulerExample {
	@SneakyThrows
	public static void main(String[] args) {
		log.info("start main");
		for (int i = 0; i < 10; i++) {
			final int idx = i;
			Flux.create(sink -> {
				log.info("next : {}", idx);
				sink.next(idx);
			}).subscribeOn(
				Schedulers.single()
			).subscribe(value -> log.info("value : {}", value));
		}
		Thread.sleep(100);
		log.info("end main");
	}
}
