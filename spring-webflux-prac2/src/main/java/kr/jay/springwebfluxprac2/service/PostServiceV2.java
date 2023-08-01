package kr.jay.springwebfluxprac2.service;

import org.springframework.stereotype.Service;

import kr.jay.springwebfluxprac2.repository.post.Post;
import kr.jay.springwebfluxprac2.repository.post.PostR2dbcRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PostServiceV2
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
@Service
@RequiredArgsConstructor
public class PostServiceV2 {
	private final PostR2dbcRepository postR2dbcRepository;

	public Mono<Post> create(
		final Long userId,
		final String title,
		final String content
	){
		return postR2dbcRepository.save(
			Post.builder()
				.userId(userId)
				.title(title)
				.content(content)
				.build()
		);
	}

	public Flux<Post> findAll(){
		return postR2dbcRepository.findAll();
	}

	public Mono<Post> findById(final Long id) {
		return postR2dbcRepository.findById(id);
	}

	public Mono<Void> deleteById(final Long id) {
		return postR2dbcRepository.deleteById(id);
	}

	public Flux<Post> findAllByUserId(final Long userId) {
		return postR2dbcRepository.findAllByUserId(userId);
	}
}
