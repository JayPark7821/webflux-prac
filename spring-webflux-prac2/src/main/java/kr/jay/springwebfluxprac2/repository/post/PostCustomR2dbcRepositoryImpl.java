package kr.jay.springwebfluxprac2.repository.post;

import java.time.ZonedDateTime;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import kr.jay.springwebfluxprac2.repository.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * PostCustomR2dbcRepositoryImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */
@Repository
@RequiredArgsConstructor
public class PostCustomR2dbcRepositoryImpl implements PostCustomR2dbcRepository {
	private final DatabaseClient databaseClient;

	@Override
	public Flux<Post> findAllByUserId(final Long userId) {
		final String sql = """

			select p.id as pid
				, p.user_id as userId
				, p.title
				, p.content
				, p.created_at as createdAt
				, p.updated_at as updatedAt
				, u.id as uid
				, u.name as name
				, u.email as email
				, u.created_at as uCreatedAt
				, u.updated_at as uUpdatedAt
			from post p 
			left join user u on p.user_id = u.id
			where p.user_id = :userId
			""";

		return databaseClient.sql(sql)
			.bind("userId", userId)
			.fetch()
			.all()
			.map(row -> Post.builder()
				.id((Long) row.get("pid"))
				.userId((Long) row.get("userId"))
				.title((String) row.get("title"))
				.content((String) row.get("content"))
				.user(
					User.builder()
						.id((Long) row.get("uid"))
						.name((String) row.get("name"))
						.email((String) row.get("email"))
						.createdAt(((ZonedDateTime)row.get("uCreatedAt")).toLocalDateTime())
						.updatedAt(((ZonedDateTime) row.get("uUpdatedAt")).toLocalDateTime())
						.build()
				)
				.createdAt(((ZonedDateTime)row.get("createdAt")).toLocalDateTime())
				.updatedAt(((ZonedDateTime) row.get("updatedAt")).toLocalDateTime())
				.build());
	}
}
