package kr.jay.webflux.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * ImageResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
@Getter
@Setter
public class ImageResponse {
	private String id;
	private String name;
	private String url;
}
