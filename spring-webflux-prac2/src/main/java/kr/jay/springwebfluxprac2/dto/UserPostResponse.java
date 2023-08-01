package kr.jay.springwebfluxprac2.dto;

import java.time.LocalDateTime;

import kr.jay.springwebfluxprac2.repository.post.Post;

/**
 * UserPostResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
public record UserPostResponse(
	Long id,
	String username,
	String title,
	String content,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static UserPostResponse of(final Post post) {
		return new UserPostResponse(
			post.getId(),
			post.getUser().getName(),
			post.getTitle(),
			post.getContent(),
			post.getCreatedAt(),
			post.getUpdatedAt()
		);
	}
}
