package com.sparta.fooddeliveryapp.domain.cart.dto;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartResponseDto {

    private Long cartId;
    private String storeName;
    // 장바구니 1개에 메뉴 여러개
    private List<CartDetailResponseDto> cartDetailList;
//    private List<Menu> menuList;
}
