package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.follow.FollowUserPreviewDTO;
import com.hibuddy.hibuddy_backend.entity.User;

public class FollowMapper {

    public static FollowUserPreviewDTO toUserPreview(User user) {
        FollowUserPreviewDTO dto = new FollowUserPreviewDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setProfilePic(user.getProfileImageUrl());
        return dto;
    }
}