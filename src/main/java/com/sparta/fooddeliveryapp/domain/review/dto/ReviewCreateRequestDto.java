package com.sparta.fooddeliveryapp.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
    private Long ordersId;
    private String content;
    private Long rate;
}
