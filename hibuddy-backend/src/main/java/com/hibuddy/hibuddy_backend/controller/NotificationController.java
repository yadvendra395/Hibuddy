package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.ApiResponse;
import com.hibuddy.hibuddy_backend.dto.notification.NotificationResponseDTO;
import com.hibuddy.hibuddy_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 🔔 Get notifications of a user
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Page<NotificationResponseDTO>>> getUserNotifications(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<NotificationResponseDTO> notifications =
                notificationService.getUserNotifications(userId, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notifications fetched successfully",
                        notifications
                )
        );
    }

    // 📌 Get unread notification count
    @GetMapping("/users/{userId}/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(
            @PathVariable Long userId) {

        long count = notificationService.getUnreadCount(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Unread notification count fetched successfully ",
                        count
                )
        );
    }

    // ✅ Mark one notification as read
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification marked as read",
                        null
                )
        );
    }

    // ✅ Mark all notifications as read
    @PutMapping("/users/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "All notifications marked as read",
                        null
                )
        );
    }

    // ❌ Delete notification (Optional)
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long notificationId) {

        notificationService.deleteNotification(notificationId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification deleted successfully",
                        null
                )
        );
    }
}