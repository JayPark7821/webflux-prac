package kr.jay.projectreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * FluxNoSubscriveExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */

@Slf4j
public class FluxNoSubscribeExample {

	public static void main(String[] args) {
		log.info("start");
		getItems();
		log.info("end");
	}

	private static Flux<Integer> getItems(){
		return Flux.create(fluxSink -> {
			log.info("start getItems");
			for (int i = 0; i < 5; i++) {
				fluxSink.next(i);
			}
			fluxSink.complete();
			log.info("end getItems");
		});
	}
}
