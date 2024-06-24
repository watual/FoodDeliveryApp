package com.sparta.fooddeliveryapp.domain.store.entity;

import com.sparta.fooddeliveryapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private Double rate;

    @Column
    private String storeName;

    @Column
    private String dialNumber;

    @Column
    private String intro;
}