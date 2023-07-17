package kr.jay.webfluximage.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import kr.jay.webfluximage.handler.dto.ImageResponse;
import kr.jay.webfluximage.service.ImageService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * ImageHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */
@Component
@RequiredArgsConstructor
public class ImageHandler {
	private final ImageService imageService;

	public Mono<ServerResponse> getImageById(ServerRequest request) {
		final String imageId = request.pathVariable("imageId");
		return imageService.getImageById(imageId)
			.map(image -> new ImageResponse(image.getId(), image.getName(), image.getUrl()))
			.flatMap(imageResponse -> ServerResponse.ok().bodyValue(imageResponse))
			.onErrorMap(e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
			//.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

	}
}
