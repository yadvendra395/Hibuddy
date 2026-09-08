package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.message.MessageResponseDTO;
import com.hibuddy.hibuddy_backend.dto.message.SendMessageRequestDTO;
import com.hibuddy.hibuddy_backend.entity.Message;
import com.hibuddy.hibuddy_backend.mapper.MessageMapper;
import com.hibuddy.hibuddy_backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Send Message
    @PostMapping("/send/{senderId}")
    public ResponseEntity<MessageResponseDTO> sendMessage(
            @PathVariable Long senderId,
            @RequestBody SendMessageRequestDTO request
    ) {

        Message message = messageService.sendMessage(
                senderId,
                request.getReceiverId(),
                request.getContent()
        );

        return ResponseEntity.ok(
                MessageMapper.toDTO(message)
        );
    }

    // Get Conversation
    @GetMapping("/conversation")
    public ResponseEntity<Page<MessageResponseDTO>> getConversation(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id,
            Pageable pageable
    ) {

        Page<MessageResponseDTO> conversation =
                messageService.getConversation(user1Id, user2Id, pageable)
                        .map(MessageMapper::toDTO);

        return ResponseEntity.ok(conversation);
    }

    // Inbox
    @GetMapping("/inbox/{userId}")
    public ResponseEntity<Page<MessageResponseDTO>> getInbox(
            @PathVariable Long userId,
            Pageable pageable
    ) {

        Page<MessageResponseDTO> inbox =
                messageService.getInbox(userId, pageable)
                        .map(MessageMapper::toDTO);

        return ResponseEntity.ok(inbox);
    }

    // Unread Count
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                messageService.getUnreadCount(userId)
        );
    }

    // Mark Single Message Read
    @PatchMapping("/{messageId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long messageId
    ) {

        messageService.markAsRead(messageId);

        return ResponseEntity.ok(
                "Message marked as read"
        );
    }

    // Mark Conversation Read
    @PatchMapping("/conversation/read")
    public ResponseEntity<String> markConversationAsRead(
            @RequestParam Long userId,
            @RequestParam Long senderId
    ) {

        messageService.markConversationAsRead(
                userId,
                senderId
        );

        return ResponseEntity.ok(
                "Conversation marked as read"
        );
    }
}