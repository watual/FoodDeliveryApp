package com.sparta.fooddeliveryapp.domain.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequestDto {
    private Long storeId;
    private String storeName;
    private String intro;
    private String dialNumber;
}
