package com.sparta.fooddeliveryapp.domain.cart.repository;

import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CartDetailRepository extends JpaRepository<Cart, Long> {
}
