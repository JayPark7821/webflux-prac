package kr.jay.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

/**
 * ContextWriteExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class ContextInitExample {
	public static void main(String[] args) {
		final Context initialContext = Context.of("name", "jay");

		Flux.just(1)
			.flatMap(value -> ContextLogger.logContext(value, "1"))
			.contextWrite(context -> context.put("name", "park"))
			.flatMap(value -> ContextLogger.logContext(value, "2"))
			.subscribe(null, null, null, initialContext);
	}
}
