package com.hibuddy.hibuddy_backend.service;


public interface LikeService {

    void likePost(Long userId, Long postId);

    void unlikePost(Long userId, Long postId);

    boolean isPostLiked(Long userId, Long postId);

    long countLikes(Long postId);
}
