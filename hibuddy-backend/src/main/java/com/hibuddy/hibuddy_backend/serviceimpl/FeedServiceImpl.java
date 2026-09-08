package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.feedpost.FeedPostDTO;
import com.hibuddy.hibuddy_backend.dto.follow.FollowUserPreviewDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.mapper.FeedMapper;
import com.hibuddy.hibuddy_backend.repository.PostRepository;
import com.hibuddy.hibuddy_backend.service.FeedService;
import com.hibuddy.hibuddy_backend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FollowService followService;
    private final PostRepository postRepository;
    private final FeedMapper feedMapper;

    @Override
    public Page<FeedPostDTO> getUserFeed(
            Long userId,
            Pageable pageable
    ){

        List<Long> followingIds = followService.getFollowingUsers(userId)
                .stream()
                .map(FollowUserPreviewDTO::getUserId)
                .toList();

        if (followingIds.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Post> posts = postRepository.findFeedPosts(followingIds,pageable);

        return posts.map(feedMapper::mapToDTO);
    }


}