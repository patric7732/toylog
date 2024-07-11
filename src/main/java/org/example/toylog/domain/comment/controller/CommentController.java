//package org.example.toylog.domain.comment.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.example.toylog.domain.comment.entity.Comment;
//import org.example.toylog.domain.comment.service.CommentService;
//import org.example.toylog.domain.user.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("posts/{postId}/comments")
//@RequiredArgsConstructor
//public class CommentController {
//    private final CommentService commentService;
//
//    @PostMapping("/add/{postId}")
//    public Comment addComment(@PathVariable Long postId, @RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
//        User user = (User) userDetails;
//        return commentService.addComment(postId, content, user);
//    }
//
//    @PutMapping("/edit/{commentId}")
//    public Comment editComment(@PathVariable Long commentId, @RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
//        User user = (User) userDetails;
//        return commentService.editComment(commentId, content, user);
//    }
//
//    @DeleteMapping("/delete/{commentId}")
//    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
//        User user = (User) userDetails;
//        commentService.deleteComment(commentId, user);
//    }
//}
//
