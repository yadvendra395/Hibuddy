package com.hibuddy.hibuddy_backend.serviceimpl;
import com.hibuddy.hibuddy_backend.entity.Message;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.enums.MessageStatus;
import com.hibuddy.hibuddy_backend.repository.MessageRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // 📩 Send message
    @Override
    public Message sendMessage(Long senderId, Long receiverId, String content) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setStatus(MessageStatus.SENT);

        return messageRepository.save(message);
    }

    // 💬 Conversation
    @Override
    public Page<Message> getConversation(Long user1Id, Long user2Id, Pageable pageable) {

        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User1 not found"));

        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        return messageRepository.findConversation(user1, user2, pageable);
    }

    // 📥 Inbox (received messages)
    @Override
    public Page<Message> getInbox(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return messageRepository.findByReceiver(user, pageable);
    }

    // 🔴 Unread count
    @Override
    public long getUnreadCount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> unreadMessages =
                messageRepository.findByReceiverAndStatus(user, MessageStatus.SENT);

        return unreadMessages.size();
    }

    // ✅ Mark single message as read
    @Override
    public void markAsRead(Long messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setStatus(MessageStatus.READ);
        messageRepository.save(message);
    }

    // ✅ Mark full conversation as read
    @Override
    public void markConversationAsRead(Long userId, Long senderId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        List<Message> messages =
                messageRepository.findByReceiverAndStatus(user, MessageStatus.SENT);

        for (Message message : messages) {
            if (message.getSender().getUserId().equals(sender.getUserId())) {
                message.setStatus(MessageStatus.READ);
                messageRepository.save(message);
            }
        }
    }
}