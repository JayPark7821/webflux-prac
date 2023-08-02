package kr.jay.springwebfluxprac2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import kr.jay.springwebfluxprac2.dto.UserCreateRequest;
import kr.jay.springwebfluxprac2.dto.UserResponse;
import kr.jay.springwebfluxprac2.repository.user.User;
import kr.jay.springwebfluxprac2.service.PostService;
import kr.jay.springwebfluxprac2.service.PostServiceV2;
import kr.jay.springwebfluxprac2.service.UserService;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * UserControllerTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */

@WebFluxTest(UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {

	static{
		BlockHound.install(
			builder -> builder.allowBlockingCallsInside("org.springframework.data.redis.connection.ReactiveRedisConnection", "stringCommands")
		);
	}

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private UserService userService;
	@MockBean
	private PostServiceV2 postServiceV2 ;

	@Test
	void blockHound() throws Exception{
		StepVerifier.create(Mono.delay(Duration.ofSeconds(1))
				.doOnNext(it -> {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				})).verifyComplete();
	}

	@Test
	void createUser() {
		when(userService.save("test", "test@mail.com")).thenReturn(
			Mono.just(new User(1L, "test", "test@mail.com", LocalDateTime.now(), LocalDateTime.now()))
		);

		webTestClient.post().uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(new UserCreateRequest("test", "test@mail.com"))
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody(UserResponse.class)
			.value(res ->{
				assertEquals("test", res.name());
				assertEquals("test@mail.com", res.email());
			});
	}

	@Test
	void findAll() {
		when(userService.findAll()).thenReturn(
			Flux.just(
				new User(1L, "test", "test@mail.com", LocalDateTime.now(), LocalDateTime.now()),
				new User(2L, "test1", "test2@mail.com", LocalDateTime.now(), LocalDateTime.now()),
				new User(3L, "test2", "test3@mail.com", LocalDateTime.now(), LocalDateTime.now())
			)
		);

		webTestClient.get().uri("/users")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBodyList(UserResponse.class)
			.hasSize(3);
	}


	@Test
	void notFoundUser() {
		when(userService.findById(1L)).thenReturn(Mono.empty());

		webTestClient.get().uri("/users/1")
			.exchange()
			.expectStatus().is4xxClientError();
	}
}