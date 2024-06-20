package com.sparta.fooddeliveryapp.domain.cart.controller;

import com.sparta.fooddeliveryapp.domain.cart.dto.CartRequestDto;
import com.sparta.fooddeliveryapp.domain.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    public final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart(@RequestParam Long cartId, @RequestBody CartRequestDto requestDto) {
        cartService.createCart(cartId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("장바구니 담기 완료");
    }

}
