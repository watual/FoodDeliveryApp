package com.sparta.fooddeliveryapp.domain.menu.repository;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findById(Long menuId);

}
