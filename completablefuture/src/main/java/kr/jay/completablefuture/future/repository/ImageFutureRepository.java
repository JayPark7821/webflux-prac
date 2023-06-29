package kr.jay.completablefuture.future.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import kr.jay.completablefuture.common.repository.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageFutureRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageFutureRepository() {
        imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }


    public CompletableFuture<Optional<ImageEntity>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("ImageRepository.findById: {}", id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Optional.ofNullable(imageMap.get(id));
        });


    }
}