package kr.jay.springwebfluxprac2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/31
 */

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.builder().build();
	}
}
