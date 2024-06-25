package com.sparta.fooddeliveryapp.domain.store.service;

import com.sparta.fooddeliveryapp.domain.store.dto.StoreRequestDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.error.exception.UserNotFoundException;
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
            if (store.getUser().getUserId().equals(user.getUserId())) {
                if (storeRequestDto.getStoreName() != null) {
                    store.setStoreName(storeRequestDto.getStoreName());
                }
                if (storeRequestDto.getIntro() != null) {
                    store.setIntro(storeRequestDto.getIntro());
                }
                if (storeRequestDto.getDialNumber() != null) {
                    store.setDialNumber(storeRequestDto.getDialNumber());
                }
                storeRepository.save(store);
            } else {
                throw new RuntimeException("점주만 매장을 수정할 수 있습니다.");
            }
        } else {
            throw new RuntimeException("매장을 찾을 수 없습니다.");
        }
    }

    public void deleteStore(Long storeId, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new RuntimeException("매장을 찾을 수 없습니다.")
        );
        if (user.getRole() != UserRoleEnum.ADMIN) {
            if (!store.getUser().getUserId().equals(user.getUserId())) {
                throw new RuntimeException("매장 운영자만 매장을 삭제할 수 있습니다.");
            }
        }
        storeRepository.delete(store);
    }


    public void isOwner(String token) {
        User user = getUserFromToken(token);
        if (!user.getRole().equals(UserRoleEnum.SELLER)) {
            throw new IllegalArgumentException("해당 유저는 점주가 아닙니다.");
        }
    }

    public User getUserFromToken(String token) {
        String loginId = jwtUtil.extractLoginId(token);
        return userRepository.findByLoginId(loginId)
                .orElseThrow(UserNotFoundException::new);
    }
}
