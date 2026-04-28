package com.example.Blog.dto.post;

// DTO (Data Transfer Object) for creating new posts
// Purpose: Accepts only necessary fields from client, hides internal details
public class PostRequest {
    private String title;
    private String content;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
