package kr.jay.r2dbcprac.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kr.jay.r2dbcprac.common.repository.UserEntity;
import kr.jay.r2dbcprac.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PersonInsertRunner
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/15
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class PersonInsertRunner implements CommandLineRunner {
	private final UserR2dbcRepository userR2dbcRepository;
	@Override
	public void run(final String... args) throws Exception {
		final UserEntity user = new UserEntity("jay", 10, "1", "awerawsefwa!");

		final UserEntity userEntity = userR2dbcRepository.save(user).block();
	}
}
