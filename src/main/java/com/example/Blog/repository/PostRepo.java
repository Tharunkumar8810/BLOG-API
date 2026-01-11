package com.example.Blog.repository;

import com.example.Blog.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByAuthorUserName(String userName);
}
