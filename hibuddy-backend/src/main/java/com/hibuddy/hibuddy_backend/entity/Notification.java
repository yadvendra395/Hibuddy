package com.hibuddy.hibuddy_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hibuddy.hibuddy_backend.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"receiver", "sender"})
@Entity
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_receiver_id", columnList = "receiver_id"),
                @Index(name = "idx_receiver_read", columnList = "receiver_id, is_read"),
                @Index(name = "idx_receiver_created", columnList = "receiver_id, createdAt")
        }
)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = true)
    private User sender;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private boolean isRead = false;

    private Long referenceId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}