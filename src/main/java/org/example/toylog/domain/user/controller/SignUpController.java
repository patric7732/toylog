package org.example.toylog.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.user.dto.UserDto;
import org.example.toylog.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SignUpController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/signUpForm";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userDto") UserDto userDto, Model model) {
        if (userService.findByLoginId(userDto.getLoginId()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "user/signUpForm";
        }

        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered.");
            return "user/signUpForm";
        }

        userService.signUp(userDto);

        return "redirect:/login";
    }

}
