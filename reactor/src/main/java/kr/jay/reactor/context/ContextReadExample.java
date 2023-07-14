package kr.jay.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ContextReadExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/12
 */
@Slf4j
public class ContextReadExample {
	public static void main(String[] args) {
		Flux.just(1)
			.flatMap(value -> {
				return Mono.deferContextual(contextView -> {
					final String name = contextView.get("name");
					log.info("name : {}", name);
					return Mono.just(value);
				});
			}).contextWrite(context -> context.put("name", "jay"))
			.subscribe();
	}
}
