package kr.jay.springwebflux.prac;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebFilterExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/14
 */
@Slf4j
public class WebFilterExample {
	public static void main(String[] args) {
		final WebFilter preFilter = new WebFilter() {

			@Override
			public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
				log.info("extract name from header");
				final ServerHttpRequest request = exchange.getRequest();
				final ServerHttpResponse response = exchange.getResponse();

				final String name = request.getHeaders()
					.getFirst("X-Custom-Name");

				if (name == null) {
					response.setStatusCode(HttpStatus.BAD_REQUEST);
					return response.setComplete();
				} else {
					exchange.getAttributes().put("name", name);
					final ServerHttpRequest newReq = request.mutate()
						.headers(h -> h.remove("X-Custom-Name"))
						.build();
					return chain.filter(
						exchange.mutate().request(newReq).build()
					);
				}
			}
		};

		final WebFilter aroundFilter = new WebFilter() {
			@Override
			public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
				log.info("timeLoggingFilter");
				final long startTime = System.nanoTime();
				return chain.filter(exchange)
					.doOnSuccess(v -> {
						final long endTime = System.nanoTime();
						log.info("time : {}ms", (endTime - startTime) / 1000000.0);
					});
			}
		};

		final WebHandler webHandler = new WebHandler() {

			@Override
			public Mono<Void> handle(final ServerWebExchange exchange) {
				log.info("web handler");
				final ServerHttpRequest request = exchange.getRequest();
				final ServerHttpResponse response = exchange.getResponse();

				final String nameHeader = request.getHeaders()
					.getFirst("X-Custom-Name");

				log.info("X-Custom-Name : {}", nameHeader);
				final String name = exchange.getAttribute("name");
				final String content = "Hello " + name;
				final Mono<DataBuffer> responseBody =
					Mono.just(response.bufferFactory().wrap(content.getBytes()));

				response.getHeaders()
					.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
				return response.writeWith(responseBody);
			}
		};

		final HttpHandler webHttpHandler = WebHttpHandlerBuilder
			.webHandler(webHandler)
			.filter(
				preFilter,
				aroundFilter
			)
			.build();
	}
}
