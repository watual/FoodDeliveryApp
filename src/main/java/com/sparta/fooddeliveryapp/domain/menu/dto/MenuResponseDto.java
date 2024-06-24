package com.sparta.fooddeliveryapp.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponseDto {
    private final Long storeId;
    private final Long menuId;
    private final String menuName;
    private final String intro;
    private final Integer price;
}
