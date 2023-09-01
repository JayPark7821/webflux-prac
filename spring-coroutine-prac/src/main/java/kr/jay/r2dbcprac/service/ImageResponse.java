package kr.jay.r2dbcprac.service;

import lombok.Getter;
import lombok.Setter;

/**
 * ImageResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
public class ImageResponse {
	private String id;
	private String name;
	private String url;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}