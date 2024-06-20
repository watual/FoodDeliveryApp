package com.sparta.fooddeliveryapp.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Review {
    @Id
    Long hel;
}
