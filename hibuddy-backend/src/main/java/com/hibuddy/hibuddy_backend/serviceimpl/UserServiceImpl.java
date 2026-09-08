package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.user.UserRequestDTO;
import com.hibuddy.hibuddy_backend.dto.user.UserResponseDTO;
import com.hibuddy.hibuddy_backend.dto.user.UpdateUserRequestDTO;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.exception.DuplicateResourceException;
import com.hibuddy.hibuddy_backend.exception.ResourceNotFoundException;
import com.hibuddy.hibuddy_backend.mapper.UserMapper;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔥 Register user
    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        User user = UserMapper.toEntity(dto);

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }
    // 🔍 Get user by ID
    @Override
    public UserResponseDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.toDTO(user);
    }
    // 🔍 Get user by username
    @Override
    public UserResponseDTO getUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.toDTO(user);
    }

    @Override
    public Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable) {

        return userRepository
                .findByUsernameContainingIgnoreCase(keyword, pageable)
                .map(UserMapper::toDTO);
    }

    // ✏️ Update profile
    @Override
    public UserResponseDTO updateProfile(Long userId, UpdateUserRequestDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getUsername() != null &&
                !user.getUsername().equals(dto.getUsername())) {

            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new DuplicateResourceException("Username already taken");
            }

            user.setUsername(dto.getUsername());
        }

        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }

        if (dto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(dto.getProfileImageUrl());
        }

        return UserMapper.toDTO(userRepository.save(user));
    }

    // ❌ Delete user
    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}