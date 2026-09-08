package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Notification;
import com.hibuddy.hibuddy_backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByReceiverOrderByCreatedAtDesc(User receiver, Pageable pageable);

    List<Notification> findByReceiverAndIsReadFalse(User receiver);

    long countByReceiverAndIsReadFalse(User receiver);
    @Modifying
    @Transactional
    @Query("""
       UPDATE Notification n
       SET n.isRead = true
       WHERE n.receiver = :user
         AND n.isRead = false
       """)
    void markAllAsRead(User user);
}