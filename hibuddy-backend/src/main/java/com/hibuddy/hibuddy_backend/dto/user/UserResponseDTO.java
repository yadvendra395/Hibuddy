package com.hibuddy.hibuddy_backend.dto.user;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {

    private Long userId;
    private String email;
    private String username;
    private String bio;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private boolean privateAccount;
}
