package kr.jay.reactorprac2;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Operator3
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public class Operator3 {
	// count
	public Mono<Long> fluxCount() {
		return Flux.range(1, 10)
			.count()
			.log();
	}

	// distinct
	public Flux<String> fluxDistinct(){
		return Flux.fromIterable(List.of("a", "b", "c", "d", "a","b"))
			.distinct()
			.log();
	}

	// reduce
	public Mono<Integer> fluxReduce(){
		return Flux.range(1, 10)
			.reduce(Integer::sum)
			.log();
	}


	// groupby
	public Flux<Integer> fluxGroupBy(){
		return Flux.range(1, 10)
			.groupBy(i -> i % 2 == 0 ? "even" : "odd")
			.flatMap(group -> group.reduce(Integer::sum))
			.log();
	}
}
