package org.example.toylog.domain.user.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<User> optionalUser = userService.findByLoginId(userDetails.getUsername());
            optionalUser.ifPresent(user -> model.addAttribute("user", user));
        }
        return "main";
    }

    @GetMapping("/@{loginId}")
    public String personalBlog(@PathVariable String loginId, Model model) {
        Optional<User> optionalUser = userService.findByLoginId(loginId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
        }
        return "user/blog";
    }
}