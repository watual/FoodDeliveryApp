package com.sparta.fooddeliveryapp.domain.store.controller;

import com.sparta.fooddeliveryapp.domain.store.dto.StoreRequestDto;
import com.sparta.fooddeliveryapp.domain.store.dto.StoreResponseDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.service.StoreService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    @Autowired
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreResponseDto> getAllStores(
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

    @GetMapping("/search")
    public ResponseEntity<?> searchStores(
            @RequestParam("searchStore") String searchStore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Store> storePage = storeService.searchStores(searchStore, page, size);

        if (storePage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색한 매장이 없습니다.");
        }

        List<StoreResponseDto> response = storePage.stream().map(store -> new StoreResponseDto(
                store.getStoreId(),
                store.getRate(),
                store.getStoreName(),
                store.getDialNumber(),
                store.getIntro()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // 매장 등록 (점주 유저)
    @PostMapping
    public ResponseEntity<?> createStore(
            @RequestHeader("Authorization") String token,
            @RequestBody StoreRequestDto storeRequestDto) {
        try {
            boolean isOwner = storeService.isOwner(token);
            if (!isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("점주만 매장을 등록할 수 있습니다.");
            }
            User user = storeService.getUserFromToken(token);
            storeService.createStore(storeRequestDto, user);
            return ResponseEntity.status(HttpStatus.OK).body("매장등록 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    // 매장 수저 (점주 유저)
    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStore(
            @RequestHeader("Authorization") String token,
            @PathVariable Long storeId,
            @RequestBody StoreRequestDto storeRequestDto) {
        try {
            boolean isOwner = storeService.isOwner(token);
            if (!isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("점주만 매장을 수정할 수 있습니다.");
            }
            User user = storeService.getUserFromToken(token);
            storeService.updateStore(storeId, storeRequestDto, user);
            return ResponseEntity.status(HttpStatus.OK).body("매장수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }
}
