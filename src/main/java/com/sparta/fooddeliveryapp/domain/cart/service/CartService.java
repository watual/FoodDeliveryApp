package com.sparta.fooddeliveryapp.domain.cart.service;

import com.sparta.fooddeliveryapp.domain.cart.dto.CartListResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartRequestDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import com.sparta.fooddeliveryapp.domain.cart.repository.CartRepository;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final StoreRepository storeRepository;

    private final MenuRepository menuRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createCart(Long cartId, CartRequestDto requestDto) {
        Store store = findStoreById(requestDto.getStoreId());
        Menu menu  = findMenuById(requestDto.getMenuId());
        User user = findUserById(requestDto.getUserId());

        Cart cart = new Cart(store, menu, user);
        cartRepository.save(cart);
    }

    public CartListResponseDto getCart() {
        List<Cart> cartList = cartRepository.findAll();
        List<CartResponseDto> cartItems = cartList.stream()
            .map(cart -> new CartResponseDto(
                cart.getStore().getStoreName(),
                cart.getMenu().getMenuName(),
                cart.getMenu().getPrice()
            ))
            .collect(Collectors.toList());

        Integer total_price = cartItems.stream()
            .mapToInt(CartResponseDto::getPrice)
            .sum();

        return new CartListResponseDto(cartItems, total_price);
    }

    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
            .orElseThrow(() -> new IllegalArgumentException("해당 장바구니를 찾을 수 없습니다."));
    }

    public Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없습니다."));
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
