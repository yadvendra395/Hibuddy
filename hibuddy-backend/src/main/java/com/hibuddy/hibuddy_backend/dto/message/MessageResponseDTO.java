package com.hibuddy.hibuddy_backend.dto.message;

import com.hibuddy.hibuddy_backend.enums.MessageStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDTO {

    private Long messageId;

    private Long senderId;
    private String senderUsername;

    private Long receiverId;
    private String receiverUsername;

    private String content;

    private MessageStatus status;

    private LocalDateTime sentAt;
}