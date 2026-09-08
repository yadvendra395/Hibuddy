package com.hibuddy.hibuddy_backend.dto.follow;

import lombok.Data;

@Data
public class FollowStatusDTO {

    private boolean following;

    private Long followerId;

    private Long targetUserId;

    private String message;
}