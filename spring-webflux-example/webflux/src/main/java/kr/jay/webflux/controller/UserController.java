package kr.jay.webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
		return userService.findById(userId)
			.map(user -> new UserResponse(
					user.getId(),
					user.getName(),
					user.getAge(),
					user.getFollowCount()
				)
			).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
}
