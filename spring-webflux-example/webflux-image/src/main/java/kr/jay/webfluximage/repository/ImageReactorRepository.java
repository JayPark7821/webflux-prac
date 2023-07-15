package kr.jay.webfluximage.repository;

import java.util.Map;

import kr.jay.webfluximage.entity.common.repository.ImageEntity;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ImageReactorRepository {
	private final Map<String, ImageEntity> imageMap;

	public ImageReactorRepository() {
		imageMap = Map.of(
			"image#1000", new ImageEntity("1", "profileImage", "https://dailyone.com/images/1000")
		);
	}

	public Mono<ImageEntity> findById(String id) {
		return Mono.create(sink -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			var image = imageMap.get(id);
			if (image == null) {
				sink.error(new RuntimeException("Image not found"));
			} else {
				sink.success(image);
			}
		});

	}
}