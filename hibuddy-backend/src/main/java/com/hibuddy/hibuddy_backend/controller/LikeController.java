package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // Like a post
    @PostMapping("/posts/{postId}/users/{userId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        likeService.likePost(userId, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    // Unlike a post
    @DeleteMapping("/posts/{postId}/users/{userId}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        likeService.unlikePost(userId, postId);
        return ResponseEntity.ok("Post unliked successfully");
    }

    // Check if user liked post
    @GetMapping("/posts/{postId}/users/{userId}/status")
    public ResponseEntity<Boolean> isPostLiked(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(
                likeService.isPostLiked(userId, postId)
        );
    }

    // Count likes
    @GetMapping("/posts/{postId}/count")
    public ResponseEntity<Long> countLikes(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(
                likeService.countLikes(postId)
        );
    }
}