package com.hibuddy.hibuddy_backend.dto.user;

import lombok.Data;

@Data
public class UpdateUserProfileDTO {

    private String username;
    private String bio;
    private String profileImageUrl;
    private String coverImageUrl;
    private String fullName;
}