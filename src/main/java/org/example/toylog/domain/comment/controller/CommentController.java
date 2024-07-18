package org.example.toylog.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.comment.entity.Comment;
import org.example.toylog.domain.comment.service.CommentService;
import org.example.toylog.domain.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{loginId}/{title}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(@PathVariable String loginId, @PathVariable String title, @RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
        commentService.addComment(title, content, userDetails.getUsername());
        return "redirect:/" + loginId + "/" + title;
    }

    @PostMapping("/editComment/{commentId}")
    public String editComment(@PathVariable String loginId, @PathVariable String title, @PathVariable Long commentId, @RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        commentService.editComment(commentId, content, user);
        return "redirect:/" + loginId + "/" + title;
    }

    @PostMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable String loginId, @PathVariable String title, @PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        commentService.deleteComment(commentId, user);
        return "redirect:/" + loginId + "/" + title;
    }
}
