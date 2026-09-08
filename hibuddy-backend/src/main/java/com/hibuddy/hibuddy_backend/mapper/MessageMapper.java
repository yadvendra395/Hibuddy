package com.hibuddy.hibuddy_backend.mapper;

import com.hibuddy.hibuddy_backend.dto.message.MessageResponseDTO;
import com.hibuddy.hibuddy_backend.dto.message.SendMessageRequestDTO;
import com.hibuddy.hibuddy_backend.entity.Message;

public class MessageMapper {

    // DTO → Entity
    public static Message toEntity(SendMessageRequestDTO dto) {
        Message message = new Message();
        message.setContent(dto.getContent());
        return message;
    }

    // Entity → DTO
    public static MessageResponseDTO toDTO(Message message) {

        MessageResponseDTO dto = new MessageResponseDTO();

        dto.setMessageId(message.getMessageId());

        dto.setSenderId(message.getSender().getUserId());
        dto.setSenderUsername(message.getSender().getUsername());

        dto.setReceiverId(message.getReceiver().getUserId());
        dto.setReceiverUsername(message.getReceiver().getUsername());

        dto.setContent(message.getContent());
        dto.setStatus(message.getStatus());
        dto.setSentAt(message.getSentAt());

        return dto;
    }
}