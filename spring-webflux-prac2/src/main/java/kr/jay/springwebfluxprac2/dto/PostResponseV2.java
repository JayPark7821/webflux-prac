package kr.jay.springwebfluxprac2.dto;

import java.time.LocalDateTime;

import kr.jay.springwebfluxprac2.repository.post.Post;

/**
 * PostResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public record PostResponseV2(
	Long id,
	String content,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updatedAt

) {
	public static PostResponseV2 of(final Post post) {
		return new PostResponseV2(
			post.getId(),
			post.getContent(),
			post.getTitle(),
			post.getCreatedAt(),
			post.getUpdatedAt()
		);
	}
}
