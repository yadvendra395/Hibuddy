package com.hibuddy.hibuddy_backend.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponseDTO {

    private Long commentId;

    private String text;

    private LocalDateTime createdAt;

    private Long userId;

    private String username;

    private Long postId;

    private Long parentCommentId;

    private List<CommentResponseDTO> replies;
}