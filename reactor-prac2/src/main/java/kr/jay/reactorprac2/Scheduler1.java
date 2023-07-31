package kr.jay.reactorprac2;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Scheduler1
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public class Scheduler1 {
	public Flux<Integer> fluxMapWithSubscription(){
		return Flux.range(1, 10)
			.map(i -> i * 2)
			.subscribeOn(Schedulers.boundedElastic())
			.log();
	}

	public Flux<Integer> fluxMapWithPublishOn(){
		return Flux.range(1, 10)
			.map(i -> i + 1)
			.log()
			.publishOn(Schedulers.boundedElastic())
			.log()
			.map(i -> i * 2);
	}
}
