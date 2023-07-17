package kr.jay.webflux.repository;

import java.util.Map;

import kr.jay.webflux.common.repository.UserEntity;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class UserReactorRepository {
    private final Map<String, UserEntity> userMap;

    public UserReactorRepository() {
        var user = new UserEntity("1234", "taewoo", 32, "image#1000");

        userMap = Map.of("1234", user);
    }

    public Mono<UserEntity> findById(String userId) {
        return Mono.create(sink -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var user = userMap.get(userId);
            if (user == null) {
                sink.success();
            }else{
                sink.success(user);
            }
        });

        // try {
        //     Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }
        // final UserEntity user = userMap.get(userId);
        // return Mono.justOrEmpty(user);

    }
}