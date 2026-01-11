package com.example.Blog.controller;

import com.example.Blog.dto.comment.CommentRequest;
import com.example.Blog.dto.comment.CommentResponse;
import com.example.Blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create a new comment (or reply)
    @PostMapping
    public CommentResponse create(@PathVariable Long postId,
                                  @RequestBody CommentRequest req,
                                  Principal principal) {
        return commentService.create(postId, req, principal.getName());
    }

    // Get all comments for a post (nested)
    @GetMapping
    public List<CommentResponse> getByPost(@PathVariable Long postId) {
        return commentService.getByPost(postId);
    }

    // Delete a comment (only owner can delete)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long postId,
                         @PathVariable Long id,
                         Principal principal) {
        commentService.delete(id, principal.getName());
        return "Comment deleted successfully";
    }
}
