package kr.jay.springwebflux.functionalendpoints;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * RouterFunctionExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/14
 */
@Slf4j
public class RouterFunctionExample {
	public static void main(String[] args) {
		final RouterFunction<ServerResponse> router = RouterFunctions.route()
			.path("/greet", b1 -> b1
				.nest(RequestPredicates.accept(MediaType.TEXT_PLAIN), b2 -> b2
					.GET("/",
						queryParam("name", name -> !name.isBlank()),
						GreetingHandler::greetQueryParam)
					.GET("/name/{name}", GreetingHandler::greetPathVariable)
					.GET("/header", headers(h -> h.firstHeader("X-Custom-Name") != null), GreetingHandler::greetHeader)
					.POST("/json", contentType(MediaType.APPLICATION_JSON), GreetingHandler::greetJsonBody)
					.POST("/text", GreetingHandler::greetPlainTextBody)
				)
			)
			.build();
	}
}
