package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Comment;
import com.hibuddy.hibuddy_backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostOrderByCreatedAtDesc(Post post, Pageable pageable);

    long countByPost(Post post);
    Page<Comment> findByParentComment(Comment parentComment, Pageable pageable);

    void deleteByPost(Post post);
}