package com.hibuddy.hibuddy_backend.dto.comment;

import lombok.Data;

@Data
public class CommentRequestDTO {

    private String text;

    private Long postId;

    private Long parentCommentId; // null = top-level comment
    private Long userId;

}