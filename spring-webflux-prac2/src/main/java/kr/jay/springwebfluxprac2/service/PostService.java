package kr.jay.springwebfluxprac2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.jay.springwebfluxprac2.client.PostClient;
import kr.jay.springwebfluxprac2.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * PostService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@RequiredArgsConstructor
@Service
public class PostService {
	private final PostClient postClient;

	public Mono<PostResponse> getPostContent(final Long id) {
		return postClient.getPosts(id)
			.onErrorResume(error -> Mono.just(new PostResponse(id, "Fallback data %d".formatted(id))));
	}

	public Flux<PostResponse> getMultiplePostContent(final List<Long> idList) {
		return Flux.fromIterable(idList)
			.flatMap(this::getPostContent);
	}

	public Flux<PostResponse> getParallelMultiplePostContent(final List<Long> idList) {
		return Flux.fromIterable(idList)
			.parallel()
			.runOn(Schedulers.parallel())
			.flatMap(this::getPostContent)
			.sequential();
	}
}
