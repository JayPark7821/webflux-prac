package kr.jay.reactor.prac.reactor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import kr.jay.reactor.prac.common.Article;
import kr.jay.reactor.prac.common.EmptyImage;
import kr.jay.reactor.prac.common.Image;
import kr.jay.reactor.prac.common.User;
import kr.jay.reactor.prac.common.repository.UserEntity;
import kr.jay.reactor.prac.reactor.repository.ArticleReactorRepository;
import kr.jay.reactor.prac.reactor.repository.FollowReactorRepository;
import kr.jay.reactor.prac.reactor.repository.ImageReactorRepository;
import kr.jay.reactor.prac.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;


@Slf4j
@RequiredArgsConstructor
public class UserReactorService {
	private final UserReactorRepository userRepository;
	private final ArticleReactorRepository articleRepository;
	private final ImageReactorRepository imageRepository;
	private final FollowReactorRepository followRepository;

	@SneakyThrows
	public Mono<User> getUserById(String id) {
		return userRepository.findById(id)
			.flatMap(userEntity -> (this.getUser(userEntity)));
		//.map(userOptional ->  userOptional.get());// map은 값이 있을 때만 실행된다. 즉 userEntity는 null이 아니다.

	}

	@SneakyThrows
	private Mono<User> getUser(final UserEntity userEntity) {
		final Context context = Context.of("user", userEntity);
		Mono<Image> imageMono = imageRepository.findById(userEntity.getProfileImageId())
			.map(imageEntity ->
				new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
			).onErrorReturn(new EmptyImage());

		var articlesMono = articleRepository.findAllWithContext( )
			.skip(5 )
			.take(2)
			.map(articleEntity ->
				new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
			).collectList()
			.contextWrite(context);


		var followCountMono = followRepository.countByUserId(userEntity.getId());

		return Flux.mergeSequential(imageMono, articlesMono, followCountMono) // Mono.zip(imageMono, articlesMono, followCountMono).map(resultTuple->{ Image image = resultTuple.getT1(); List<Article> articles = resultTuple.getT2(); Long followCount = resultTuple.getT3();  });
			.collectList()
			.map(resultList -> {
				var image = (Image)resultList.get(0);
				var articles = (List<Article>)resultList.get(1);
				var followCount = (Long)resultList.get(2);

				Optional<Image> imageOptional = Optional.empty();
				if(!(image instanceof EmptyImage)) {
					imageOptional = Optional.of(image);
				}
				return
					new User(
						userEntity.getId(),
						userEntity.getName(),
						userEntity.getAge(),
						imageOptional,
						articles,
						followCount);

			});

	}


}