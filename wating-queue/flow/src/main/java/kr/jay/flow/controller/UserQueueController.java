package kr.jay.flow.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.flow.dto.AllowUserResponse;
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
}
