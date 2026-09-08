package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.savedpost.SavedPostRequestDTO;
import com.hibuddy.hibuddy_backend.dto.savedpost.SavedPostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.SavedPost;

public class SavedPostMapper {

    // DTO → Entity
    public static SavedPost toEntity(SavedPostRequestDTO dto) {
        SavedPost savedPost = new SavedPost();
        return savedPost;
    }

    // Entity → DTO
    public static SavedPostResponseDTO toDTO(SavedPost savedPost) {

        SavedPostResponseDTO dto = new SavedPostResponseDTO();

        dto.setSavedPostId(savedPost.getId());
        dto.setPostId(savedPost.getPost().getPostId());
        dto.setPostContent(savedPost.getPost().getCaption());
        dto.setUserId(savedPost.getUser().getUserId());
        dto.setUsername(savedPost.getUser().getUsername());
        dto.setSavedAt(savedPost.getSavedAt());

        return dto;
    }
}