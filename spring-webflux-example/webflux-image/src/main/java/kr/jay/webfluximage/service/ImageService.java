package kr.jay.webfluximage.service;

import org.springframework.stereotype.Service;

import kr.jay.webfluximage.entity.common.Image;
import kr.jay.webfluximage.repository.ImageReactorRepository;
import reactor.core.publisher.Mono;

/**
 * ImageService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/14
 */
@Service
public class ImageService {

	private final ImageReactorRepository imageRepository = new ImageReactorRepository();

	public Mono<Image> getImageById(String imageId) {
		return imageRepository.findById(imageId)
			.map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));
	}
}
