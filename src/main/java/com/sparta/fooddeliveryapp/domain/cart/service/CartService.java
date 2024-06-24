package com.sparta.fooddeliveryapp.domain.cart.service;

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
import java.util.stream.Collectors;

import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Transactional
    public void createCart(Long cartId, CartRequestDto requestDto) {
        Store store = findStoreById(requestDto.getStoreId());
        Menu menu  = findMenuById(requestDto.getMenuId());
        User user = findUserById(requestDto.getUserId());

        Cart cart = new Cart(store, user);
        cartRepository.save(cart);
    }

    // 나은님의 BHC 장바구니 or 원하의 BBQ 장바구니
    /*원하(일) -> getCart 제거만 봄 O, getAllCart 모든 사용자 장바구니 봄 X
      나은(관) -> getCart 제거만 봄 O, getAllCart 모든 사용자 장바구니 봄 O
    나은 장바구니1
    나은 장바구니2
    나은 장바구니3
    나은 장바구니4

    원하 장바구니1
    원하 장바구니2
    원하 장바구니3
    원하 장바구니4

    성모 장바구니1
    성모 장바구니2
    성모 장바구니3
    성모 장바구니4
     */
    public CartListResponseDto getCart(int page, int size, User user) {
        // 전체 조회에서 개인 장바구니 조회로 변경
        Page<Cart> cartPage = cartRepository.findAllByUser(user, PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt")));
        List<CartResponseDto> cartItems = new ArrayList<>();
        int totalPrice = 0;
        for(Cart cart : cartPage.getContent()) {
//            나은 장바구니1 -> UserDetails -> 목록들 -> 가지고와서 front 에 전달
//            나은 장바구니2
//            나은 장바구니3
//            나은 장바구니4
            List<CartDetail> cartDetailList = cartDetailRepository.findAllByCart(cart).orElseThrow(
                    () -> new NullPointerException("장바구니에 메뉴가 없습니다")
            );
            List<Menu> menuList = cartDetailList.stream()
                    .map(CartDetail::getMenu) // CartDetail 에서 Menu 를 추출
                    .collect(Collectors.toList());
            cartItems.add(new CartResponseDto(
                cart.getStore().getStoreName(),
                menuList
            ));
            /*
            {
                cartId : 장바구니1
                menuList{

                }
            }
             */
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

    /*
    cart 장바구니 - id, store,  user
    cartDetail 장바구니 목록 - id, cart, menu

    #cart
    장바구니1, 나은, BHC
    #cartDetail
    목록1, 장바구니1, 양념치킨(12000원)
    목록2, 장바구니1, 양념치킨(12000원)
    목록3, 장바구니1, 간장치킨(12000원)
     */
    // 지금 '장바구니' 를 삭제하고 있습니다, 장바구니에 메뉴를 지우는 API가 필요합니다
    // ex) 간장2, 양념3 에서 양념1개 취소하는 API
    // -> UserDetails 에서 나은님의 3번 장바구니에 8번 양념치킨 데이터 1개 삭제
    public void deleteCart(Long userId, Long cartId) {
        cartRepository.findById(cartId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 장바구니 입니다")
        );
        cartRepository.deleteById(cartId);
    }
}
