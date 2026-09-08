package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.ApiResponse;
import com.hibuddy.hibuddy_backend.dto.post.PostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 🔥 Create post
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> createPost(
            @PathVariable Long userId,
            @RequestBody Post post) {

        PostResponseDTO createdPost = postService.createPost(userId, post);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post created successfully", createdPost)
        );
    }

    // 🔍 Get post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPostById(
            @PathVariable Long postId) {

        PostResponseDTO post = postService.getPostById(postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post fetched successfully", post)
        );
    }

    // 📦 Get user posts
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<PostResponseDTO>>> getUserPosts(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<PostResponseDTO> posts = postService.getUserPosts(userId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User posts fetched successfully", posts)
        );
    }

    // 🌍 Get all posts (global feed)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponseDTO>>> getAllPosts(
            Pageable pageable) {

        Page<PostResponseDTO> posts = postService.getAllPosts(pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All posts fetched successfully", posts)
        );
    }

    // 🔥 Feed posts (personalized)
    @GetMapping("/feed/{userId}")
    public ResponseEntity<ApiResponse<Page<PostResponseDTO>>> getFeedPosts(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<PostResponseDTO> posts = postService.getFeedPosts(userId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Feed posts fetched successfully", posts)
        );
    }

    // ❌ Delete post
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId) {

        postService.deletePost(postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post deleted successfully", null)
        );
    }

    // 📊 Count user posts
    @GetMapping("/count/{userId}")
    public ResponseEntity<ApiResponse<Long>> countUserPosts(
            @PathVariable Long userId) {

        long count = postService.countUserPosts(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post count fetched successfully", count)
        );
    }
}