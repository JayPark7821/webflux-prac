package kr.jay.reactivestreams;

import java.util.concurrent.Flow;

/**
 * Client
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/29
 */
public class Client {

	public static void main(String[] args) throws InterruptedException {
		Flow.Publisher publisher = new FixedIntPublisher();
		Flow.Subscriber subscriber= new RequestNSubscriber<>(4);

		publisher.subscribe(subscriber);

		Thread.sleep(100);
	}
}
