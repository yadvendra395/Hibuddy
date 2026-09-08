package com.hibuddy.hibuddy_backend.dto.savedpost;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SavedPostResponseDTO {

    private Long savedPostId;

    private Long postId;
    private String postContent;

    private Long userId;
    private String username;

    private LocalDateTime savedAt;
}