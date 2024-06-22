package com.sparta.fooddeliveryapp.domain.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String menuName;

    @Column
    private String intro;

    @Column(nullable = false)
    private Integer price;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
