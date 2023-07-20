package kr.jay.webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kr.jay.webflux.controller.dto.ProfileImageResponse;
import kr.jay.webflux.controller.dto.UserResponse;
import kr.jay.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public Mono<UserResponse> getUserById(@PathVariable String userId) {
		return ReactiveSecurityContextHolder.getContext()
			.flatMap(context->{
				final String userName = context.getAuthentication().getName();

				if (!userId.equals(userName))
					return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

				return userService.findById(userId)
					.map(user -> new UserResponse(
						user.getId(),
						user.getName(),
						user.getAge(),
						user.getFollowCount(),
						user.getProfileImage()
							.map(image ->
								new ProfileImageResponse(image.getId(), image.getName(), image.getUrl())
							)
						)
					);
			}).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

	}
}
