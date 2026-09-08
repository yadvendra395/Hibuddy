package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.savedpost.SavedPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SavedPostService {

    void savePost(Long userId, Long postId);

    void unsavePost(Long userId, Long postId);

    Page<SavedPostResponseDTO> getSavedPosts(Long userId, Pageable pageable); // ✅ FIXED

    boolean isPostSaved(Long userId, Long postId);
}