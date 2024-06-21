package com.sparta.fooddeliveryapp.domain.cart.controller;

import com.sparta.fooddeliveryapp.domain.cart.dto.CartListResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartRequestDto;
import com.sparta.fooddeliveryapp.domain.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResponseEntity.status(HttpStatus.OK).body("장바구니 담기 완료");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCart(@RequestParam Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴삭제 완료");
    }

    @GetMapping
    public ResponseEntity<CartListResponseDto> getCart() {
        CartListResponseDto cartItems = cartService.getCart();
        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }
}
