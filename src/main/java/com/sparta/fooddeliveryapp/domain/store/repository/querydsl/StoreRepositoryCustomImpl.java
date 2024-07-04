package com.sparta.fooddeliveryapp.domain.store.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.store.entity.QStore;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
