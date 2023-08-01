package kr.jay.springwebfluxprac2;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;

/**
 * RouteConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@Configuration
@RequiredArgsConstructor
public class RouteConfig {

	private final SampleHandler sampleHandler;

	@Bean
	public RouterFunction<ServerResponse> route(){
		return RouterFunctions.route()
			.GET("/hello-functional", sampleHandler::getString)
			.build();
	}
}
