package kr.jay.webfluximage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import kr.jay.webfluximage.handler.ImageHandler;

/**
 * RouterConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/17
 */
@Configuration
public class RouterConfig {
	@Bean
	RouterFunction router(
		ImageHandler imageHandler
	) {
		return route()
			.path("/api", b1 -> b1
				.path("/images", b2 -> b2
					.GET("/{imageId:[0-9]+}", imageHandler::getImageById)
				)
			).build();
	}
}
