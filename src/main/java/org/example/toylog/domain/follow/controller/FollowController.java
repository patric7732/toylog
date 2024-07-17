package org.example.toylog.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.follow.entity.Follow;
import org.example.toylog.domain.follow.service.FollowService;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.example.toylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/@{loginId}")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

//    @GetMapping
//    public String viewBlog(@PathVariable String loginId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        User blogOwner = userService.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("User not found"));
//        User currentUser = ((CustomUserDetails) userDetails).getUser();
//
//        boolean isFollowing = followService.isFollowing(currentUser, blogOwner);
//        long followersCount = followService.getFollowers(blogOwner).size();
//        long followingCount = followService.getFollowing(blogOwner).size();
//
//        model.addAttribute("user", blogOwner);
//        model.addAttribute("isFollowing", isFollowing);
//        model.addAttribute("followersCount", followersCount);
//        model.addAttribute("followingCount", followingCount);
//
//        return "user/blog";
//    }

    @PostMapping("/follow")
    public String followUser(@PathVariable String loginId, @AuthenticationPrincipal UserDetails userDetails) {
        User follower = ((CustomUserDetails) userDetails).getUser();
        User following = userService.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("User not found"));
        followService.toggleFollow(follower, following);
        return "redirect:/@" + loginId;
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@PathVariable String loginId, @AuthenticationPrincipal UserDetails userDetails) {
        User follower = ((CustomUserDetails) userDetails).getUser();
        User following = userService.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("User not found"));
        followService.unfollowUser(follower, following);
        return "redirect:/@" + loginId;
    }

    @GetMapping("/followers")
    public String getFollowers(@PathVariable String loginId, Model model) {
        User user = userService.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> followers = followService.getFollowers(user);
        model.addAttribute("followers", followers);
        return "user/follower";
    }

    @GetMapping("/following")
    public String getFollowing(@PathVariable String loginId, Model model) {
        User user = userService.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> following = followService.getFollowing(user);
        model.addAttribute("following", following);
        return "user/following";
    }
}
