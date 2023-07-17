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
			"1", new ImageEntity("1", "profileImage", "https://dailyone.com/images/1"),
			"2", new ImageEntity("2", "peter's image", "https://dailyone.com/images/2")
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