package org.example.toylog.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.user.dto.UserDto;
import org.example.toylog.domain.user.entity.Role;
import org.example.toylog.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
//@RequestMapping("/api")
public class SignUpController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("role", Role.values());
        return "user/signUpForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/signUpForm";
        }

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
