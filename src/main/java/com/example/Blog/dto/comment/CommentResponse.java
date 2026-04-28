package com.example.Blog.dto.comment;

import java.util.List;

// DTO (Data Transfer Object) for returning comment data to client
// Purpose: Controls comment response format, supports nested/threaded replies
// Key Feature: Self-referencing structure for comment trees
public class CommentResponse {
    private Long id;
    private String body;
    private String authorName;
    private List<CommentResponse> children; // Nested replies (self-referencing)
    // Constructor - creates comment with its nested children
    // Used recursively to build comment trees
    public CommentResponse(Long id, String body, String authorName, List<CommentResponse> children) {
        this.id = id;
        this.body = body;
        this.authorName = authorName;
        this.children = children;
    }

    // Getters (no setters - immutable response object)
    public Long getId() { return id; }
    public String getBody() { return body; }
    public String getAuthorName() { return authorName; }
    public List<CommentResponse> getChildren() { return children; }  // Access nested replies
}
