package kr.jay.r2dbcprac.controller.dto;

import lombok.Data;

/**
 * SignupUserRequest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
@Data
public class SignupUserRequest {
	private String name;
	private Integer age;
	private String password;
	private String profileImage;
}
