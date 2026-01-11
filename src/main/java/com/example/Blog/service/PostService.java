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
    public PostResponse create(PostRequest req, String username) {
        Users author = userRepo.findByUserName(username);
        Post post = new Post();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setAuthor(author);

        Post saved = postRepo.save(post);
        return new PostResponse(saved.getId(), saved.getTitle(), saved.getContent(), author.getUserName());
    }

    // Get all posts with pagination
    public PageResponse<PostResponse> getAll(int page, int size) {
        Page<Post> posts = postRepo.findAll(PageRequest.of(page, size));

        List<PostResponse> content = posts.getContent().stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getContent(), p.getAuthor().getUserName()))
                .collect(Collectors.toList());

        return new PageResponse<>(content, page, size, posts.getTotalElements(), posts.getTotalPages());
    }

    // ✅ Get post by ID
    public PostResponse getById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getUserName());
    }

    // Delete post (only owner can delete)
    public void delete(Long id, String username) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getUserName().equals(username)) {
            throw new RuntimeException("You can only delete your own post");
        }
        postRepo.delete(post);
    }
}
