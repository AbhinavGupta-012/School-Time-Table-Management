package com.krishan.school.timetable.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.krishan.school.timetable.entity.User;
import com.krishan.school.timetable.repository.UserRepository;
import com.krishan.school.timetable.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registration
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Auto-assign roles
        if (userRepository.count() == 0) {
            user.setRole(User.Role.ADMIN); // First user becomes admin
        } else {
            user.setRole(User.Role.TEACHER); // Rest are teachers
        }

        userRepository.save(user);
        return "User registered successfully with role: " + user.getRole();
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return jwtService.generateToken(dbUser.getUsername(), dbUser.getRole().name());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
