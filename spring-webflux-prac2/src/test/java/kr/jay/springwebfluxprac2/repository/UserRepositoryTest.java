package kr.jay.springwebfluxprac2.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kr.jay.springwebfluxprac2.repository.user.User;
import kr.jay.springwebfluxprac2.repository.user.UserRepository;
import kr.jay.springwebfluxprac2.repository.user.UserRepositoryImpl;
import reactor.test.StepVerifier;

/**
 * UserRepositoryTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
class UserRepositoryTest {

	private final UserRepository userRepository = new UserRepositoryImpl();
	@Test
	void save() {
		final User user = User.builder()
			.name("test")
			.email("test@mail.com")
			.build();
		StepVerifier.create(userRepository.save(user))
			.assertNext(u -> {
					assertEquals(1L, u.getId());
					assertEquals("test", u.getName());
					assertEquals("test@mail.com", u.getEmail());
			})
			.verifyComplete();
	}

	@Test
	void findAll() {
		userRepository.save(
			User.builder()
				.name("test")
				.email("test@mail.com")
				.build()
		);
		userRepository.save(
			User.builder()
				.name("test1")
				.email("test1@mail.com")
				.build()
		);

		StepVerifier.create(userRepository.findAll())
			.assertNext(u -> {
				assertEquals(1L, u.getId());
				assertEquals("test", u.getName());
				assertEquals("test@mail.com", u.getEmail());
			}).assertNext(u -> {
				assertEquals(2L, u.getId());
				assertEquals("test1", u.getName());
				assertEquals("test1@mail.com", u.getEmail());
			})
			.verifyComplete();
	}


}