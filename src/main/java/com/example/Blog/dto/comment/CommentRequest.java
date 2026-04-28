package com.example.Blog.dto.comment;

// DTO (Data Transfer Object) for creating comments and replies
// Purpose: Accepts comment data from client
public class CommentRequest {
    private String body;      // The comment text content
    private Long parentId;    // Optional - ID of parent comment (for nested replies)
                             // null = new comment, not null = reply to existing comment

    // Getters and setters
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
