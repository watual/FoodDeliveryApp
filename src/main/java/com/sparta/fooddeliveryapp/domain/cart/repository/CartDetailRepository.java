package com.sparta.fooddeliveryapp.domain.cart.repository;

import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import com.sparta.fooddeliveryapp.domain.cart.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<List<CartDetail>> findAllByCart(Cart cart);
}
