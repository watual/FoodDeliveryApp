package com.sparta.fooddeliveryapp.domain.review.dto;

import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String userName;
    private Long ordersId;
    private String content;
    private Long rate;
}
