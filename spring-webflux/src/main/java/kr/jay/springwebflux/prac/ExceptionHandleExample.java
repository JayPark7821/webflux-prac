package kr.jay.springwebflux.prac;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;

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
public class ExceptionHandleExample {
	public static void main(String[] args) {
		new WebHandler() {

			@Override
			public Mono<Void> handle(final ServerWebExchange exchange) {
				final ServerHttpResponse response = exchange.getResponse();

				return response.writeWith(
					Mono.create(sink -> {
						sink.error(new RuntimeException("test"));
					}))
					.onErrorResume(RuntimeException.class, e->{
						response.setStatusCode(HttpStatus.BAD_REQUEST);
						return response.writeWith(
							Mono.just(response.bufferFactory().wrap(e.getMessage().getBytes()))
						);
					});
			}
		};
	}
}
