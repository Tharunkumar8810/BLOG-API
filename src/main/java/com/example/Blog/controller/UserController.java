package com.example.Blog.controller;

import com.example.Blog.model.Users;
import com.example.Blog.dto.AuthResponse;
import com.example.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Users user) {
        String token = service.verify(user);
        return new AuthResponse(token, user.getUserName());
    }
}
