package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.user.UserRequestDTO;
import com.hibuddy.hibuddy_backend.dto.user.UserResponseDTO;
import com.hibuddy.hibuddy_backend.dto.user.UpdateUserRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO user);

    UserResponseDTO getUserById(Long userId);

    UserResponseDTO getUserByUsername(String username);

    Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable);

    UserResponseDTO updateProfile(Long userId, UpdateUserRequestDTO updatedUser);

    void deleteUser(Long userId);
}