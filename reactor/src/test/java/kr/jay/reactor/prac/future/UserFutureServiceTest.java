package kr.jay.reactor.prac.future;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.jay.completablefuture.common.User;
import kr.jay.completablefuture.future.UserFutureService;
import kr.jay.completablefuture.future.repository.ArticleFutureRepository;
import kr.jay.completablefuture.future.repository.FollowFutureRepository;
import kr.jay.completablefuture.future.repository.ImageFutureRepository;
import kr.jay.completablefuture.future.repository.UserFutureRepository;

/**
 * UserBlockingServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/29
 */
class UserFutureServiceTest {
	UserFutureService userFutureService;
	UserFutureRepository userFutureRepository;
	ArticleFutureRepository articleFutureRepository;
	ImageFutureRepository imageFutureRepository;
	FollowFutureRepository followFutureRepository;

	@BeforeEach
	void setUp() {
		userFutureRepository = new UserFutureRepository();
		articleFutureRepository = new ArticleFutureRepository();
		imageFutureRepository = new ImageFutureRepository();
		followFutureRepository = new FollowFutureRepository();

		userFutureService = new UserFutureService(
			userFutureRepository, articleFutureRepository, imageFutureRepository, followFutureRepository
		);
	}

	@Test
	void getUserEmptyIfInvalidUserIdIsGiven() throws Exception{
		// given
		String userId = "invalid_user_id";

		// when
		Optional<User> user = userFutureService.getUserById(userId).get();

		// then
		assertTrue(user.isEmpty());
	}

	@Test
	void testGetUser() throws Exception{
		// given
		String userId = "1234";

		// when
		Optional<User> optionalUser = userFutureService.getUserById(userId).get();

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