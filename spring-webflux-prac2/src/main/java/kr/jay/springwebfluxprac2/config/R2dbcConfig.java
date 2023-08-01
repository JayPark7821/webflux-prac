package kr.jay.springwebfluxprac2.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * R2dbcConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

	private final DatabaseClient databaseClient;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		databaseClient.sql("select 1").fetch().one()
			.subscribe(
				success -> {
					log.info("Initialize r2dbc connection success");
				},
				error -> {
					log.error("Fail to initialize r2dbc connection success");
					SpringApplication.exit(event.getApplicationContext(), () -> -1);
				}
			);
	}
}
