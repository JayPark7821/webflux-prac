package kr.jay.reactor.combination;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * FluxConcatExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class FluxConcatExample {
	public static void main(String[] args) throws InterruptedException {
		var flux1 = Flux.range(1, 3)
			.doOnSubscribe(subscription -> log.info("subscribe flux1"))
			.delayElements(Duration.ofMillis(100));
		var flux2 = Flux.range(10, 3)
			.doOnSubscribe(subscription -> log.info("subscribe flux2"))
			.delayElements(Duration.ofMillis(100));

		Flux.concat(flux1, flux2)
			.doOnNext(value -> log.info("doOnNext : {}", value))
			.subscribe();

		Thread.sleep(1000);
	}
}
