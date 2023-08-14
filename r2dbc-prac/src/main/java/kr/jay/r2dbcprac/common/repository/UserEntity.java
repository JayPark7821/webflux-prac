package kr.jay.r2dbcprac.common.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Table("USER")
@Data
public class UserEntity {
    @Id
    private Long id;
    private final String name;
    private final Integer age;
    private final String profileImageId;
    private final String password;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public UserEntity(final String name, final Integer age, final String profileImageId, final String password) {
        this.name = name;
        this.age = age;
        this.profileImageId = profileImageId;
        this.password = password;
    }
}