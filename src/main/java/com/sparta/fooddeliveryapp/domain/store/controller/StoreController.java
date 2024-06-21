package com.sparta.fooddeliveryapp.domain.store.controller;

import com.sparta.fooddeliveryapp.domain.store.dto.StoreResponseDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping
    public List<StoreResponseDto> getAllStores(
            @RequestHeader("Access-Token") String accessToken,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Store> storePage = storeService.getAllStores(page, size);

        return storePage.stream().map(store -> new StoreResponseDto(
                store.getStoreId(),
                store.getRate(),
                store.getStoreName(),
                store.getDialNumber(),
                store.getIntro()
        )).collect(Collectors.toList());
    }
}