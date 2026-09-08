package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.post.PostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.exception.ResourceNotFoundException;
import com.hibuddy.hibuddy_backend.mapper.PostMapper;
import com.hibuddy.hibuddy_backend.repository.PostRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 🔥 helper
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
    }

    // 🔥 Create post
    @Override
    public PostResponseDTO createPost(Long userId, Post post) {

        post.setUser(getUser(userId));

        Post saved = postRepository.save(post);

        return PostMapper.toDTO(saved);
    }

    // 🔍 Get post by ID
    @Override
    public PostResponseDTO getPostById(Long postId) {

        return PostMapper.toDTO(getPost(postId));
    }

    // 📦 Get posts of a user
    @Override
    public Page<PostResponseDTO> getUserPosts(Long userId, Pageable pageable) {

        return postRepository.findByUser(getUser(userId), pageable)
                .map(PostMapper::toDTO);
    }

    // 🌍 Get all posts
    @Override
    public Page<PostResponseDTO> getAllPosts(Pageable pageable) {

        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostMapper::toDTO);
    }

    // 🔥 Feed posts
    @Override
    public Page<PostResponseDTO> getFeedPosts(Long userId, Pageable pageable) {

        User user = getUser(userId);

        List<User> followingUsers = List.of(user);

        return postRepository.findByUserInOrderByCreatedAtDesc(followingUsers, pageable)
                .map(PostMapper::toDTO);
    }

    // ❌ Delete post
    @Override
    public void deletePost(Long postId) {

        postRepository.delete(getPost(postId));
    }

    // 📊 Count posts
    @Override
    public long countUserPosts(Long userId) {

        return postRepository.countByUser(getUser(userId));
    }
}