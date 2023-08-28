package kr.jay.flow.dto;

/**
 * AllowUserResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */
public record AllowUserResponse(
	Long requestCount,
	Long allowedCount
) {
}
