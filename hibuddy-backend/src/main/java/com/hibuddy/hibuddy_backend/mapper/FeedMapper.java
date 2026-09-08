package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.feedpost.FeedPostDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class FeedMapper {
    public FeedPostDTO mapToDTO(Post post) {

        FeedPostDTO dto = new FeedPostDTO();

        dto.setPostId(post.getPostId());
        dto.setUserId(post.getUser().getUserId());
        dto.setUsername(post.getUser().getUsername());
        dto.setCaption(post.getCaption());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());

        return dto;
    }
}
