package com.sparta.fooddeliveryapp.domain.cart.dto;

import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
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
public class CartRequestDto {
    private Long storeId;
}
