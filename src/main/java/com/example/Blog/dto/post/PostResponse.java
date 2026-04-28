package com.example.Blog.dto.post;

// DTO (Data Transfer Object) for returning post data to client
// Purpose: Controls what data is sent to frontend, adds custom fields like authorName
// Security: Doesn't expose sensitive internal data (passwords, internal relationships)
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName;  // Custom field - extracted from Users entity

    // Constructor - maps from Post entity to this DTO
    public PostResponse(Long id, String title, String content, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
    }

    // Getters (no setters - immutable response object)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorName() { return authorName; }
}
