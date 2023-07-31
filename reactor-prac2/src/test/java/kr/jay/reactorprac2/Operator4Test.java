package kr.jay.reactorprac2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Operator4Test
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class Operator4Test {

	private final Operator4 operator4 = new Operator4();
	@Test
	void fluxDelayAndLimit() {
		StepVerifier.create(operator4.fluxDelayAndLimit())
			.expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
			.verifyComplete();
	}

	 @Test
	void fluxSample() {
		StepVerifier.create(operator4.fluxSample())
			.expectNextCount(1000)
			.verifyComplete();
	}
}