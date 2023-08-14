package kr.jay.r2dbcprac.repository;

import java.util.Map;

import kr.jay.r2dbcprac.common.repository.UserEntity;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class UserReactorRepository {
    private final Map<String, UserEntity> userMap;

    public UserReactorRepository() {
        var user = new UserEntity("123df", 32,"jay", "1");

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