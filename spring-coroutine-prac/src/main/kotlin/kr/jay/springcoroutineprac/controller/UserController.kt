package kr.jay.springcoroutineprac.controller

import kr.jay.r2dbcprac.common.Image
import kr.jay.r2dbcprac.common.User
import kr.jay.r2dbcprac.controller.dto.ProfileImageResponse
import kr.jay.r2dbcprac.controller.dto.SignupUserRequest
import kr.jay.r2dbcprac.controller.dto.UserResponse
import kr.jay.springcoroutineprac.service.UserCoroutineService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

/**
 * UserCoroutineController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/24
 */

@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserCoroutineService
) {

    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: String
    ): Mono<UserResponse> {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap { context: SecurityContext ->
                val userName: String = context.authentication.name
                if (userId != userName) return@flatMap Mono.error<UserResponse>(
                    ResponseStatusException(
                        HttpStatus.UNAUTHORIZED
                    )
                )
                userService.findById(userId)
                    .map(this::map)
                    .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signupUser(
        @RequestBody request: SignupUserRequest
    ): Mono<UserResponse> {
        return userService.createUser(
            request.name,
            request.age,
            request.password,
            request.profileImage
        ).map(this::map)
    }


    private fun map(user: User): UserResponse {
        return UserResponse(
            user.id,
            user.name,
            user.age,
            user.followCount,
            user.profileImage
                .map { image: Image ->
                    ProfileImageResponse(
                        image.id,
                        image.name,
                        image.url
                    )
                }
        )
    }
}