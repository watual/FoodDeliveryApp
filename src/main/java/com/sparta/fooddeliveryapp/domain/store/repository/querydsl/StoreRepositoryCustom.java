package com.sparta.fooddeliveryapp.domain.store.repository.querydsl;

import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {

    Store selectfromStoreWhereStoreId(Long typeId);

    Page<Store> selectfromStoreWhereUserLike(User user, Pageable pageable);

}
