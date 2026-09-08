package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.post.PostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    // 🔥 Create post
    PostResponseDTO createPost(Long userId, Post post);

    // 🔍 Get post by ID
    PostResponseDTO getPostById(Long postId);

    // 📦 Get posts of a user
    Page<PostResponseDTO> getUserPosts(Long userId, Pageable pageable);

    // 🌍 Get all posts
    Page<PostResponseDTO> getAllPosts(Pageable pageable);

    // 🔥 Feed posts
    Page<PostResponseDTO> getFeedPosts(Long userId, Pageable pageable);

    // ❌ Delete post
    void deletePost(Long postId);

    // 📊 Count posts
    long countUserPosts(Long userId);
}