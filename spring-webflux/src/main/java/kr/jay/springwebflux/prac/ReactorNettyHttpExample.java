package kr.jay.springwebflux.prac;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

/**
 * ReactorNettyHttpExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/13
 */
@Slf4j
public class ReactorNettyHttpExample {

	public static void main(String[] args) {
		log.info("start main");
		Consumer<HttpServerRoutes> routesConsumer = routes ->
			routes.get("/hello", (request, response) -> {
					var data = Mono.just("Hello World!");
					return response.sendString(data);
				}
			);

		HttpServer.create()
			.route(routesConsumer)
			.port(8080)
			.bindNow()
			.onDispose()
			.block();
		log.info("end main");
	}
}
