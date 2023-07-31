package kr.jay.reactorprac2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Operator3Test
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class Operator3Test {

	private final Operator3 operator3 = new Operator3();

	@Test
	void fluxCount() {
		StepVerifier.create(operator3.fluxCount())
			.expectNext(10L)
			.verifyComplete();
	}

	@Test
	void fluxDistinct() {
		StepVerifier.create(operator3.fluxDistinct())
			.expectNext("a")
			.expectNext("b")
			.expectNext("c")
			.expectNext("d")
			.verifyComplete();
	}

	@Test
	void fluxReduce() {
		StepVerifier.create(operator3.fluxReduce())
			.expectNext(55)
			.verifyComplete();
	}

	@Test
	void fluxGroupBy() {
		StepVerifier.create(operator3.fluxGroupBy())
			.expectNext(30)
			.expectNext(25)
			.verifyComplete();
	}
}