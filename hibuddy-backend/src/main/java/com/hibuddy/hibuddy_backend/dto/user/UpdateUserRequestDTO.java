package com.hibuddy.hibuddy_backend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDTO {

    private String username;
    private String bio;
    private String profileImageUrl;
    private Boolean privateAccount;
}
