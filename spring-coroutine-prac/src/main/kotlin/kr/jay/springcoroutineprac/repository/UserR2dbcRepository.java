package kr.jay.springcoroutineprac.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import kr.jay.r2dbcprac.common.repository.UserEntity;

/**
 * UserR2dbcRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/15
 */
public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, Long> {
}
