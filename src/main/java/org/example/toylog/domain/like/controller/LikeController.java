package org.example.toylog.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.like.service.LikeService;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.post.service.PostService;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.example.toylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/@{loginId}/{title}")
public class LikeController {
    private final LikeService likeService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/likes")
    public String getPostDetails(@PathVariable String loginId, @PathVariable String title, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByLoginId(loginId);
        if (userOpt.isPresent()) {
            Optional<Post> postOpt = postService.findPostByUserAndTitle(loginId, title);
            if (postOpt.isPresent()) {
                Post post = postOpt.get();
                model.addAttribute("post", post);

                User currentUser = ((CustomUserDetails) userDetails).getUser();
                boolean hasLiked = likeService.hasLiked(post, currentUser);
                long likeCount = likeService.countLikes(post);
                model.addAttribute("hasLiked", hasLiked);
                model.addAttribute("likeCount", likeCount);
                return "post/detailPost";  // Assuming this is the correct view name
            }
        }
        return "redirect:/";
    }

    @PostMapping("/like")
    public String likePost(@PathVariable String loginId, @PathVariable String title, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Optional<Post> postOpt = postService.findPostByUserAndTitle(loginId, title);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            likeService.likePost(post, user);
        }
        return "redirect:/@" + loginId + "/" + title;
    }

    @DeleteMapping("/unlike")
    public String unlikePost(@PathVariable String loginId, @PathVariable String title, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Optional<Post> postOpt = postService.findPostByUserAndTitle(loginId, title);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            likeService.unlikePost(post, user);
        }
        return "redirect:/@" + loginId + "/" + title;
    }
}
