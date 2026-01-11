package com.example.Blog.dto.post;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName;

    // Constructor
    public PostResponse(Long id, String title, String content, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorName() { return authorName; }
}
