package kr.jay.reactorprac2;

import java.time.Duration;

import reactor.core.publisher.Flux;

/**
 * Operator4
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public class Operator4 {

	// limit
	public Flux<Integer> fluxDelayAndLimit(){
		return Flux.range(1, 10)
			.delaySequence(Duration.ofSeconds(1))
			.log()
			.limitRate(2);
	}

	// sample
	public  Flux<Integer> fluxSample(){
		return Flux.range(1, 100)
			.delayElements(Duration.ofMillis(100))
			.sample(Duration.ofMillis(300))
			.log();
	}
}
