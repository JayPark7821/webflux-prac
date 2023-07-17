package kr.jay.webfluximage.handler.dto;

import lombok.Data;

/**
 * ImageResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */

@Data
public class ImageResponse {
	private final String id;
	private final String name;
	private final String url;
}
