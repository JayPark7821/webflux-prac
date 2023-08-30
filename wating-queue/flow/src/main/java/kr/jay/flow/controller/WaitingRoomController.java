package kr.jay.flow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;

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
		@RequestParam("redirect_url") final String redirectUrl,
		final ServerWebExchange exchange
	) {
		var key = "user-queue-%s-token".formatted(queue);
		var cookieValue = exchange.getRequest().getCookies().getFirst(key);
		var token = (cookieValue == null) ? "" : cookieValue.getValue();

		return userQueueService.isAllowedByToken(queue, userId, token)
			.filter(allowed -> allowed)
			.flatMap(allowed -> Mono.just(Rendering.redirectTo(redirectUrl).build()))
			.switchIfEmpty(
				userQueueService.registerWaitQueue(queue, userId)
					.onErrorResume(ex -> userQueueService.getRank(queue, userId))
					.map(rank -> Rendering.view("waiting-room.html")
						.modelAttribute("number", rank)
						.modelAttribute("userId", userId)
						.modelAttribute("queue", queue)
						.build()
					)
			);
	}
}
