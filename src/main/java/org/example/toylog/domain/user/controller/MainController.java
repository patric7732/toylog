package org.example.toylog.domain.user.controller;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.post.service.PostService;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PostService postService;

//    @GetMapping("/")
//    public String getAllPosts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        List<Post> posts = postService.findAll();
//        model.addAttribute("posts", posts);
//        if (userDetails != null) {
//            Optional<User> userOpt = userService.findByLoginId(userDetails.getUsername());
//            userOpt.ifPresent(user -> model.addAttribute("user", user));
//        }
//        return "main";
//    }

//    @GetMapping("/@{loginId}")
//    public String personalBlog(@PathVariable String loginId, Model model) {
//        Optional<User> optionalUser = userService.findByLoginId(loginId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            model.addAttribute("user", user);
//        }
//        return "user/blog";
//    }
}