package kr.jay.springwebfluxprac2.dto;

/**
 * UserCreateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
public record UserCreateRequest(
	String name,
	String email
) {
}
