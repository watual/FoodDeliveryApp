package com.sparta.fooddeliveryapp.domain.store.controller;

import com.sparta.fooddeliveryapp.domain.store.dto.StoreRequestDto;
import com.sparta.fooddeliveryapp.domain.store.dto.StoreResponseDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.service.StoreService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody StoreRequestDto storeRequestDto) {
        storeService.createStore(storeRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("매장등록 완료")
                        .build());
    }

    // 매장 수정 (점주 유저)
    @PatchMapping("/{storeId}")
    public ResponseEntity<ResponseDto> updateStore(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long storeId,
            @RequestBody StoreRequestDto storeRequestDto) {

        storeService.updateStore(storeId, storeRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseDto.builder()
                            .status(HttpStatus.OK)
                            .message("매장수정 완료")
                            .build());
    }

    // 매장 삭제 (점주 유저)
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long storeId) {
        storeService.deleteStore(storeId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("매장삭제 완료")
                        .build());
    }
}