package kr.jay.projectreactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * ContinuousRequestSubscriber
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/30
 */

@Slf4j
public class ContinuousRequestSubscriber<T> implements Subscriber<T> {
	private final Integer count = 1;
	private Subscription subscription = null;

	@Override
	public void onSubscribe(final Subscription s) {
		this.subscription = s;
		log.info("subscribe");
		s.request(count);
		log.info("request count: {}", count);
	}

	@SneakyThrows
	@Override
	public void onNext(final T t) {
		log.info("onNext: {}", t);

		Thread.sleep(1000);
		subscription.request(count);
		log.info("request count: {}", count);
	}

	@Override
	public void onError(final Throwable t) {

	}

	@Override
	public void onComplete() {

	}
}
