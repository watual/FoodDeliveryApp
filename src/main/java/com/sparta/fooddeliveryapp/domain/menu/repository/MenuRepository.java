package com.sparta.fooddeliveryapp.domain.menu.repository;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findByStoreId(Long storeId, Pageable pageable);
}
