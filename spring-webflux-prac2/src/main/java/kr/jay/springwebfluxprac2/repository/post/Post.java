package kr.jay.springwebfluxprac2.repository.post;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import kr.jay.springwebfluxprac2.repository.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * post
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/01
 */

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table("posts")
public class Post {

	@Id
	private Long id;
	@Column("user_id")
	private Long userId;
	@Transient
	private User user;
	private String title;
	private String content;
	@Column("created_at")
	@CreatedDate
	private LocalDateTime createdAt;
	@Column("updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;

}
