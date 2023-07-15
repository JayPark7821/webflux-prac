package kr.jay.springwebflux.prac;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseCookie;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebHandlerOnlyFormDataExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/13
 */
@Slf4j
public class WebHandlerOnlyAcceptJsonExample {

	record NameHolder(String name) {}
	public static void main(String[] args) {
		final ServerCodecConfigurer codecConfigurer = ServerCodecConfigurer.create();
		final WebHandler webHandler = new WebHandler() {
			@Override
			public Mono<Void> handle(final ServerWebExchange exchange) {
				final ServerRequest serverRequest = ServerRequest.create(
					exchange,
					codecConfigurer.getReaders()
				);
				final ServerHttpResponse response = exchange.getResponse();

				final Mono<NameHolder> bodyMono = serverRequest.bodyToMono(NameHolder.class);
				return bodyMono.flatMap(nameHolder->{
					final String nameQuery = nameHolder.name();
					final String name = nameQuery == null ? "world" : nameQuery;

					String content = "Hello " + name;
					log.info("responseBody: {}", content);
					Mono<DataBuffer> responseBody = Mono.just(
						response.bufferFactory()
							.wrap(content.getBytes())
					);

					response.addCookie(
						ResponseCookie.from("name", name).build());
					response.getHeaders()
						.add("Content-Type", "text/plain");
					return response.writeWith(responseBody);
				});

			}
		};
	}


}
