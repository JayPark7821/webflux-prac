package kr.jay.springwebfluxprac2.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.springwebfluxprac2.dto.PostResponse;
import kr.jay.springwebfluxprac2.service.PostService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PostController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping("/{id}")
	public Mono<PostResponse> getPostContent(@PathVariable("id") final Long id) {
		return postService.getPostContent(id);
	}

	@GetMapping("/search")
	public Flux<PostResponse> getMultiplePostContent(@RequestParam("ids") List<Long> idList){
		return postService.getMultiplePostContent(idList);
	}
}
