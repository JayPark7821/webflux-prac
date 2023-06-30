package kr.jay.prac1;

import static kr.jay.prac1.FixedIntPublisher.*;

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
		Flow.Publisher<Result> publisher = new FixedIntPublisher();
		Flow.Subscriber<Result> subscriber= new RequestNSubscriber<>(4);

		publisher.subscribe(subscriber);

		Thread.sleep(100);
	}
}
