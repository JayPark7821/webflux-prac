package kr.jay.springwebfluxprac2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.springwebfluxprac2.dto.PostCreateRequest;
import kr.jay.springwebfluxprac2.dto.PostResponse;
import kr.jay.springwebfluxprac2.dto.PostResponseV2;
import kr.jay.springwebfluxprac2.repository.post.Post;
import kr.jay.springwebfluxprac2.service.PostServiceV2;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PostControllerV2
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostControllerV2 {
	private final PostServiceV2 postServiceV2;

	@PostMapping
	Mono<PostResponseV2> create(@RequestBody PostCreateRequest request) {
		return postServiceV2.create(
				request.userId(),
				request.title(),
				request.content()
			)
			.map(PostResponseV2::of);
	}

	@GetMapping
	Flux<PostResponseV2> findAll() {
		return postServiceV2.findAll()
			.map(PostResponseV2::of);
	}

	@GetMapping("/{id}")
	Mono<ResponseEntity<PostResponseV2>> findById(@PathVariable("id") final Long id) {
		return postServiceV2.findById(id)
			.map(p -> ResponseEntity.ok().body(PostResponseV2.of(p)))
			.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}

	@DeleteMapping("/{id}")
	Mono<ResponseEntity<Void>> delete(@PathVariable("id") final Long id) {
		return postServiceV2.deleteById(id)
			.then(Mono.just(ResponseEntity.noContent().build()));
	}
}
