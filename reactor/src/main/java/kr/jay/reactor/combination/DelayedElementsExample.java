package kr.jay.reactor.combination;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * DelayedElementsExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class DelayedElementsExample {
	public static void main(String[] args) throws InterruptedException {
		Flux.create(sink -> {
				for (int i = 1; i <= 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					sink.next(i);
				}
				sink.complete();
			})
			.delayElements(Duration.ofMillis(500))
			.doOnNext(value -> log.info("doOnNext : {}", value))
			.subscribeOn(Schedulers.single())
			.subscribe();
		Thread.sleep(6000);
	}
}
