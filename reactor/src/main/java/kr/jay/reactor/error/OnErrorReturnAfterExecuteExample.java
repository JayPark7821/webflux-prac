package kr.jay.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * OnErrorReturnAfterExecuteExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class OnErrorReturnAfterExecuteExample {
	public static void main(String[] args) {
		log.info("start main");
		Flux.just(1)
			.onErrorReturn(shouldDoOnError())
			.subscribe(value-> log.info("value : {}", value));

		log.info("end main");
	}

	private static int shouldDoOnError() {
		log.info("shouldDoOnError");
		return 0;
	}
}
