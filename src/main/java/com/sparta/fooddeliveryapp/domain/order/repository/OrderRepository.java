package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long>,
    PagingAndSortingRepository<Orders, Long> {
    // BUGFIX : List<Orders> findAllByUserOrderByCreatedAtDesc(User user); 에서 Pageable 미반영 수정
    Page<Orders> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

}
