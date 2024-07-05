package com.sparta.fooddeliveryapp.domain.like.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.store.entity.QStore;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserLikeRepositoryCustomImpl implements UserLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
