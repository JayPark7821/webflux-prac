package kr.jay.projectreactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * SimpleSubscriber
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleSubscriber<T> implements Subscriber<T> {

	private final Integer count;

	@Override
	public void onSubscribe(final Subscription s) {
		log.info("subscribed");
		s.request(count);
		log.info("request count: {}", count);
	}

	@SneakyThrows
	@Override
	public void onNext(final T t) {
		log.info("onNext: {}", t);
		Thread.sleep(100);

	}

	@Override
	public void onError(final Throwable t) {
		log.error("error: {}", t.getMessage());
	}

	@Override
	public void onComplete() {
		log.info("complete");
	}
}
