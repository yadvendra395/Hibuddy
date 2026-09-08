package com.hibuddy.hibuddy_backend.dto.like;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeResponseDTO {

    private Long likeId;

    private Long postId;

    private Long userId;
    private String username;

    private LocalDateTime likedAt;
}