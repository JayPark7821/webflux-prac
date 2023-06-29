package kr.jay.completablefuture.future.repository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FollowFutureRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowFutureRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    public CompletableFuture<Long> countByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("FollowRepository.countByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return userFollowCountMap.getOrDefault(userId, 0L);
        });

    }
}