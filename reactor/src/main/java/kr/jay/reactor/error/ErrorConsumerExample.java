package kr.jay.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * ErrorConsumerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class ErrorConsumerExample {
	public static void main(String[] args) {
		Flux.error(new RuntimeException("error"))
			.subscribe(
				value -> log.info("value : {}", value),
				error -> log.error("error : " , error));
	}
}
