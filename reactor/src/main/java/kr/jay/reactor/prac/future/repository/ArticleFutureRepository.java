package kr.jay.reactor.prac.future.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import kr.jay.completablefuture.common.repository.ArticleEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleFutureRepository {
    private static List<ArticleEntity> articleEntities;

    public ArticleFutureRepository() {
        articleEntities = List.of(
                new ArticleEntity("1", "소식1", "내용1", "1234"),
                new ArticleEntity("2", "소식2", "내용2", "1234"),
                new ArticleEntity("3", "소식3", "내용3", "10000")
        );
    }

    public CompletableFuture<List<ArticleEntity>> findAllByUserId(String userId) {
        return CompletableFuture.supplyAsync(()->{
            log.info("ArticleRepository.findAllByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return articleEntities.stream()
                    .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                    .collect(Collectors.toList());
        });
    }
}