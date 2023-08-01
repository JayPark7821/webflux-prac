package kr.jay.springwebfluxprac2.dto;

/**
 * UserUpdateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public record UserUpdateRequest(
	String name,
	String email
) {
}
