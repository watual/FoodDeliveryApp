package com.sparta.fooddeliveryapp.domain.cart.dto;

import java.util.List;
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
public class CartListResponseDto {

    private List<CartResponseDto> cartItems;

    private Integer total_price;

}
