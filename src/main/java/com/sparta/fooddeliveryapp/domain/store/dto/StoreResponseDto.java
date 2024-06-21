package com.sparta.fooddeliveryapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreResponseDto {
    private final Long storeId;
    private final Double rate;
    private final String storeName;
    private final String dialNumber;
    private final String intro;
}