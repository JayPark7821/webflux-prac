package kr.jay.flow.controller;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import kr.jay.flow.dto.AllowUserResponse;
import kr.jay.flow.dto.AllowedUserResponse;
import kr.jay.flow.dto.RankNumberResponse;
import kr.jay.flow.dto.RegisterUserResponse;
import kr.jay.flow.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * UserQueueController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/queue")
public class UserQueueController {

	private final UserQueueService userQueueService;

	@PostMapping
	Mono<RegisterUserResponse> registerUser(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("userId") final Long userId
	) {
		return userQueueService.registerWaitQueue(queue, userId)
			.map(RegisterUserResponse::new);
	}

	@PostMapping("/allow")
	Mono<AllowUserResponse> allowUser(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("count") final Long count
	){
		return userQueueService.allowUser(queue, count)
			.map(allowed -> new AllowUserResponse(count, allowed));
	}

	@GetMapping("/allowed")
	Mono<AllowedUserResponse> isAllowed(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("userId") final Long userId,
		@RequestParam("token") final String token
	){
		return userQueueService.isAllowedByToken(queue, userId, token)
			.map(AllowedUserResponse::new);
	}

	@GetMapping("/rank")
	Mono<RankNumberResponse> getRank(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("userId") final Long userId
	){
		return userQueueService.getRank(queue, userId)
			.map(RankNumberResponse::new);
	}

	@GetMapping("/touch")
	Mono<?> touch(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("userId") final Long userId,
		ServerWebExchange exchange
	){
		return Mono.defer(() -> userQueueService.generateToken(queue, userId))
			.map(token -> {
				exchange.getResponse().addCookie(
					ResponseCookie.from("user-queue-%s-token".formatted(queue), token)
						.maxAge(Duration.ofSeconds(300))
						.path("/")
						.build()
				);
				return token;
			});
	}


}
