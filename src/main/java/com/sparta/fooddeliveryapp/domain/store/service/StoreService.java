package com.sparta.fooddeliveryapp.domain.store.service;

import com.sparta.fooddeliveryapp.domain.store.dto.StoreRequestDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    @Autowired
    private final StoreRepository storeRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    public Page<Store> getAllStores(int page, int size) {
        return storeRepository.findAllByOrderByStoreIdDesc(PageRequest.of(page, size));
    }

    public Page<Store> searchStores(String storeName, int page, int size) {
        return storeRepository.findByStoreNameContainingIgnoreCaseOrderByStoreIdDesc(storeName, PageRequest.of(page, size));
    }

    public void createStore(StoreRequestDto storeRequestDto, User user) {
        Store store = new Store();
        store.setStoreName(storeRequestDto.getStoreName());
        store.setIntro(storeRequestDto.getIntro());
        store.setDialNumber(storeRequestDto.getDialNumber());
        store.setUser(user);
        storeRepository.save(store);
    }

    public void updateStore(Long storeId, StoreRequestDto storeRequestDto, User user) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            if (store.getUser().equals(user)) {
                store.setStoreName(storeRequestDto.getStoreName());
                store.setIntro(storeRequestDto.getIntro());
                store.setDialNumber(storeRequestDto.getDialNumber());
                storeRepository.save(store);
            } else {
                throw new RuntimeException("점주만 매장을 수정할 수 있습니다.");
            }
        } else {
            throw new RuntimeException("매장을 찾을 수 없습니다.");
        }
    }

    public boolean isOwner(String token) {
        try {
            String username = jwtUtil.getUsername(token);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return "OWNER".equals(user.getRole());
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserFromToken(String token) {
        String username = jwtUtil.getUsername(token);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



}
