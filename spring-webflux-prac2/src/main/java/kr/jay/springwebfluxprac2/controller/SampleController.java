package kr.jay.springwebfluxprac2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * SampleController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */
@RestController
public class SampleController {

	@GetMapping(("/sample/hello"))
	public Mono<String> getHello(){
		return Mono.just("hello rest controller with webflux!");
	}
}
