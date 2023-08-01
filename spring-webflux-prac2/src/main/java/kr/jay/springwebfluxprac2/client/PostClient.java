package kr.jay.springwebfluxprac2.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import kr.jay.springwebfluxprac2.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * PostClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@Service
@RequiredArgsConstructor
public class PostClient {
	private final WebClient webClient;
	private final String url = "http://localhost:8090";

	public Mono<PostResponse> getPosts(final Long id) {
		final String uriString = UriComponentsBuilder.fromHttpUrl(url)
			.path("/posts/%d".formatted(id))
			.buildAndExpand()
			.toUriString();

		return webClient.get()
			.uri(uriString)
			.retrieve()
			.bodyToMono(PostResponse.class);
	}

}
