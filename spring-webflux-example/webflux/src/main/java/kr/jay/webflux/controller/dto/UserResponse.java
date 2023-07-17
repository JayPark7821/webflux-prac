package kr.jay.webflux.controller.dto;

import lombok.Data;

/**
 * UserResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */

@Data
public class UserResponse  {
	private final String id;
	private final String name;
	private final int age;
	private final Long followCount;
}
