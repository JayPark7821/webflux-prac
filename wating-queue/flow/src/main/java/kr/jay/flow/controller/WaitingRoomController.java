package kr.jay.flow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;

import kr.jay.flow.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * WaittingRoomController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/29
 */

@Controller
@RequiredArgsConstructor
public class WaitingRoomController {

	private final UserQueueService userQueueService;

	@GetMapping("/waiting-room")
	Mono<Rendering> waitingRoomPage(
		@RequestParam(name = "queue", defaultValue = "default") final String queue,
		@RequestParam("userId") final Long userId,
		@RequestParam("redirect_url") final String redirectUrl

	) {
		return userQueueService.isAllowed(queue, userId)
			.filter(isAllowed -> isAllowed)
			.flatMap(isAllowed -> Mono.just(Rendering.redirectTo(redirectUrl).build())
				.switchIfEmpty(userQueueService.registerWaitQueue(queue, userId)
					.onErrorResume(ex -> userQueueService.getRank(queue, userId))
					.map(rank -> Rendering.view("waiting-room.html")
						.modelAttribute("rank", rank)
						.modelAttribute("userId", userId)
						.modelAttribute("queue", queue)
						.build())));

	}
}
