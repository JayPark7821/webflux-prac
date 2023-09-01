package kr.jay.r2dbcprac.controller.dto;

import lombok.Data;

/**
 * SignupUserRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class SignupUserRequest {
	private String name;
	private Integer age;
	private String password;
	private String profileImage;

	public SignupUserRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(final String profileImage) {
		this.profileImage = profileImage;
	}
}
