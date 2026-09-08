package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.follow.*;
import com.hibuddy.hibuddy_backend.entity.Follow;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.enums.NotificationType;
import com.hibuddy.hibuddy_backend.mapper.FollowMapper;
import com.hibuddy.hibuddy_backend.repository.FollowRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.FollowService;
import com.hibuddy.hibuddy_backend.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public FollowStatusDTO followUser(Long followerId, Long followingId) {

        validateSelfFollow(followerId, followingId);

        User follower = getUser(followerId);
        User following = getUser(followingId);

        if (followRepository.existsByFollower_UserIdAndFollowing_UserId(followerId, followingId)) {
            return buildStatus(false, followerId, followingId, "Already following");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

        notificationService.createFollowNotification(
                follower.getUserId(),   // sender (who followed)
                following.getUserId()   // receiver (who is being followed)
        );
        return buildStatus(true, followerId, followingId, "Followed successfully");
    }

    @Override
    public FollowStatusDTO unfollowUser(Long followerId, Long followingId) {

        followRepository.deleteByFollower_UserIdAndFollowing_UserId(followerId, followingId);

        return buildStatus(false, followerId, followingId, "Unfollowed successfully");
    }

    @Override
    public FollowStatusDTO isFollowing(Long followerId, Long followingId) {

        boolean status = followRepository.existsByFollower_UserIdAndFollowing_UserId(followerId, followingId);

        return buildStatus(status, followerId, followingId, "Status fetched");
    }

    @Override
    public Page<FollowUserPreviewDTO> getFollowing(Long userId, Pageable pageable) {

        return followRepository.findFollowingUsersByFollowerId(userId, pageable)
                .map(FollowMapper::toUserPreview);
    }

    @Override
    public Page<FollowUserPreviewDTO> getFollowers(Long userId, Pageable pageable) {

        return followRepository.findFollowersByUserId(userId, pageable)
                .map(FollowMapper::toUserPreview);
    }

    @Override
    public FollowStatsDTO getFollowStats(Long userId) {

        FollowStatsDTO dto = new FollowStatsDTO();
        dto.setFollowersCount(followRepository.countByFollowing_UserId(userId));
        dto.setFollowingCount(followRepository.countByFollower_UserId(userId));

        return dto;
    }

    @Override
    public List<FollowUserPreviewDTO> getFollowingUsers(Long userId) {

        return followRepository.findFollowingUsersByFollowerId(userId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(FollowMapper::toUserPreview)
                .toList();
    }

    // ---------------- HELPERS ----------------

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void validateSelfFollow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }
    }

    private FollowStatusDTO buildStatus(boolean following, Long followerId, Long targetId, String message) {
        FollowStatusDTO dto = new FollowStatusDTO();
        dto.setFollowing(following);
        dto.setFollowerId(followerId);
        dto.setTargetUserId(targetId);
        dto.setMessage(message);
        return dto;
    }
}