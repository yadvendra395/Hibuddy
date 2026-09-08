package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.follow.*;
import com.hibuddy.hibuddy_backend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // FOLLOW USER
    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<FollowStatusDTO> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId
    ) {
        return ResponseEntity.ok(
                followService.followUser(followerId, followingId)
        );
    }


    // UNFOLLOW USER
    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<FollowStatusDTO> unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId
    ) {
        return ResponseEntity.ok(
                followService.unfollowUser(followerId, followingId)
        );
    }

    // =========================
    // CHECK FOLLOW STATUS
    // =========================
    @GetMapping("/status")
    public ResponseEntity<FollowStatusDTO> isFollowing(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        return ResponseEntity.ok(
                followService.isFollowing(followerId, followingId)
        );
    }


    // GET FOLLOWING LIST (PAGINATED)

    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<FollowUserPreviewDTO>> getFollowing(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                followService.getFollowing(userId, pageable)
        );
    }


    // GET FOLLOWERS LIST (PAGINATED)
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Page<FollowUserPreviewDTO>> getFollowers(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                followService.getFollowers(userId, pageable)
        );
    }


    // FOLLOW STATS

    @GetMapping("/{userId}/stats")
    public ResponseEntity<FollowStatsDTO> getFollowStats(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                followService.getFollowStats(userId)
        );
    }
}