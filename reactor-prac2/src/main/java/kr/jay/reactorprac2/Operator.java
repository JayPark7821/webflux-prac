package kr.jay.reactorprac2;

import java.time.Duration;

import reactor.core.publisher.Flux;

/**
 * Operator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public class Operator {
	//map
	public Flux<Integer> fluxMap(){
		return Flux.range(1, 5)
			.map(i -> i * 10)
			.log();
	}

	//filter
	public Flux<Integer> fluxFilter(){
		return Flux.range(1, 10)
			.filter(i -> i > 5)
			.log();
	}

	//take
	public Flux<Integer> fluxFilterTake(){
		return Flux.range(1, 10)
			.filter(i -> i > 5)
			.take(3)
			.log();
	}

	//flatMap
	public Flux<Integer> fluxFlatMap(){
		return Flux.range(1, 10)
			.flatMap(i -> Flux.range(i * 10, 10)
				.delayElements(Duration.ofMillis(100)))
			.log();
	}

	//flatMap2
	public Flux<Integer> fluxFlatMap2(){
		// for (int i = 0 ......)
		// 		for (int j = 0 ......)
		return Flux.range(1, 9)
			.flatMap(i -> Flux.range(1, 9)
				.map(j -> {
					System.out.printf("%d * %d = %d\n", i, j, (i * j));
					return i * j;
				}));
	}
}
