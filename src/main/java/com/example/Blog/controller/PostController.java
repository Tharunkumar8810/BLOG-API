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
    // Uses PostRequest DTO to accept only title/content from client
    // Returns PostResponse DTO with generated ID and author info
    @PostMapping
    public PostResponse create(@RequestBody PostRequest req, Principal principal) {
        return postService.create(req, principal.getName());  //same
    }

    // Get all posts with pagination
    // Uses PageResponse<PostResponse> to return paginated data + metadata
    // Query params: page (default 0), size (default 10)
    // Example: GET /api/posts?page=1&size=5
    @GetMapping
    public PageResponse<PostResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return postService.getAll(page, size);
    }

    // ✅ Get post by ID
    // Returns single PostResponse DTO
    @GetMapping("/{id}")    //same
    public PostResponse getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    // Delete a post (only owner can delete)
    // Uses Principal to verify ownership
    @DeleteMapping("/{id}")     //same
    public String delete(@PathVariable Long id, Principal principal) {
        postService.delete(id, principal.getName());
        return "Post deleted successfully";
    }

    // WITHOUT DTOs & PAGINATION - SIMPLIFIED VERSION:
    // @PostMapping
    // public Post create(@RequestBody Post post, Principal principal) {
    //     return postService.create(post, principal.getName());
    // }
    //
    // @GetMapping
    // public List<Post> getAll() {
    //     return postService.getAll();
    // }
    //
    // @GetMapping("/{id}")
    // public Post getById(@PathVariable Long id) {
    //     return postService.getById(id);
    // }
    //
    // @DeleteMapping("/{id}")
    // public String delete(@PathVariable Long id, Principal principal) {
    //     postService.delete(id, principal.getName());
    //     return "Post deleted successfully";
    // }
}
