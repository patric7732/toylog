package org.example.toylog.domain.comment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.comment.entity.Comment;
import org.example.toylog.domain.comment.repository.CommentRepository;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.post.repository.PostRepository;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Comment addComment(String title, String content, String loginId) {
        Post post = postRepository.findByUserLoginIdAndTitle(loginId, title).orElseThrow(() -> new IllegalArgumentException("Invalid post title: " + title));
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalArgumentException("Invalid user loginId: " + loginId));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public Comment editComment(Long commentId, String content, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Invalid comment Id: " + commentId));
        if (comment.getUser().equals(user)) {
            comment.setContent(content);
            return commentRepository.save(comment);
        } else {
            throw new IllegalStateException("You can only edit your own comments");
        }
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Invalid comment Id: " + commentId));
        if (comment.getUser().equals(user)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalStateException("You can only delete your own comments");
        }
    }

    public List<Comment> findCommentByPostId(Long postId) {
        return commentRepository.findCommentByPostId(postId);
    }
}
