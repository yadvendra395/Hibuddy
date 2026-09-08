package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.notification.NotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    void createLikeNotification(Long senderId, Long receiverId, Long postId);

    void createCommentNotification(Long senderId, Long receiverId, Long postId);

    void createFollowNotification(Long senderId, Long receiverId);

    void createMessageNotification(Long senderId, Long receiverId, Long messageId);

    Page<NotificationResponseDTO> getUserNotifications(Long userId, Pageable pageable);

    long getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    void deleteNotification(Long notificationId);
}