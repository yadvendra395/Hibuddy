package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.comment.CommentRequestDTO;
import com.hibuddy.hibuddy_backend.dto.comment.CommentResponseDTO;
import com.hibuddy.hibuddy_backend.dto.comment.ReplyRequestDTO;
import com.hibuddy.hibuddy_backend.service.CommentService;
import com.hibuddy.hibuddy_backend.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 💬 Add comment
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDTO>> addComment(
            @Valid @RequestBody CommentRequestDTO dto
    ) {
        CommentResponseDTO response = commentService.addComment(dto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comment added successfully", response)
        );
    }

    // 🔥 Reply to comment
    @PostMapping("/reply")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> replyToComment(
            @Valid @RequestBody ReplyRequestDTO dto
    ) {
        CommentResponseDTO response = commentService.replyToComment(dto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Reply added successfully", response)
        );
    }

    // 📦 Get comments of a post (paginated)
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<Page<CommentResponseDTO>>> getPostComments(
            @PathVariable Long postId,
            Pageable pageable
    ) {
        Page<CommentResponseDTO> response = commentService.getPostComments(postId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comments fetched successfully", response)
        );
    }

    // 📦 Get replies of a comment
    @GetMapping("/replies/{parentCommentId}")
    public ResponseEntity<ApiResponse<Page<CommentResponseDTO>>> getReplies(
            @PathVariable Long parentCommentId,
            Pageable pageable
    ) {
        Page<CommentResponseDTO> response = commentService.getReplies(parentCommentId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Replies fetched successfully", response)
        );
    }

    // ❌ Delete comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comment deleted successfully", null)
        );
    }

    // 📊 Count comments
    @GetMapping("/count/{postId}")
    public ResponseEntity<ApiResponse<Long>> countComments(
            @PathVariable Long postId
    ) {
        long count = commentService.countComments(postId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comment count fetched", count)
        );
    }
}