package com.example.Blog.service;

import com.example.Blog.common.PageResponse;
import com.example.Blog.dto.post.PostRequest;
import com.example.Blog.dto.post.PostResponse;
import com.example.Blog.model.post.Post;
import com.example.Blog.model.Users;
import com.example.Blog.repository.PostRepo;
import com.example.Blog.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    // Create post
    // Maps PostRequest DTO to Post entity, saves to DB, returns PostResponse DTO
    public PostResponse create(PostRequest req, String username) {
        Users author = userRepo.findByUserName(username);
        Post post = new Post();
        post.setTitle(req.getTitle());        // Map from DTO to entity
        post.setContent(req.getContent());
        post.setAuthor(author);

        Post saved = postRepo.save(post);
        // Map entity back to DTO for response
        return new PostResponse(saved.getId(), saved.getTitle(), saved.getContent(), author.getUserName());
    }

    // Get all posts with pagination
    // Uses JPA PageRequest for efficient database pagination
    // Maps Post entities to PostResponse DTOs using Stream API
    public PageResponse<PostResponse> getAll(int page, int size) {
        // JPA handles pagination at database level (efficient!)
        Page<Post> posts = postRepo.findAll(PageRequest.of(page, size));

        // Convert List<Post> to List<PostResponse> using functional programming
        List<PostResponse> content = posts.getContent().stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getContent(), p.getAuthor().getUserName()))
                .collect(Collectors.toList());

        // Wrap in PageResponse with pagination metadata
        return new PageResponse<>(content, page, size, posts.getTotalElements(), posts.getTotalPages());
    }

    // ✅ Get post by ID
    // Maps Post entity to PostResponse DTO
    public PostResponse getById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getUserName());
    }

    // Delete post (only owner can delete)
    // Authorization check using username from Principal
    public void delete(Long id, String username) {  //same
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getUserName().equals(username)) {
            throw new RuntimeException("You can only delete your own post");
        }
        postRepo.delete(post);
    }

    // WITHOUT DTOs & PAGINATION - SIMPLIFIED VERSION:
    // public Post create(Post post, String username) {
    //     Users author = userRepo.findByUserName(username);
    //     post.setAuthor(author);
    //     return postRepo.save(post);
    // }
    //
    // public List<Post> getAll() {
    //     return postRepo.findAll();
    // }
    //
    // public Post getById(Long id) {
    //     return postRepo.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Post not found"));
    // }
    //
    // public void delete(Long id, String username) {
    //     Post post = postRepo.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Post not found"));
    //     if (!post.getAuthor().getUserName().equals(username)) {
    //         throw new RuntimeException("You can only delete your own post");
    //     }
    //     postRepo.delete(post);
    // }
}
