package com.sparta.fooddeliveryapp.domain.cart.service;

import com.sparta.fooddeliveryapp.domain.cart.dto.CartListResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartRequestDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import com.sparta.fooddeliveryapp.domain.cart.repository.CartRepository;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;

import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    public CartListResponseDto getCart(int page, int size) {
        Page<Cart> cartPage = cartRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt")));
        List<CartResponseDto> cartItems = new ArrayList<>();
        int totalPrice = 0;

        for(Cart cart : cartPage.getContent()) {
            cartItems.add(new CartResponseDto(
                cart.getStore().getStoreName(),
                cart.getMenu().getMenuName(),
                cart.getMenu().getPrice()
            ));
        }

//        for (int price : cartRepository.findAll().getMenu().getPrice()) {
//            totalPrice += price;
//        }

        return new CartListResponseDto(cartItems, totalPrice);

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
