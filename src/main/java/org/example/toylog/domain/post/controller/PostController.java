package org.example.toylog.domain.post.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.post.entity.Post;
import org.example.toylog.domain.post.service.PostService;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.example.toylog.global.util.CommonUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String getAllPosts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        if (userDetails != null) {
            Optional<User> userOpt = userService.findByLoginId(userDetails.getUsername());
            userOpt.ifPresent(user -> model.addAttribute("user", user));
        }
        return "main";
    }

    @GetMapping("/@{loginId}")
    public String getUserPosts(@PathVariable String loginId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByLoginId(loginId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Post> posts = postService.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
        }
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "user/blog";
    }

    @GetMapping("/write")
    public String postWriteForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/postWrite";
    }

    @PostMapping("/write")
    public String postWrite(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByLoginId(userDetails.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            post.setUser(user);
            post.setCreatedAt(LocalDateTime.now());
//            String contentHtml = CommonUtil.markdown(post.getContent());
//            post.setContent(contentHtml);
            postService.createPost(post);
            return "redirect:/@" + user.getLoginId();
        }
        return "redirect:/";
    }

    @GetMapping("/@{loginId}/{content}")
    public String getPostDetails(@PathVariable String loginId, @PathVariable String content, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByLoginId(loginId);
        if (userOpt.isPresent()) {
            Optional<Post> postOpt = postService.findPostByUserAndTitle(loginId, content); // Adjust this method to match your service
            if (postOpt.isPresent()) {
                Post post = postOpt.get();
                model.addAttribute("post", post);
                return "post/detailPost";
            }
        }
        return "redirect:/";
    }


    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUser().getLoginId().equals(userDetails.getUsername())) {
                postService.delete(post);
                return "redirect:/@" + userDetails.getUsername();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUser().getLoginId().equals(userDetails.getUsername())) {
                model.addAttribute("post", post);
                return "post/postUpdateForm";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            Post existingPost = postOpt.get();
            if (existingPost.getUser().getLoginId().equals(userDetails.getUsername())) {
                existingPost.setTitle(post.getTitle());
                existingPost.setContent(post.getContent());
                postService.createPost(existingPost);
                return "redirect:/@" + userDetails.getUsername();
            }
        }
        return "redirect:/";
    }
}
