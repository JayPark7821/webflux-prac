package kr.jay.springwebfluxprac2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.springwebfluxprac2.dto.UserCreateRequest;
import kr.jay.springwebfluxprac2.dto.UserPostResponse;
import kr.jay.springwebfluxprac2.dto.UserResponse;
import kr.jay.springwebfluxprac2.service.PostServiceV2;
import kr.jay.springwebfluxprac2.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final PostServiceV2 postServiceV2;

	@PostMapping
	public Mono<UserResponse> createUser(@RequestBody UserCreateRequest request) {
		return userService.save(request.name(), request.email())
			.map(UserResponse::of);
	}

	@GetMapping
	public Flux<UserResponse> findAll() {
		return userService.findAll()
			.map(UserResponse::of);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<UserResponse>> findById(@PathVariable("id") final Long id) {
		return userService.findById(id)
			.map(user -> ResponseEntity.ok(UserResponse.of(user)))
			.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<?>> deleteById(@PathVariable("id") final Long id) {
		return userService.deleteById(id)
			.then(Mono.just(ResponseEntity.noContent().build()));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<UserResponse>> update(
		@PathVariable("id") final Long id,
		@RequestBody final UserCreateRequest request
	) {
		return userService.update(id, request.name(), request.email())
			.map(user -> ResponseEntity.ok(UserResponse.of(user)))
			.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}

	@GetMapping("/{id}/posts")
	public Flux<UserPostResponse> getUserPosts(@PathVariable("id") final Long id) {
		return postServiceV2.findAllByUserId(id)
			.map(UserPostResponse::of);
	}
}
