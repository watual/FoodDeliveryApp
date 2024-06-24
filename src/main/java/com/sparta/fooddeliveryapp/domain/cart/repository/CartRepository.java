package com.sparta.fooddeliveryapp.domain.cart.repository;

import com.sparta.fooddeliveryapp.domain.cart.entity.Cart;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CartRepository extends JpaRepository<Cart, Long>,
    PagingAndSortingRepository<Cart, Long> {
    Page<Cart> findAllByUser(User user, PageRequest createdAt);

}
