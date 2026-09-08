package com.hibuddy.hibuddy_backend.service;


import com.hibuddy.hibuddy_backend.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    Message sendMessage(Long senderId, Long receiverId, String content);

    Page<Message> getConversation(Long user1Id, Long user2Id, Pageable pageable);

    Page<Message> getInbox(Long userId, Pageable pageable);

    long getUnreadCount(Long userId);

    void markAsRead(Long messageId);

    void markConversationAsRead(Long userId, Long senderId);
}
