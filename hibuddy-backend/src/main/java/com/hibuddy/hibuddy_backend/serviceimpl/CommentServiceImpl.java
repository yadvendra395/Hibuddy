package com.hibuddy.hibuddy_backend.serviceimpl;


import com.hibuddy.hibuddy_backend.dto.comment.CommentRequestDTO;
import com.hibuddy.hibuddy_backend.dto.comment.CommentResponseDTO;
import com.hibuddy.hibuddy_backend.dto.comment.ReplyRequestDTO;
import com.hibuddy.hibuddy_backend.entity.Comment;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.repository.CommentRepository;
import com.hibuddy.hibuddy_backend.repository.PostRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.CommentService;
import com.hibuddy.hibuddy_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private NotificationService notificationService;

    // helper mapper
    private CommentResponseDTO mapToDTO(Comment c) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setCommentId(c.getCommentId());
        dto.setUserId(c.getUser().getUserId());
        dto.setUsername(c.getUser().getUsername());
        dto.setPostId(c.getPost().getPostId());
        dto.setText(c.getText());
        dto.setParentCommentId(
                c.getParentComment() != null ? c.getParentComment().getCommentId() : null
        );
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }

    // 💬 Add comment
    @Override
    public CommentResponseDTO addComment(CommentRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(dto.getText());

        Comment saved = commentRepository.save(comment);

        // 🔔 Notification
        if (!user.getUserId().equals(post.getUser().getUserId())) {
            notificationService.createCommentNotification(
                    user.getUserId(),            // sender
                    post.getUser().getUserId(), // receiver
                    post.getPostId()            // postId
            );
        }

        return mapToDTO(saved);
    }

    // 🔥 Reply
    @Override
    public CommentResponseDTO replyToComment(ReplyRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment parent = commentRepository.findById(dto.getParentCommentId())
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        Comment reply = new Comment();
        reply.setUser(user);
        reply.setPost(post);
        reply.setText(dto.getText());
        reply.setParentComment(parent);

        Comment saved = commentRepository.save(reply);

        if (!user.getUserId().equals(parent.getUser().getUserId())) {
                    notificationService.createCommentNotification(
                            user.getUserId(),            // sender
                            post.getUser().getUserId(), // receiver
                            post.getPostId()            // postId
            );
        }

        return mapToDTO(saved);
    }

    // 📦 Get comments
    @Override
    public Page<CommentResponseDTO> getPostComments(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPostOrderByCreatedAtDesc(post, pageable)
                .map(this::mapToDTO);
    }

    // 📦 Get replies
    @Override
    public Page<CommentResponseDTO> getReplies(Long parentCommentId, Pageable pageable) {
        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentRepository.findByParentComment(parent, pageable)
                .map(this::mapToDTO);
    }

    // ❌ Delete
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }

    // 📊 Count
    @Override
    public long countComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.countByPost(post);
    }
}