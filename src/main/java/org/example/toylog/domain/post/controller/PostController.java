package org.example.toylog.domain.post.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.comment.entity.Comment;
import org.example.toylog.domain.comment.service.CommentService;
import org.example.toylog.domain.follow.service.FollowService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final FollowService followService;

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
    public String getUserBlog(@PathVariable String loginId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByLoginId(loginId);
        User currentUser = ((CustomUserDetails) userDetails).getUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Post> posts = postService.findByUser(user);
            boolean isFollowing = followService.isFollowing(user, currentUser);
            long followersCount = followService.getFollowers(user).size();
            long followingCount = followService.getFollowing(user).size();


            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
            model.addAttribute("isFollowing", isFollowing);
            model.addAttribute("followersCount", followersCount);
            model.addAttribute("followingCount", followingCount);
            model.addAttribute("isSelf", user.equals(currentUser));

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

    @GetMapping("/@{loginId}/{title}")
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
                List<Comment> comments  = commentService.findCommentByPostId(post.getId());
                model.addAttribute("comments", comments);
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("hasLiked", hasLiked);
                model.addAttribute("likeCount", likeCount);
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
