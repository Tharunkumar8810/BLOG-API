package com.example.Blog.service;

import com.example.Blog.model.Users;
import com.example.Blog.repository.UserRepo;
import com.example.Blog.security.JwtService;
import com.example.Blog.dto.AuthResponse;  // Add import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Modified: Now returns AuthResponse with JWT token for auto-login
    public AuthResponse register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Users savedUser = repo.save(user);

        // Generate JWT token immediately after registration
        String token = jwtService.generateToken(savedUser.getUserName());

        // Return token and username for auto-login
        return new AuthResponse(token, savedUser.getUserName());
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        }
        throw new RuntimeException("Login failed");
    }
}
