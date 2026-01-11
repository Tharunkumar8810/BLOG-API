package com.example.Blog.dto.comment;

import java.util.List;

public class CommentResponse {
    private Long id;
    private String body;
    private String authorName;
    private List<CommentResponse> children; // nested replies

    // Constructor
    public CommentResponse(Long id, String body, String authorName, List<CommentResponse> children) {
        this.id = id;
        this.body = body;
        this.authorName = authorName;
        this.children = children;
    }

    // Getters
    public Long getId() { return id; }
    public String getBody() { return body; }
    public String getAuthorName() { return authorName; }
    public List<CommentResponse> getChildren() { return children; }
}
