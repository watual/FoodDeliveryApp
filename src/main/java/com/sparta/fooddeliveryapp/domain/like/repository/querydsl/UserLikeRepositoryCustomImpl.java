//package com.sparta.fooddeliveryapp.domain.like.repository.querydsl;
//
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.sparta.fooddeliveryapp.domain.like.entity.QUserLike;
//import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
//import com.sparta.fooddeliveryapp.domain.like.entity.UserLikeType;
//import com.sparta.fooddeliveryapp.global.common.PageDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class UserLikeRepositoryCustomImpl implements UserLikeRepositoryCustom {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public Page<UserLike> selectLikeList(UserLikeType userLikeType, Long typeId, Pageable pageable) {
//        QUserLike qUserLike = QUserLike.userLike;
//        List<UserLike> userLikeList = jpaQueryFactory.selectFrom(qUserLike)
//                .where(qUserLike.userLikeType.eq(userLikeType)
//                        .and(qUserLike.typeId.eq(typeId)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> totalSize = jpaQueryFactory.select(Wildcard.count)
//                .from(qUserLike)
//                .where(qUserLike.userLikeType.eq(userLikeType)
//                        .and(qUserLike.typeId.eq(typeId)));
////                .where(qUserLike.typeId.eq(typeId));
//
//        return PageableExecutionUtils.getPage(userLikeList, pageable, totalSize::fetchOne);
//    }
//
//}
