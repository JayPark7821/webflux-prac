package kr.jay.reactorprac2;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Publisher
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public class Publisher {
	public Flux<Integer> startFlux() {
		return Flux.range(1, 10).log();
	}

	public Flux<String> startFlux2() {
		return Flux.fromIterable(List.of("a","b","c","d")).log();
	}

	public Mono<Integer> startMono() {
		return Mono.just(1).log();
	}

	public Mono<?> startMono2() {
		return Mono.empty().log();
	}

	public Mono<?> startMono3() {
		return Mono.error(new Exception("hello reactor")).log();
	}
}
