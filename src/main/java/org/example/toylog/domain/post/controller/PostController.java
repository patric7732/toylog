//package org.example.toylog.domain.post.controller;
//
//import ch.qos.logback.core.model.Model;
//import lombok.RequiredArgsConstructor;
//import org.example.toylog.domain.post.repository.PostRepository;
//import org.example.toylog.domain.user.repository.UserRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class PostController {
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    @GetMapping("/")
//    public String boardList(Model model) {
//        return "main";
//    }
//}
