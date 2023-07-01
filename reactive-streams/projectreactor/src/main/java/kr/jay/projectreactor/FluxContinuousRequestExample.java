package kr.jay.projectreactor;

import java.util.List;

import reactor.core.publisher.Flux;

/**
 * FluxSimpleRequestThreeExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */
public class FluxContinuousRequestExample {

	public static void main(String[] args) {
		getItems()
			.subscribe(new ContinuousRequestSubscriber<>());
	}

	private static Flux<Integer> getItems(){
		return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
	}
}
