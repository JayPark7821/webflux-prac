package kr.jay.springwebfluxprac2.dto;

import java.time.LocalDateTime;

import kr.jay.springwebfluxprac2.repository.user.User;

/**
 * UserResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public record UserResponse(
	Long id,
	String name,
	String email,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

	public static UserResponse of(final User user) {
		return new UserResponse(
			user.getId(),
			user.getName(),
			user.getEmail(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}
}
