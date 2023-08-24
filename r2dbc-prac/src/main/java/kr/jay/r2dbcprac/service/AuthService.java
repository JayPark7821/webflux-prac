package kr.jay.r2dbcprac.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

import kr.jay.r2dbcprac.common.repository.AuthEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * AuthService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final R2dbcEntityTemplate r2dbcEntityTemplate;

	public Mono<String> getNameByToken(final String token) {
		final Query query = Query.query(Criteria.where("token").is(token));

		return r2dbcEntityTemplate.select(AuthEntity.class)
			.matching(query)
			.one()
			.map(authEntity -> authEntity.getUserId().toString())
			.doOnNext(name -> log.info("name: {}", name));
	}
}
