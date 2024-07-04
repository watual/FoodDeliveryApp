package com.sparta.fooddeliveryapp.domain.store.repository.querydsl;

import com.sparta.fooddeliveryapp.domain.store.entity.Store;

public interface StoreRepositoryCustom {

    Store selectfromStoreWhereStoreId(Long typeId);

}
