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
public class ContextReadFromSinkExample {
	public static void main(String[] args) {
		final Context initialContext = Context.of("name", "jay");

		Flux.create(sink ->{
			final String name = sink.contextView().get("name");
			log.info("name : {}", name);
			sink.next(1);
		})
			.contextWrite(context -> context.put("name", "park"))
			.subscribe(null,null,null, initialContext);
	}
}
