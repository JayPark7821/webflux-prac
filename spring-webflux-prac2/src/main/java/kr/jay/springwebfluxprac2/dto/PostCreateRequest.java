package kr.jay.springwebfluxprac2.dto;

/**
 * PostCreateRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
public record PostCreateRequest(
	Long userId,
	String title,
	String content
) {
}
