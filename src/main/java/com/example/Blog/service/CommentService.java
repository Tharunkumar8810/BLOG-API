package com.example.Blog.service;

import com.example.Blog.dto.comment.CommentRequest;
import com.example.Blog.dto.comment.CommentResponse;
import com.example.Blog.model.comment.Comment;
import com.example.Blog.model.post.Post;
import com.example.Blog.model.Users;
import com.example.Blog.repository.CommentRepo;
import com.example.Blog.repository.PostRepo;
import com.example.Blog.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public CommentService(CommentRepo commentRepo, PostRepo postRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    // Create comment (or reply)  //same
    public CommentResponse create(Long postId, CommentRequest req, String username) {
        Users author = userRepo.findByUserName(username);
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setBody(req.getBody());
        comment.setAuthor(author);
        comment.setPost(post);

        if (req.getParentId() != null) {
            Comment parent = commentRepo.findById(req.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParent(parent);
        }

        Comment saved = commentRepo.save(comment);
        return new CommentResponse(saved.getId(), saved.getBody(), author.getUserName(), List.of());
    }

    // Get all comments for a post (nested)
    public List<CommentResponse> getByPost(Long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream()
                .filter(c -> c.getParent() == null) // only top-level
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Delete comment (only owner can delete)  //same
    public void delete(Long id, String username) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAuthor().getUserName().equals(username)) {
            throw new RuntimeException("You can only delete your own comment");
        }
        commentRepo.delete(comment);
    }

    // WITHOUT DTOs - SIMPLIFIED VERSION:
    // public Comment create(Long postId, Comment comment, String username) {
    //     Users author = userRepo.findByUserName(username);
    //     Post post = postRepo.findById(postId)
    //             .orElseThrow(() -> new RuntimeException("Post not found"));
    //
    //     comment.setAuthor(author);
    //     comment.setPost(post);
    //
    //     if (comment.getParent() != null) {
    //         Comment parent = commentRepo.findById(comment.getParent().getId())
    //                 .orElseThrow(() -> new RuntimeException("Parent comment not found"));
    //         comment.setParent(parent);
    //     }
    //
    //     return commentRepo.save(comment);
    // }
    //
    // public List<Comment> getByPost(Long postId) {
    //     return commentRepo.findByPostId(postId);
    // }
    //
    // public void delete(Long id, String username) {
    //     Comment comment = commentRepo.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Comment not found"));
    //
    //     if (!comment.getAuthor().getUserName().equals(username)) {
    //         throw new RuntimeException("You can only delete your own comment");
    //     }
    //     commentRepo.delete(comment);
    // }

    // Helper: map entity → response recursively
    private CommentResponse mapToResponse(Comment comment) {
        List<CommentResponse> children = comment.getChildren().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new CommentResponse(comment.getId(), comment.getBody(),
                comment.getAuthor().getUserName(), children);
    }
}
