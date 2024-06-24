package com.sparta.fooddeliveryapp.domain.store.repository;

import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByOrderByStoreIdDesc(Pageable pageable);
    Page<Store> findByStoreNameContainingIgnoreCaseOrderByStoreIdDesc(String storeName, Pageable pageable);
}
//    Page<Store> findAllByOrderByCreatedAtDesc(Pageable pageable);
//    Page<Store> findByStoreNameContainingIgnoreCaseOrderByCreatedAtDesc(String storeName, Pageable pageable);
