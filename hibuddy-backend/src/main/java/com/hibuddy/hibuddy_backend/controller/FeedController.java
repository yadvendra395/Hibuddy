package com.hibuddy.hibuddy_backend.controller;// ============================================
// FEED CONTROLLER
// ============================================

import com.hibuddy.hibuddy_backend.dto.feedpost.FeedPostDTO;
import com.hibuddy.hibuddy_backend.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<FeedPostDTO>> getFeed(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                feedService.getUserFeed(userId,pageable)
        );
    }
}