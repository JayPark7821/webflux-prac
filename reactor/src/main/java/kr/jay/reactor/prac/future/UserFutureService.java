package kr.jay.reactor.prac.future;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import kr.jay.completablefuture.common.Article;
import kr.jay.completablefuture.common.Image;
import kr.jay.completablefuture.common.User;
import kr.jay.completablefuture.common.repository.UserEntity;
import kr.jay.completablefuture.future.repository.ArticleFutureRepository;
import kr.jay.completablefuture.future.repository.FollowFutureRepository;
import kr.jay.completablefuture.future.repository.ImageFutureRepository;
import kr.jay.completablefuture.future.repository.UserFutureRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserFutureService {
	private final UserFutureRepository userFutureRepository;
	private final ArticleFutureRepository articleFutureRepository;
	private final ImageFutureRepository imageFutureRepository;
	private final FollowFutureRepository followFutureRepository;

	@SneakyThrows
	public CompletableFuture<Optional<User>> getUserById(String id) {
		return userFutureRepository.findById(id)
			.thenComposeAsync(this::getUser);
	}

	@SneakyThrows
	private CompletableFuture<Optional<User>> getUser(final Optional<UserEntity> userEntityOptional) {
		if (userEntityOptional.isEmpty())
			return CompletableFuture.completedFuture(Optional.empty());
		// final CompletableFuture<Optional<User>> future = new CompletableFuture<>();
		// future.complete(Optional.empty());
		// return future;

		final UserEntity userEntity = userEntityOptional.get();

		var imageFuture = imageFutureRepository.findById(userEntity.getProfileImageId())
			.thenApplyAsync(imageEntityOptional ->
				imageEntityOptional.map(imageEntity ->
					new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
				)
			);

		var articlesFuture = articleFutureRepository.findAllByUserId(userEntity.getId())
			.thenApplyAsync(articleEntities ->
				articleEntities.stream().map(articleEntity ->
						new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
					).collect(Collectors.toList()));

		var followCountFuture = followFutureRepository.countByUserId(userEntity.getId());

		return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
			.thenAcceptAsync(v -> log.info("All futures are done!"))
			.thenRunAsync(()-> log.info("All futures are done!"))
			.thenApplyAsync(v -> {
				try {
					final Optional<Image> image = imageFuture.get();
					final List<Article> articles = articlesFuture.get();
					final Long followCount = followCountFuture.get();

					return Optional.of(
						new User(
							userEntity.getId(),
							userEntity.getName(),
							userEntity.getAge(),
							image,
							articles,
							followCount
						)
					);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
	}
}