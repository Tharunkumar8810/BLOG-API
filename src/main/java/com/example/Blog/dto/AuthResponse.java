package com.example.Blog.dto;

public class AuthResponse {
    private String token;
    private String userName;

    public AuthResponse(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

    public String getToken() { return token; }
    public String getUserName() { return userName; }
}
