package com.hibuddy.hibuddy_backend.entity;

import com.hibuddy.hibuddy_backend.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_sender_id", columnList = "sender_id"),
        @Index(name = "idx_receiver_id", columnList = "receiver_id"),
        @Index(name = "idx_sent_at", columnList = "sentAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sender", "receiver"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status = MessageStatus.SENT;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;
    @Column(nullable = false, updatable = false)
    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }

}


