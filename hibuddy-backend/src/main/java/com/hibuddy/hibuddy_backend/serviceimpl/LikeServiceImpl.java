package com.hibuddy.hibuddy_backend.serviceimpl;



import com.hibuddy.hibuddy_backend.entity.Like;
import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.exception.ResourceNotFoundException;
import com.hibuddy.hibuddy_backend.repository.LikeRepository;
import com.hibuddy.hibuddy_backend.repository.PostRepository;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.LikeService;
import com.hibuddy.hibuddy_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NotificationService notificationService;

    // ❤️ Like post
    @Override
    public void likePost(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Prevent duplicate like
        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new RuntimeException("Post already liked");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);

        // Send notification
        if (!user.getUserId().equals(post.getUser().getUserId())) {
            notificationService.createLikeNotification(
                    user.getUserId(),          // sender
                    post.getUser().getUserId(),// receiver
                    post.getPostId()           // postId
            );
        }
    }

    // ❌ Unlike post
    @Transactional
    @Override
    public void unlikePost(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        likeRepository.deleteByUserAndPost(user, post);
    }

    // 🔍 Check if liked
    @Override
    public boolean isPostLiked(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.existsByUserAndPost(user, post);
    }

    // 📊 Count likes

    @Override
    public long countLikes(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.countByPost(post);
    }
}
