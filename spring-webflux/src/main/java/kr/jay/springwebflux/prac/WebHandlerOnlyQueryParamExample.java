package kr.jay.springwebflux.prac;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebHandlerOnlyQueryParamExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/13
 */
@Slf4j
public class WebHandlerOnlyQueryParamExample {
	public static void main(String[] args) {
		final WebHandler webHandler = new WebHandler() {

			@Override
			public Mono<Void> handle(final ServerWebExchange exchange) {
				final ServerHttpRequest request = exchange.getRequest();
				final ServerHttpResponse response = exchange.getResponse();

				final String nameQuery = request.getQueryParams().getFirst("name");
				final String name = nameQuery == null ? "world" : nameQuery;

				final String content = "Hello " + name;
				log.info("response Body : {}", content);
				final Mono<DataBuffer> responseBody = Mono.just(
					response.bufferFactory()
						.wrap(content.getBytes())
				);

				response.addCookie(ResponseCookie.from("name", name).build());
				response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

				return response.writeWith(responseBody);

			}
		};

		final HttpHandler webHttpHandler = WebHttpHandlerBuilder
			.webHandler(webHandler)
			.build();

	}
}
