package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.follow.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowService {

    FollowStatusDTO followUser(Long followerId, Long followingId);

    FollowStatusDTO unfollowUser(Long followerId, Long followingId);

    FollowStatusDTO isFollowing(Long followerId, Long followingId);

    Page<FollowUserPreviewDTO> getFollowing(Long userId, Pageable pageable);

    Page<FollowUserPreviewDTO> getFollowers(Long userId, Pageable pageable);

    FollowStatsDTO getFollowStats(Long userId);

    List<FollowUserPreviewDTO> getFollowingUsers(Long userId);
}