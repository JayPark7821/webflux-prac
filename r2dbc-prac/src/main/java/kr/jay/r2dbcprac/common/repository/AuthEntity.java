package kr.jay.r2dbcprac.common.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

/**
 * AuthEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
@Table("AUTH")
@Data
public class AuthEntity {
	@Id
	private Long id;
	private final Long userId;
	private final String token;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@PersistenceCreator
	public AuthEntity(final Long id, final Long userId, final String token) {
		this.id = id;
		this.userId = userId;
		this.token = token;
	}

	public AuthEntity(final Long userId, final String token) {
		this(null, userId, token);
	}
}
