package org.example.toylog.domain.comment.repository;

import org.example.toylog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPostId(Long postId);
}
