package com.hibuddy.hibuddy_backend.dto.comment;

import lombok.Data;

@Data
public class ReplyRequestDTO {
    private Long userId;
    private Long postId;
    private Long parentCommentId;
    private String text;
}