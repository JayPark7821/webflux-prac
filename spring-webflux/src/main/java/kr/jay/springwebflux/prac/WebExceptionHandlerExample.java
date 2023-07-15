package kr.jay.springwebflux.prac;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebExceptionHandlerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/14
 */
@Slf4j
public class WebExceptionHandlerExample {
	public static void main(String[] args) {
		final WebExceptionHandler exceptionHandler = new WebExceptionHandler() {

			@Override
			public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {
				final ServerHttpResponse response = exchange.getResponse();

				if (ex instanceof RuntimeException) {
					response.setStatusCode(HttpStatus.BAD_REQUEST);
					final DataBuffer responseBody = response.bufferFactory().wrap(ex.getMessage().getBytes());
					return response.writeWith(Mono.just(responseBody));
				} else {
					return Mono.error(ex);
				}
			}
		};

		WebHttpHandlerBuilder
			.exceptionHandler(exceptionHandler)
			.build();

	}
}
