package com.sparta.fooddeliveryapp.domain.order.dto;

import java.util.List;

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
public class OrderRequestDto {

    private Long cartId;

//    private Integer totalPrice;
//
//    private String store_id;
//
//    private List<OrderDetailRequestDto> orderDetailDtoList;

}
