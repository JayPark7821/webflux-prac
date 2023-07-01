package kr.jay.projectreactor;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * FluxSimpleExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */

@Slf4j
public class FluxSimpleExample {

	@SneakyThrows
	public static void main(String[] args) {
		log.info("start");
		getItems()
			// .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
			.map(i -> {
				log.info("map {}", i);
				return i;
			})
			.subscribeOn(Schedulers.single())
			.subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
		log.info("end");

		Thread.sleep(1000);
	}

	private static Flux<Integer> getItems(){
		return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
	}
}
