package org.example.toylog.domain.comment.repository;

import java.util.List;
import org.example.toylog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
