package kr.jay.reactor.combination;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * CacheExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class CacheExample {

	public static void main(String[] args) {
		Flux<Object> flux = Flux.create(sink -> {
			for (int i = 0; i < 3; i++) {
				log.info("next : {}", i);
				sink.next(i);
			}
			log.info("complete in publisher");
			sink.complete();
		}).cache();

		flux.subscribe(
			value -> log.info("value : {}", value),
			null,
			() -> log.info("complete")
		);

		flux.subscribe(
			value -> log.info("value : {}", value),
			null,
			() -> log.info("complete")
		);
	}
}
