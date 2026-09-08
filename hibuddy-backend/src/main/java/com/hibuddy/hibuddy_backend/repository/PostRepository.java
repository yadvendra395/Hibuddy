package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByUserInOrderByCreatedAtDesc(List<User> users, Pageable pageable);

    long countByUser(User user);
    // ============================================
// POST REPOSITORY
// ============================================
    @Query("""
    SELECT p
    FROM Post p
    WHERE p.user.userId IN :userIds
    ORDER BY p.createdAt DESC
""")
    Page<Post> findFeedPosts(@Param("userIds")List<Long> userIds,
                             Pageable pageable);

}