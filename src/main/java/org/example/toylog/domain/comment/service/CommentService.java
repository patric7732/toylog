//package org.example.toylog.domain.comment.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.example.toylog.domain.comment.entity.Comment;
//import org.example.toylog.domain.comment.repository.CommentRepository;
//import org.example.toylog.domain.post.entity.Post;
//import org.example.toylog.domain.post.repository.PostRepository;
//import org.example.toylog.domain.user.entity.User;
//import org.example.toylog.domain.user.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class CommentService {
//
//    private final CommentRepository commentRepository;
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//
//    public List<Comment> getCommentsByPostId(Long postId) {
//        return commentRepository.findByPostId(postId);
//    }
//
//    public Comment addComment(Long postId, String content, User user) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("error"));
//        Comment comment = new Comment();
//        comment.setContent(content);
//        comment.setCreatedAt(LocalDateTime.now());
//        comment.setPost(post);
//        comment.setUser(user);
//        return commentRepository.save(comment);
//    }
//
//    public Comment editComment(Long commentId, String content, User user) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
//        if (!comment.getUser().getId().equals(user.getLoginId())) {
//            throw new RuntimeException("You are not authorized to edit this comment");
//        }
//        comment.setContent(content);
//        return commentRepository.save(comment);
//    }
//
//    public void deleteComment(Long commentId, User user) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
//        if (!comment.getUser().getId().equals(user.getLoginId())) {
//            throw new RuntimeException("You are not authorized to delete this comment");
//        }
//        commentRepository.deleteById(commentId);
//    }
//}
