package com.sparta.fooddeliveryapp.domain.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDto {
    private Long storeId;
    private String menuName;
    private String intro;
    private Integer price;
}
