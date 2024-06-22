package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}