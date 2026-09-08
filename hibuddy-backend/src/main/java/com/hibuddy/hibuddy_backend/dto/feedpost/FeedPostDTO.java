package com.hibuddy.hibuddy_backend.dto.feedpost;// ============================================
// FEED DTO
// ============================================

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostDTO {
    private Long postId;
    private Long userId;
    private String username;
    private String profilePic;
    private String caption;
    private String imageUrl;

    private long likeCount;
    private long commentCount;
    private boolean likedByCurrentUser;

    private LocalDateTime createdAt;
}