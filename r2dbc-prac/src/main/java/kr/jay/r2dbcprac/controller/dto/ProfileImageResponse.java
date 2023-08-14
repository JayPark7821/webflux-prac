package kr.jay.r2dbcprac.controller.dto;

import lombok.Data;

/**
 * ImageResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
@Data
public class ProfileImageResponse {
	private final String name;
	private final String url;
	private final String id;
}
