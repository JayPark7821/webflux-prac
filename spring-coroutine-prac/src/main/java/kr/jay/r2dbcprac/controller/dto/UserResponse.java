package kr.jay.r2dbcprac.controller.dto;

import java.util.Optional;

import lombok.Data;

/**
 * UserResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */


public class UserResponse  {
	private final String id;
	private final String name;
	private final int age;
	private final Long followCount;
	private final Optional<ProfileImageResponse> imageResponse;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Long getFollowCount() {
		return followCount;
	}

	public Optional<ProfileImageResponse> getImageResponse() {
		return imageResponse;
	}

	public UserResponse(final String id, final String name, final int age, final Long followCount,
		final Optional<ProfileImageResponse> imageResponse) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.followCount = followCount;
		this.imageResponse = imageResponse;
	}
}
