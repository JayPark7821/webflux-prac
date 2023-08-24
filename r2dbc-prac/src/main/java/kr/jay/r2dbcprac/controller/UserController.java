package kr.jay.r2dbcprac.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kr.jay.r2dbcprac.common.User;
import kr.jay.r2dbcprac.controller.dto.ProfileImageResponse;
import kr.jay.r2dbcprac.controller.dto.SignupUserRequest;
import kr.jay.r2dbcprac.controller.dto.UserResponse;
import kr.jay.r2dbcprac.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public Mono<UserResponse> getUserById(@PathVariable String userId) {
		log.info("=====================");
		return ReactiveSecurityContextHolder.getContext()
			.flatMap(context->{
				final String userName = context.getAuthentication().getName();

				if (!userId.equals(userName))
					return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

				return userService.findById(userId)
					.map(this::map);
			}).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	Mono<UserResponse> signupUser(
		@RequestBody final SignupUserRequest request
	) {
		return userService.createUser(
			request.getName(),
			request.getAge(),
			request.getPassword(),
			request.getProfileImage()
		).map(this::map);

	}


	private UserResponse map(final User user) {
		return new UserResponse(
			user.getId(),
			user.getName(),
			user.getAge(),
			user.getFollowCount(),
			user.getProfileImage()
				.map(image ->
					new ProfileImageResponse(image.getId(), image.getName(), image.getUrl())
				)
		);
	}

}
