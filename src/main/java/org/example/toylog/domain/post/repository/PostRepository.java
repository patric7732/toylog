package org.example.toylog.domain.post.repository;

import java.util.List;
import java.util.Optional;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    Optional<Post> findByUserLoginIdAndTitle(String loginId, String title);
}
