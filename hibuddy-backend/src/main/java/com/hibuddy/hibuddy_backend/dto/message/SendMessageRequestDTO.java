package com.hibuddy.hibuddy_backend.dto.message;

import lombok.Data;

@Data
public class SendMessageRequestDTO {

    private Long receiverId;
    private String content;
}