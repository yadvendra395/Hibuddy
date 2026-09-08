package com.hibuddy.hibuddy_backend.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequestDTO {
    private String caption;
    private String imageUrl;
}