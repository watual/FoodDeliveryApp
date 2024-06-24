package com.sparta.fooddeliveryapp.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {
    private Long reviewId;
    private String content;
    private Long rate;
}
