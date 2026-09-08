package com.hibuddy.hibuddy_backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @Email
    @NotBlank
    private String email;
    @Size(min = 3 ,max = 25)
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 5,max = 25)
    private String password;

    private String bio;
    private String profileImageUrl;
}
