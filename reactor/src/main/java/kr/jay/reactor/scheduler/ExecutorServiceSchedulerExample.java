package kr.jay.reactor.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * ImmediateSchedulerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/11
 */

@Slf4j
public class ExecutorServiceSchedulerExample {
	@SneakyThrows
	public static void main(String[] args) {
		log.info("start main");
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 100; i++) {
			final int idx = i;
			Flux.create(sink -> {
				log.info("next : {}", idx);
				sink.next(idx);
			}).subscribeOn(
				Schedulers.fromExecutorService(executor)
			).subscribe(value -> {
				log.info("value : {}", value);
			});
		}
		Thread.sleep(100);
		executor.shutdown();
		log.info("end main");
	}
}
