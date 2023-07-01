package kr.jay.projectreactor;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * MonoToFluxExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */

@Slf4j
public class ListMonoToFluxExample {

	public static void main(String[] args) {
		log.info("start");
		getItem()
			.flatMapMany(value-> Flux.fromIterable(value))
			.subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
		log.info("end");
	}

	private static Mono<Iterable<Integer>> getItem(){
		return Mono.just(List.of(1,2,3,4,5));
	}
}
