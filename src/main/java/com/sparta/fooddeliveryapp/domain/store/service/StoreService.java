package com.sparta.fooddeliveryapp.domain.store.service;

import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public Page<Store> getAllStores(int page, int size) {
        return storeRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }
}
