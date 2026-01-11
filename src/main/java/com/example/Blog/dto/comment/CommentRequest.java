package com.example.Blog.dto.comment;

public class CommentRequest {
    private String body;
    private Long parentId; // optional, for nested replies

    // Getters and setters
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
