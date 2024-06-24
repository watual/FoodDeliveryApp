package com.sparta.fooddeliveryapp.domain.cart.dto;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class CartDetailResponseDto {
    private final Long cartDetailId;
    private final Long cartId;
    private final Long menuId;
    private final String menuName;
    private final Integer menuPrice;
    private final String menuIntro;

    public CartDetailResponseDto(Long cartDetailId, Long cartId, Menu menu) {
        this.cartDetailId = cartDetailId;
        this.cartId = cartId;
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getPrice();
        this.menuIntro = menu.getIntro();
    }
}
