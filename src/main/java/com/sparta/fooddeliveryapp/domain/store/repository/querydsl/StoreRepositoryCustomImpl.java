package com.sparta.fooddeliveryapp.domain.store.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.like.entity.QUserLike;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLikeType;
import com.sparta.fooddeliveryapp.domain.store.entity.QStore;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Store selectfromStoreWhereStoreId(Long typeId) {
        QStore qStore = QStore.store;
        Store store = jpaQueryFactory.selectFrom(qStore)
                .where(qStore.storeId.eq(typeId))
                .fetchOne();
        return store;
    }

    @Override
    public Page<Store> selectfromStoreWhereUserLike(User user, Pageable pageable) {
        // userLike 에서 좋아요를 WHERE user_id로 찾고 찾은 store_id 들을 store repo 에서 찾아 List 로 반환
        QUserLike qUserLike = QUserLike.userLike;
        QStore qStore = QStore.store;
        List<Store> storeList = jpaQueryFactory.select(qStore)
                .from(qStore)
                .innerJoin(qUserLike).on(qStore.storeId.eq(qUserLike.typeId))
                .where(qUserLike.userLikeType.eq(UserLikeType.STORE)
                        .and(qUserLike.user.eq(user)))
                .orderBy(qUserLike.createdAt.desc())  // created_at을 기준으로 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(qStore.count())
                        .from(qStore)
                        .innerJoin(qUserLike).on(qStore.storeId.eq(qUserLike.typeId))
                        .where(qUserLike.userLikeType.eq(UserLikeType.STORE)
                                .and(qUserLike.user.eq(user)))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(storeList, pageable, total);
    }
}
