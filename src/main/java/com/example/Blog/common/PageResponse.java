package com.example.Blog.common;

import java.util.List;

// Generic wrapper for paginated API responses
// Purpose: Provides pagination metadata along with data
// Generic <T> allows reuse for any type (PostResponse, CommentResponse, etc.)
public class PageResponse<T> {
    private List<T> content;        // The actual data items for this page
    private int page;               // Current page number (0-based)
    private int size;               // Number of items per page
    private long totalElements;     // Total items in database (all pages)
    private int totalPages;         // Total number of pages available

    // Constructor - creates pagination response
    public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    // Getters - frontend uses these for pagination UI
    public List<T> getContent() { return content; }           // Data to display
    public int getPage() { return page; }                     // Current page
    public int getSize() { return size; }                     // Items per page
    public long getTotalElements() { return totalElements; }  // Total in DB
    public int getTotalPages() { return totalPages; }         // Total pages
}
