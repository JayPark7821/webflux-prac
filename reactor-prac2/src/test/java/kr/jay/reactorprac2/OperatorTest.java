package kr.jay.reactorprac2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * OperatorTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class OperatorTest {

	private final Operator operator = new Operator();

	@Test
	void fluxMap() {
		StepVerifier.create(operator.fluxMap())
			.expectNext(10, 20, 30, 40, 50)
			.verifyComplete();
	}

	@Test
	void fluxFilter() {
		StepVerifier.create(operator.fluxFilter())
			.expectNext(6,7,8,9,10)
			.verifyComplete();
	}

	@Test
	void fluxFilterTake() {
		StepVerifier.create(operator.fluxFilterTake())
			.expectNext(6,7,8)
			.verifyComplete();
	}

	@Test
	void fluxFlatMap() {
		StepVerifier.create(operator.fluxFlatMap())
			.expectNextCount(100)
			.verifyComplete();
	}
	@Test
	void fluxFlatMap2() {
		StepVerifier.create(operator.fluxFlatMap2())
			.expectNextCount(81)
			.verifyComplete();
	}
}