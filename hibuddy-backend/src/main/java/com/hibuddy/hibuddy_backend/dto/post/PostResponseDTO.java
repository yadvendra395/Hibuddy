package com.hibuddy.hibuddy_backend.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {

    private Long postId;

    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    private Long userId;

    private String username;

    private long likeCount;

    private long commentCount;
}