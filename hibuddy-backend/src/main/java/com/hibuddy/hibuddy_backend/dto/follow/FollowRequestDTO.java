package com.hibuddy.hibuddy_backend.dto.follow;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowRequestDTO {

    @NotNull
    private Long targetUserId;
}