package kr.jay.r2dbcprac.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/**
 * AuthService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
@Service
public class AuthService {
	private static final Map<String, String> tokenUserIdMap = Map.of("abcd", "1234");

	public Mono<String> getNameByToken(final String token) {
		return Mono.justOrEmpty(tokenUserIdMap.get(token));
	}
}
