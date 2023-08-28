package kr.jay.flow.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ApplicationException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {
	private HttpStatus httpStatus;
	private String code;
	private String reason;
}
