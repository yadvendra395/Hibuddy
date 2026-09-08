package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.comment.CommentResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Comment;

public class CommentMapper {

    public static CommentResponseDTO toDTO(Comment comment) {

        CommentResponseDTO dto = new CommentResponseDTO();

        dto.setCommentId(comment.getCommentId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());

        dto.setUserId(comment.getUser().getUserId());
        dto.setUsername(comment.getUser().getUsername());

        dto.setPostId(comment.getPost().getPostId());

        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getCommentId());
        }

        // ⚠️ NO RECURSION HERE (important for production)
        dto.setReplies(null);

        return dto;
    }
}