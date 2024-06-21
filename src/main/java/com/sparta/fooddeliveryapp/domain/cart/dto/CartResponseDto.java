package com.sparta.fooddeliveryapp.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartResponseDto {

    private String storeName;

    private String menuName;

    private Integer price;

}
