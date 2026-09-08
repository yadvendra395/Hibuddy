package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.SavedPost;
import com.hibuddy.hibuddy_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

    Optional<SavedPost> findByUserAndPost(User user, Post post);

    Page<SavedPost> findByUserOrderBySavedAtDesc(User user, Pageable pageable);

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);
}