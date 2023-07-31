package kr.jay.reactorprac2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * PublisherTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class PublisherTest {

	private final Publisher publisher = new Publisher();
	@Test
	void startFlux() {
		StepVerifier.create(publisher.startFlux())
			.expectNext(1,2,3,4,5,6,7,8,9,10)
			.verifyComplete();
	}

	@Test
	void startFlux2() {
		StepVerifier.create(publisher.startFlux2())
			.expectNext("a","b","c","d")
			.verifyComplete();
	}

	@Test
	void startMono() {
		StepVerifier.create(publisher.startMono())
			.expectNext(1)
			.verifyComplete();
	}

	@Test
	void startMono2() {
		StepVerifier.create(publisher.startMono2())
			.verifyComplete();
	}

	@Test
	void startMono3() {
		StepVerifier.create(publisher.startMono3())
			.expectError(Exception.class)
			.verify();
	}
}