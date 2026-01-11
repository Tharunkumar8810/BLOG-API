package com.example.Blog.controller;

import com.example.Blog.common.PageResponse;
import com.example.Blog.dto.post.PostRequest;
import com.example.Blog.dto.post.PostResponse;
import com.example.Blog.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a new post
    @PostMapping
    public PostResponse create(@RequestBody PostRequest req, Principal principal) {
        return postService.create(req, principal.getName());
    }

    // Get all posts with pagination
    @GetMapping
    public PageResponse<PostResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return postService.getAll(page, size);
    }

    // ✅ Get post by ID
    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    // Delete a post (only owner can delete)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        postService.delete(id, principal.getName());
        return "Post deleted successfully";
    }
}
