package kr.jay.webfluximage.service;

import org.springframework.stereotype.Service;

import kr.jay.webfluximage.repository.ImageReactorRepository;

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
}
