package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.feedpost.FeedPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedService {

    Page<FeedPostDTO> getUserFeed(
            Long userId,
            Pageable pageable
    );
}