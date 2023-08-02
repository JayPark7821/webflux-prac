package kr.jay.springwebfluxprac2.config;

import java.time.Duration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import kr.jay.springwebfluxprac2.repository.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RedisConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/02
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig implements ApplicationListener<ApplicationReadyEvent> {

	private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		reactiveRedisTemplate.opsForValue().get("1")
			.doOnSuccess(i -> log.info("Initialize to redis connection"))
			.doOnError(error -> log.error("Failed to initialize redis connection {}",error.getMessage()))
			.subscribe();

		// reactiveRedisTemplate.opsForList().leftPush("list1", "hello").subscribe();
		// reactiveRedisTemplate.opsForValue().set("sampleKey1", "sample", Duration.ofSeconds(10)).subscribe();
	}

	@Bean
	public ReactiveRedisTemplate<String, User> reactiveRedisUserTemplate(final ReactiveRedisConnectionFactory connectionFactory) {
		final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

		Jackson2JsonRedisSerializer<User> jsonSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, User.class);
		RedisSerializationContext<String, User> serializationContext = RedisSerializationContext
			.<String, User>newSerializationContext()
			.key(RedisSerializer.string())
			.value(jsonSerializer)
			.hashKey(RedisSerializer.string())
			.hashValue(jsonSerializer)
			.build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}
}
