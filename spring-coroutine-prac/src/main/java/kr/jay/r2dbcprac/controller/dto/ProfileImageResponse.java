package kr.jay.r2dbcprac.controller.dto;

import lombok.Data;

/**
 * ImageResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */

public class ProfileImageResponse {
	private final String name;
	private final String url;
	private final String id;
	public ProfileImageResponse(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}
}
