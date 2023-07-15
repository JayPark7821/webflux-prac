package kr.jay.reactor.prac.reactor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.jay.reactor.prac.common.User;
import kr.jay.reactor.prac.reactor.repository.ArticleReactorRepository;
import kr.jay.reactor.prac.reactor.repository.FollowReactorRepository;
import kr.jay.reactor.prac.reactor.repository.ImageReactorRepository;
import kr.jay.reactor.prac.reactor.repository.UserReactorRepository;

/**
 * UserBlockingServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/29
 */
class UserReactorServiceTest {
	UserReactorService userService;
	UserReactorRepository userRepository;
	ArticleReactorRepository articleRepository;
	ImageReactorRepository imageRepository;
	FollowReactorRepository followRepository;

	@BeforeEach
	void setUp() {
		userRepository = new UserReactorRepository();
		articleRepository = new ArticleReactorRepository();
		imageRepository = new ImageReactorRepository();
		followRepository = new FollowReactorRepository();

		userService = new UserReactorService(
			userRepository, articleRepository, imageRepository, followRepository
		);
	}

	@Test
	void getUserEmptyIfInvalidUserIdIsGiven() throws Exception{
		// given
		String userId = "invalid_user_id";

		// when
		Optional<User> user = userService.getUserById(userId).blockOptional();

		// then
		assertTrue(user.isEmpty());
	}

	@Test
	void testGetUser() throws Exception{
		// given
		String userId = "1234";

		// when
		Optional<User> optionalUser = userService.getUserById(userId).blockOptional();

		// then
		assertFalse(optionalUser.isEmpty());
		var user = optionalUser.get();
		assertEquals(user.getName(), "taewoo");
		assertEquals(user.getAge(), 32);

		assertFalse(user.getProfileImage().isEmpty());
		var image = user.getProfileImage().get();
		assertEquals(image.getId(), "image#1000");
		assertEquals(image.getName(), "profileImage");
		assertEquals(image.getUrl(), "https://dailyone.com/images/1000");

		assertEquals(2, user.getArticleList().size());

		assertEquals(1000, user.getFollowCount());
	}
}