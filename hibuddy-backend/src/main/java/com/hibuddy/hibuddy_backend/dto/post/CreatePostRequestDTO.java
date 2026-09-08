package com.hibuddy.hibuddy_backend.dto.post;

import lombok.Data;

@Data
public class CreatePostRequestDTO {
    private String caption;
    private String imageUrl;
}