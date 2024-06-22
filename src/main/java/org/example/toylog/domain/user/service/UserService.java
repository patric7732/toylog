package org.example.toylog.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.user.dto.JwtRequest;
import org.example.toylog.domain.user.dto.JwtResponse;
import org.example.toylog.domain.user.dto.UserDto;
import org.example.toylog.domain.user.entity.Role;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.repository.UserRepository;
import org.example.toylog.global.security.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @Transactional
    public User registerUser(UserDto userDto) {
        if (userRepository.existsByUserId(userDto.getUserId()) || userRepository.existsByUserId(userDto.getEmail())) {
            throw new RuntimeException("Username or email already exists");
        }

        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    public JwtResponse login(JwtRequest jwtRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}