package org.example.toylog.domain.follow.controller;

import java.util.Optional;
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

    @PostMapping("/toggleFollow")
    public String toggleFollow(@PathVariable String loginId, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = ((CustomUserDetails) userDetails).getUser();
        Optional<User> userOpt = userService.findByLoginId(loginId);
        if (userOpt.isPresent() && !userOpt.get().equals(currentUser)) {
            User user = userOpt.get();
            if (followService.isFollowing(user, currentUser)) {
                followService.unfollowUser(user, currentUser);
            } else {
                followService.toggleFollow(user, currentUser);
            }
        }
        return "redirect:/@" + loginId;
    }

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
        model.addAttribute("user", user);
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
