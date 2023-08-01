// package kr.jay.springwebflux.security;
//
// import java.security.Principal;
// import java.time.LocalDate;
// import java.time.ZoneId;
//
// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.ReactiveSecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.server.ServerWebExchange;
// import org.springframework.web.server.WebFilterChain;
//
// import reactor.core.publisher.Mono;
//
// /**
//  * SecurityWebFilterPrac
//  *
//  * @author jaypark
//  * @version 1.0.0
//  * @since 2023/07/20
//  */
// public class SecurityWebFilterPrac {
//
// 	@GetMapping("/hello")
// 	Mono<String> greet(){
// 		LocalDate.now(ZoneId.of("Asia/Seoul"));
// 		return ReactiveSecurityContextHolder
// 			.getContext()
// 			.map(securityContext ->
// 				securityContext.getAuthentication().getPrincipal())
// 			.flatMap(principal -> Mono.just("Hello " + ((Principal)principal).getName()));
// 	}
//
//
// 	// @Override
// 	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
// 		String iam = exchange.getRequest().getHeaders().getFirst("X-I-AM");
// 		if (iam == null) {
// 			final ServerHttpResponse response = exchange.getResponse();
// 			response.setStatusCode(HttpStatus.UNAUTHORIZED);
// 			return response.setComplete();
// 		}
//
// 		final Authentication authentication = new CustomAuthentication(iam);
// 		return chain.filter(exchange)
// 			.contextWrite(ctx -> ReactiveSecurityContextHolder.withAuthentication(authentication));
// 	}
// }
