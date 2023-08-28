package kr.jay.flow.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

/**
 * ErrorCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */

@AllArgsConstructor
public enum ErrorCode {

	QUEUE_ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "uq-0001", "Already registered in queue"),
	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String reason;

	public ApplicationException build(){
		return new ApplicationException(httpStatus, code, reason);
	}

	public ApplicationException build(Object... args){
		return new ApplicationException(httpStatus, code, reason.formatted(args));
	}
}
