package kr.jay.webflux.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import kr.jay.webflux.auth.IamAuthentication;
import kr.jay.webflux.service.AuthService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * WebFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
@Component
@RequiredArgsConstructor
public class SecurityWebFilter implements WebFilter {

	private final AuthService authService;
	@Override
	public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
		final ServerHttpResponse response = exchange.getResponse();
		final ServerHttpRequest request = exchange.getRequest();

		final String iam = request.getHeaders().getFirst("X-I-AM");

		if (iam == null) {
			response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}
		return authService.getNameByToken(iam)
			.map(name -> new IamAuthentication(name))
			.flatMap(auth -> {

				return chain.filter(exchange)
					.contextWrite(context -> {
						final Context authContext = ReactiveSecurityContextHolder.withAuthentication(auth);
						return context.putAll(authContext);
					});
			})
			.switchIfEmpty(Mono.defer(() -> {
				response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}));
	}
}
