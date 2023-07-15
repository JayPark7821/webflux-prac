package kr.jay.springwebflux.functionalendpoints;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseCookie;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * HandlerFunctionExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/14
 */
@Slf4j
public class HandlerFunctionExample {
	public static void main(String[] args) {
		final HandlerFunction<ServerResponse> handler = request -> {
			final String name = request.queryParam("name")
				.orElse("world");

			final String content = "Hello, " + name;
			final ResponseCookie cookie = ResponseCookie.from("name", name).build();
			return ServerResponse.ok()
				.cookie(cookie)
				.headers(headers -> headers.set("X-Hello", name))
				.cacheControl(CacheControl.noCache())
				.bodyValue(content);
		};
	}
}
