package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.ApiResponse;
import com.hibuddy.hibuddy_backend.dto.savedpost.SavedPostResponseDTO;
import com.hibuddy.hibuddy_backend.service.SavedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

    private final SavedPostService savedPostService;

    // 🔥 Save post
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> savePost(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId) {

        savedPostService.savePost(userId, postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post saved successfully", null)
        );
    }

    // ❌ Unsave post
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> unsavePost(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId) {

        savedPostService.unsavePost(userId, postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post unsaved successfully", null)
        );
    }

    // 📦 Get saved posts (DTO based + pagination)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SavedPostResponseDTO>>> getSavedPosts(
            @PathVariable("userId") Long userId,
            Pageable pageable) {

        Page<SavedPostResponseDTO> savedPosts =
                savedPostService.getSavedPosts(userId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Saved posts fetched successfully", savedPosts)
        );
    }

    // 🔍 Check if post is saved
    @GetMapping("/check/{postId}")
    public ResponseEntity<ApiResponse<Boolean>> isPostSaved(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId) {

        boolean isSaved = savedPostService.isPostSaved(userId, postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Checked successfully", isSaved)
        );
    }
}