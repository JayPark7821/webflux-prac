package kr.jay.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * ContextWriteExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class ContextWriteExample {
	public static void main(String[] args) {
		Flux.just(1)
			.flatMap(value -> ContextLogger.logContext(value, "1"))
			.contextWrite(context -> context.put("name", "jay"))
			.flatMap(value -> ContextLogger.logContext(value, "2"))
			.contextWrite(context -> context.put("name", "park"))
			.flatMap(value -> ContextLogger.logContext(value, "3"))
			.subscribe();
	}
}
