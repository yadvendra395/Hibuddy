package com.hibuddy.hibuddy_backend.repository;

import com.hibuddy.hibuddy_backend.entity.Post;
import com.hibuddy.hibuddy_backend.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}