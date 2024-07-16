package org.example.toylog.domain.like.repository;

import java.util.Optional;
import org.example.toylog.domain.like.entity.Like;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);
    long countByPost(Post post);
}
