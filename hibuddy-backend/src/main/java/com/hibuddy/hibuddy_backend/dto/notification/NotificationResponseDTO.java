package com.hibuddy.hibuddy_backend.dto.notification;

import com.hibuddy.hibuddy_backend.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {

    private Long notificationId;

    private Long senderId;
    private String senderUsername;

    private String message;

    private NotificationType type;

    private boolean isRead;

    private Long referenceId;

    private LocalDateTime createdAt;
}