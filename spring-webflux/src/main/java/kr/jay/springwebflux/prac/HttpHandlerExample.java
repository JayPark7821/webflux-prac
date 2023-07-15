package kr.jay.springwebflux.prac;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * HttpHandlerExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/13
 */
@Slf4j
public class HttpHandlerExample {
	public static void main(String[] args) {
		new HttpHandler() {

			@Override
			public Mono<Void> handle(final ServerHttpRequest request, final ServerHttpResponse response) {
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
	}
}
