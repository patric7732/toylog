package org.example.toylog.domain.user.controller;

import org.example.toylog.domain.user.dto.JwtRequest;
import org.example.toylog.domain.user.dto.JwtResponse;
import org.example.toylog.domain.user.dto.UserDto;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController@RequestMapping("/api/users")public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User newUser = userService.registerUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = userService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}