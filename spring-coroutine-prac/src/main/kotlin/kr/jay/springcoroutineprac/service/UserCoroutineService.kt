package kr.jay.springcoroutineprac.service

import kr.jay.r2dbcprac.common.EmptyImage
import kr.jay.r2dbcprac.common.Image
import kr.jay.r2dbcprac.common.User
import kr.jay.r2dbcprac.common.repository.AuthEntity
import kr.jay.r2dbcprac.common.repository.UserEntity
import kr.jay.springcoroutineprac.repository.UserR2dbcRepository
import kr.jay.r2dbcprac.service.ImageResponse
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*
import java.util.Map
/**
 * UserCoroutineService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/24
 */
@Service
class UserCoroutineService(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val userRepository: UserR2dbcRepository,
) {
    private val webClient = WebClient.create("http://localhost:8081")

    fun findById(userId: String): Mono<User> {
        return userRepository.findById(userId.toLong())
            .flatMap { userEntity: UserEntity ->
                val imageId = userEntity.profileImageId
                val uriVariableMap = Map.of("imageId", imageId)
                webClient.get()
                    .uri("/api/images/{imageId}", uriVariableMap)
                    .retrieve()
                    .toEntity(ImageResponse::class.java)
                    .map { resp: ResponseEntity<ImageResponse> -> resp.body }
                    .map { imageResp: ImageResponse? ->
                        Image(
                            imageResp?.id,
                            imageResp?.name,
                            imageResp?.url
                        )
                    }
                    .switchIfEmpty(Mono.just(EmptyImage()))
                    .map { image: Image ->
                        var profileImage: Optional<Image> = Optional.empty()
                        if (image !is EmptyImage) profileImage = Optional.of(image)
                        map(userEntity, profileImage)
                    }
            }

    }

    @Transactional
    fun createUser(
        name: String,
        age: Int,
        profileImageId: String,
        password: String
    ): Mono<User> {
        val newUser = UserEntity(name, age, profileImageId, password)
        return userRepository.save<UserEntity>(newUser)
            .flatMap { userEntity: UserEntity ->
                r2dbcEntityTemplate.insert<AuthEntity>(
                    AuthEntity(
                        newUser.id,
                        generateRandomToken()
                    )
                ).map { userEntity }
            }
            .map { entity: UserEntity ->
                map(
                    entity,
                    Optional.of<Image>(EmptyImage())
                )
            }
    }

    private fun generateRandomToken(): String {
        val token = StringBuilder()
        for (i in 0..5) {
            val item = ('A'.code.toDouble() + Math.random() * 26).toInt().toChar()
            token.append(item)
        }
        return token.toString()
    }

    private fun map(entity: UserEntity, profileImage: Optional<Image>): User {
        return User(
            entity.id.toString(),
            entity.name,
            entity.age,
            profileImage, listOf(),
            0L
        )
    }
}