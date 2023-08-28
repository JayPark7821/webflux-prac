package kr.jay.flow.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

/**
 * ApplicationAdvice
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */

@RestControllerAdvice
public class ApplicationAdvice {

	@ExceptionHandler(ApplicationException.class)
	Mono<ResponseEntity<ServerExceptionResponse>> applicationExceptionHandler(ApplicationException exception) {
		return Mono.just(
			ResponseEntity
			.status(exception.getHttpStatus())
			.body(new ServerExceptionResponse(exception.getCode(), exception.getReason()))
		);
	}

	public record ServerExceptionResponse(
		String code,
		String reason
	) {
	}

}
