package kr.jay.reactor.error;

import java.util.function.Function;

import org.reactivestreams.Publisher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * OnErrorResumeExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class OnErrorResumeExample {
	public static void main(String[] args) {
		Flux.error(new RuntimeException("error"))
			.onErrorResume(new Function<Throwable, Publisher<?>>() {
				@Override
				public Publisher<?> apply(final Throwable throwable) {
					return Flux.just(0, -1, -2);
				}
			})
			.subscribe(value -> log.info("value : {}", value));
	}
}
