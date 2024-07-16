package org.example.toylog.domain.like.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.like.entity.Like;
import org.example.toylog.domain.like.repository.LikeRepository;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public void likePost(Post post, User user) {
        Optional<Like> likeOpt = likeRepository.findByPostAndUser(post, user);
        if (likeOpt.isEmpty()) {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);
        }
    }

    public void unlikePost(Post post, User user) {
        Optional<Like> likeOpt = likeRepository.findByPostAndUser(post, user);
        likeOpt.ifPresent(likeRepository::delete);
    }

    public boolean hasLiked(Post post, User user) {
        return likeRepository.findByPostAndUser(post, user).isPresent();
    }

    public long countLikes(Post post) {
        return likeRepository.countByPost(post);
    }
}
