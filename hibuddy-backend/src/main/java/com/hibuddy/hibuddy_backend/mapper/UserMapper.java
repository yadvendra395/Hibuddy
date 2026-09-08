package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.user.UserRequestDTO;
import com.hibuddy.hibuddy_backend.dto.user.UserResponseDTO;
import com.hibuddy.hibuddy_backend.entity.User;

public class UserMapper {

    // DTO → Entity
    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setBio(dto.getBio());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        return user;
    }

    // Entity → Response DTO
    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}