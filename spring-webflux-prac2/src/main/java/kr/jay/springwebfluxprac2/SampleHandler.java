package kr.jay.springwebfluxprac2;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * SampleHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@Component
public class SampleHandler {

	public Mono<ServerResponse> getString(final ServerRequest request){
		return ServerResponse.ok().bodyValue("Hello Functional endpoint!");
	}

}
