package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Message;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.enums.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        SELECT m
        FROM Message m
        WHERE (m.sender = :user1 AND m.receiver = :user2)
           OR (m.sender = :user2 AND m.receiver = :user1)
        ORDER BY m.sentAt DESC
    """)
    Page<Message> findConversation(
            User user1,
            User user2,
            Pageable pageable
    );

    Page<Message> findByReceiver(
            User receiver,
            Pageable pageable
    );

    List<Message> findByReceiverAndStatus(
            User receiver,
            MessageStatus status
    );

    @Query("""
        SELECT COUNT(m)
        FROM Message m
        WHERE m.receiver.userId = :userId
        AND m.status = 'SENT'
    """)
    long countUnreadMessages(Long userId);
}