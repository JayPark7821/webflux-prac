package kr.jay.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * ErrorNoHandlerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class ErrorNoHandlerExample {
	public static void main(String[] args) {
		Flux.create(sink -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			sink.error(new RuntimeException("error"));
		}).subscribe();
	}
}
