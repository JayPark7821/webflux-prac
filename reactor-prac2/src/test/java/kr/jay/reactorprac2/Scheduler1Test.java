package kr.jay.reactorprac2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Scheduler1Test
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class Scheduler1Test {

	private final Scheduler1 scheduler1 = new Scheduler1();

	@Test
	void fluxMapWithSubscription() {
		StepVerifier.create(scheduler1.fluxMapWithSubscription())
			.expectNextCount(10)
			.verifyComplete();
	}

	@Test
	void fluxMapWithPublishOn() {
		StepVerifier.create(scheduler1.fluxMapWithPublishOn())
			.expectNextCount(10)
			.verifyComplete();
	}
}