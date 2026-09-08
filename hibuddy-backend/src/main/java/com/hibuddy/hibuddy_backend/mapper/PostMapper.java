package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.post.PostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Post;

public class PostMapper {

    public static PostResponseDTO toDTO(Post post) {

        PostResponseDTO dto = new PostResponseDTO();

        dto.setPostId(post.getPostId());
        dto.setContent(post.getCaption());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());

        dto.setUserId(post.getUser().getUserId());
        dto.setUsername(post.getUser().getUsername());

        dto.setLikeCount(post.getLikes() != null ? post.getLikes().size() : 0);
        dto.setCommentCount(post.getComments() != null ? post.getComments().size() : 0);

        return dto;
    }
}