package com.sparta.fooddeliveryapp.domain.cart.repository;

import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
