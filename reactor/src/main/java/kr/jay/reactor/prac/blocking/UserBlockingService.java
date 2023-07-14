package kr.jay.reactor.prac.blocking;

import java.util.Optional;
import java.util.stream.Collectors;

import kr.jay.reactor.prac.blocking.repository.ArticleRepository;
import kr.jay.reactor.prac.blocking.repository.FollowRepository;
import kr.jay.reactor.prac.blocking.repository.ImageRepository;
import kr.jay.reactor.prac.blocking.repository.UserRepository;
import kr.jay.reactor.prac.common.Article;
import kr.jay.reactor.prac.common.Image;
import kr.jay.reactor.prac.common.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserBlockingService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FollowRepository followRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    var image = imageRepository.findById(user.getProfileImageId())
                            .map(imageEntity -> {
                                return new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl());
                            });

                    var articles = articleRepository.findAllByUserId(user.getId())
                            .stream().map(articleEntity ->
                                    new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                            .collect(Collectors.toList());

                    var followCount = followRepository.countByUserId(user.getId());

                    return new User(
                            user.getId(),
                            user.getName(),
                            user.getAge(),
                            image,
                            articles,
                            followCount
                    );
                });
    }
}