package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.comment.CommentResponseDTO;
import com.hibuddy.hibuddy_backend.dto.comment.CommentRequestDTO;
import com.hibuddy.hibuddy_backend.dto.comment.ReplyRequestDTO;
import com.hibuddy.hibuddy_backend.entity.Comment;
import com.hibuddy.hibuddy_backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentResponseDTO addComment(CommentRequestDTO dto);

    CommentResponseDTO replyToComment(ReplyRequestDTO dto);

    Page<CommentResponseDTO> getPostComments(Long postId, Pageable pageable);

    Page<CommentResponseDTO> getReplies(Long parentCommentId, Pageable pageable);

    void deleteComment(Long commentId);


    long countComments(Long postId);
}