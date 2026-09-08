package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.savedpost.SavedPostResponseDTO;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.SavedPost;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.exception.DuplicateResourceException;
import com.hibuddy.hibuddy_backend.exception.ResourceNotFoundException;
import com.hibuddy.hibuddy_backend.mapper.SavedPostMapper;
import com.hibuddy.hibuddy_backend.repository.PostRepository;
import com.hibuddy.hibuddy_backend.repository.SavedPostRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.SavedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavedPostServiceImpl implements SavedPostService {

    private final SavedPostRepository savedPostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 🔥 helper methods
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
    }

    // 🔥 Save post
    @Override
    public void savePost(Long userId, Long postId) {

        User user = getUser(userId);
        Post post = getPost(postId);

        if (savedPostRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateResourceException("Post already saved");
        }

        SavedPost savedPost = new SavedPost();
        savedPost.setUser(user);
        savedPost.setPost(post);

        savedPostRepository.save(savedPost);
    }

    // ❌ Unsave post
    @Override
    public void unsavePost(Long userId, Long postId) {

        User user = getUser(userId);
        Post post = getPost(postId);

        SavedPost savedPost = savedPostRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new ResourceNotFoundException("Saved post not found"));

        savedPostRepository.delete(savedPost);
    }

    // 📦 Get saved posts (DTO based)
    @Override
    public Page<SavedPostResponseDTO> getSavedPosts(Long userId, Pageable pageable) {

        User user = getUser(userId);

        Page<SavedPost> savedPosts =
                savedPostRepository.findByUserOrderBySavedAtDesc(user, pageable);

        return savedPosts.map(SavedPostMapper::toDTO);
    }

    // 🔍 Check saved
    @Override
    public boolean isPostSaved(Long userId, Long postId) {

        User user = getUser(userId);
        Post post = getPost(postId);

        return savedPostRepository.existsByUserAndPost(user, post);
    }
}