package com.sparta.fooddeliveryapp.domain.cart.service;

import com.sparta.fooddeliveryapp.domain.cart.dto.CartDetailResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartListResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartRequestDto;
import com.sparta.fooddeliveryapp.domain.cart.dto.CartResponseDto;
import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import com.sparta.fooddeliveryapp.domain.cart.entity.CartDetail;
import com.sparta.fooddeliveryapp.domain.cart.repository.CartDetailRepository;
import com.sparta.fooddeliveryapp.domain.cart.repository.CartRepository;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.error.exception.UserMismatchException;
import jakarta.validation.constraints.Null;
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
    private final CartDetailRepository cartDetailRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void createCart(User user, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("해당 매장을 찾을 수 없습니다")
        );

        Cart cart = new Cart(store, user);
        cartRepository.save(cart);
    }

    public CartListResponseDto getCart(int page, int size, User user) {
        // 전체 조회에서 개인 장바구니 조회로 변경
        Page<Cart> cartPage = cartRepository.findAllByUser(user, PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt")));
        List<CartResponseDto> cartItems = new ArrayList<>();
        int totalPrice = 0;
        for(Cart cart : cartPage.getContent()) {

            List<CartDetail> cartDetailList = cartDetailRepository.findAllByCart(cart).orElseThrow(
                    () -> new NullPointerException("장바구니에 메뉴가 없습니다")
            );
            List<CartDetailResponseDto> cartDetailResponseDtoList = cartDetailList.stream()
                    .map(
                            cartDetail -> new CartDetailResponseDto(
                                    cartDetail.getCartDetailId(),
                                    cartDetail.getCart().getCartId(),
                                    cartDetail.getMenu()
                            )
                    )
                    .toList();
            cartItems.add(new CartResponseDto(
                    cart.getCartId(),
                    cart.getStore().getStoreName(),
                    cartDetailResponseDtoList
            ));
        }

        return new CartListResponseDto(cartItems, totalPrice);

    }
    public void deleteCart(Long userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 장바구니 입니다")
        );
        if(!Objects.equals(cart.getUser().getUserId(), userId)) {
            throw new UserMismatchException("접근 권한이 없는 사용자입니다");
        }
        cartRepository.deleteById(cartId);
    }

    public void addToCart(User user, Long menuId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NullPointerException("장바구니가 존재하지 않습니다")
        );
        if(!Objects.equals(user.getUserId(), cart.getUser().getUserId())) {
            throw new UserMismatchException("사용자가 다릅니다");
        }
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NullPointerException("메뉴가 존재하지 않습니다")
        );
        CartDetail cartDetail = new CartDetail(cart, menu);
        cartDetailRepository.save(cartDetail);
    }
}
