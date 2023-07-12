package kr.jay.reactor.error;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * OnErrorMapExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class OnErrorMapExample {

	private static class CustomBusinessException extends RuntimeException {
		public CustomBusinessException(String message) {
			super(message);
		}
	}

	public static void main(String[] args) {
		log.info("start main");
		Flux.error(new IOException("fail to read file"))
			.onErrorMap(e -> new CustomBusinessException("custom"))
			.subscribe(
				value -> log.info("value : {}", value),
				error -> log.error("error : " , error)
			);
		log.info("end main");
	}
}
