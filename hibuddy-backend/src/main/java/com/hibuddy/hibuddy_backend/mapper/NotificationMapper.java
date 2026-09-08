package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.notification.NotificationResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponseDTO toDTO(Notification notification) {

        NotificationResponseDTO dto = new NotificationResponseDTO();

        dto.setNotificationId(notification.getNotificationId());

        // sender can be null (system notifications)
        if (notification.getSender() != null) {
            dto.setSenderId(notification.getSender().getUserId());
            dto.setSenderUsername(notification.getSender().getUsername());
        }

        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setRead(notification.isRead());
        dto.setReferenceId(notification.getReferenceId());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}