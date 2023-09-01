package kr.jay.springcoroutineprac

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * UserApplication
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/28
 */
@SpringBootApplication(
    scanBasePackages = [
        "kr.jay"
    ]
)
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}