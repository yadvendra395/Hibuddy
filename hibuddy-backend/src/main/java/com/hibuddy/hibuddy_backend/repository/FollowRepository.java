package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // =========================
    // FOLLOW CHECK
    // =========================
    boolean existsByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);

    void deleteByFollower_UserIdAndFollowing_UserId(Long followerId, Long followingId);

    // =========================
    // COUNTS
    // =========================
    long countByFollower_UserId(Long followerId);

    long countByFollowing_UserId(Long followingId);

    // =========================
    // FOLLOWING LIST
    // =========================
    @Query("SELECT f.following FROM Follow f WHERE f.follower.userId = :userId")
    Page<User> findFollowingUsersByFollowerId(@Param("userId") Long userId, Pageable pageable);

    // =========================
    // FOLLOWERS LIST
    // =========================
    @Query("SELECT f.follower FROM Follow f WHERE f.following.userId = :userId")
    Page<User> findFollowersByUserId(@Param("userId") Long userId, Pageable pageable);
}