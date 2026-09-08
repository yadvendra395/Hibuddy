package com.hibuddy.hibuddy_backend.dto.follow;

import lombok.Data;

@Data
public class FollowUserPreviewDTO {

    private Long userId;

    private String username;

    private String profilePic;
}