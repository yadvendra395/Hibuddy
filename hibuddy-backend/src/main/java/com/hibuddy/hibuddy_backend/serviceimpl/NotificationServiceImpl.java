package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.notification.NotificationResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Notification;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.enums.NotificationType;
import com.hibuddy.hibuddy_backend.exception.ResourceNotFoundException;
import com.hibuddy.hibuddy_backend.mapper.NotificationMapper;
import com.hibuddy.hibuddy_backend.repository.NotificationRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    // =========================
    // CREATE NOTIFICATION


    private void createNotification(

            Long senderId,
            Long receiverId,
            NotificationType type,
            Long referenceId,
            String message) {
        if (senderId.equals(receiverId)) {
            return;
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(type);
        notification.setReferenceId(referenceId);
        notification.setMessage(message);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public void createLikeNotification(Long senderId, Long receiverId, Long postId) {

        String username = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"))
                .getUsername();

        createNotification(
                senderId,
                receiverId,
                NotificationType.LIKE,
                postId,
                username + " liked your post."
        );
    }


    @Override
    public void createCommentNotification(Long senderId, Long receiverId, Long postId) {

        String username = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"))
                .getUsername();

        createNotification(
                senderId,
                receiverId,
                NotificationType.COMMENT,
                postId,
                username + " commented on your post."
        );
    }

    @Override
    public void createFollowNotification(Long senderId, Long receiverId) {

        String username = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"))
                .getUsername();

        createNotification(
                senderId,
                receiverId,
                NotificationType.FOLLOW,
                null,
                username + " started following you."
        );
    }

    @Override
    public void createMessageNotification(Long senderId, Long receiverId, Long messageId) {

        String username = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"))
                .getUsername();

        createNotification(
                senderId,
                receiverId,
                NotificationType.MESSAGE,
                messageId,
                username + " sent you a message."
        );
    }

    // =========================
    // GET NOTIFICATIONS (DTO)
    // =========================
    @Override
    public Page<NotificationResponseDTO> getUserNotifications(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return notificationRepository
                .findByReceiverOrderByCreatedAtDesc(user, pageable)
                .map(notificationMapper::toDTO);
    }
    // =========================
    // UNREAD COUNT
    // =========================
    @Override
    public long getUnreadCount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return notificationRepository.countByReceiverAndIsReadFalse(user);
    }

    // =========================
    // MARK ONE AS READ
    // =========================
    @Transactional
    @Override
    public void markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }


    // =========================
    // MARK ALL AS READ
    // =========================
    @Override
    @Transactional
    public void markAllAsRead(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        notificationRepository.markAllAsRead(user);
    }

    @Transactional
    @Override
    public void deleteNotification(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found"));

        notificationRepository.delete(notification);
    }
}