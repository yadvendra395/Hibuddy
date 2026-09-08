package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.like.LikeResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Like;

public class LikeMapper {

    public static LikeResponseDTO toDTO(Like like) {

        if (like == null) return null;

        LikeResponseDTO dto = new LikeResponseDTO();

        dto.setLikeId(like.getLikeId());

        // post mapping
        if (like.getPost() != null) {
            dto.setPostId(like.getPost().getPostId());
        }

        // user mapping
        if (like.getUser() != null) {
            dto.setUserId(like.getUser().getUserId());
            dto.setUsername(like.getUser().getUsername());
        }

        dto.setLikedAt(like.getLikedAt());

        return dto;
    }
}