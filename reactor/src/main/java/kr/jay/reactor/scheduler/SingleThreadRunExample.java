package kr.jay.reactor.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * SingleThreadRunExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/11
 */

@Slf4j
public class SingleThreadRunExample {

	public static void main(String[] args) {
		log.info("start main");
		final ExecutorService executor = Executors.newSingleThreadExecutor();

		try{
			executor.submit(() -> {
				Flux.create(sink -> {
					for (int i = 1; i < 5; i++) {
						log.info("next : {}", i);
						sink.next(i);
					}
				}).subscribe(value -> log.info("value : {}", value));
			});
		}finally {
			executor.shutdown();
		}
		log.info("end main");
	}
}
