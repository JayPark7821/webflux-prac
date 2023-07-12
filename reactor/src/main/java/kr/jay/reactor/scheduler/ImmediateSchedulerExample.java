package kr.jay.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * ImmediateSchedulerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/11
 */

@Slf4j
public class ImmediateSchedulerExample {
	public static void main(String[] args) {
		Flux.create(sink -> {
			for (int i = 1; i <= 5; i++) {
				log.info("next : {}", i);
				sink.next(i);
			}
		}).subscribeOn(
			Schedulers.immediate()
		).subscribe(value->log.info("value : {}", value));
	}
}
