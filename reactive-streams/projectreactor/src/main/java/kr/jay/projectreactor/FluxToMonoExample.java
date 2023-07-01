package kr.jay.projectreactor;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * FluxToMonoExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */

@Slf4j
public class FluxToMonoExample {
	public static void main(String[] args) {
		log.info("start");
		Mono.from(getItems())
			.subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
		log.info("end");
	}

	private static Flux<Integer> getItems(){
		return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
	}
}
