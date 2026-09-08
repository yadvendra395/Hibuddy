package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.ApiResponse;
import com.hibuddy.hibuddy_backend.dto.user.UserRequestDTO;
import com.hibuddy.hibuddy_backend.dto.user.UserResponseDTO;
import com.hibuddy.hibuddy_backend.dto.user.UpdateUserRequestDTO;
import com.hibuddy.hibuddy_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 🔥 Register User
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(
            @RequestBody UserRequestDTO dto) {

        UserResponseDTO user = userService.registerUser(dto);

        return new ResponseEntity<>(
                new ApiResponse<>(true, "User created successfully", user),
                HttpStatus.CREATED
        );
    }

    // 🔍 Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(
            @PathVariable Long id) {

        UserResponseDTO user = userService.getUserById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", user)
        );
    }

    // 🔍 Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByUsername(
            @PathVariable String username) {

        UserResponseDTO user = userService.getUserByUsername(username);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", user)
        );
    }

    // 🔎 Search users
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> searchUsers(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<UserResponseDTO> users = userService.searchUsers(keyword, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users fetched successfully", users)
        );
    }

    // ✏️ Update profile
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDTO dto) {

        UserResponseDTO user = userService.updateProfile(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated successfully", user)
        );
    }

    // ❌ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User deleted successfully", null)
        );
    }
}