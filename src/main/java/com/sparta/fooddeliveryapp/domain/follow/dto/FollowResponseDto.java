package com.sparta.fooddeliveryapp.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FollowResponseDto {
    private Long followId;
    private Long userId;
    private Long storeId;
}
