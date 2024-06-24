package com.sparta.fooddeliveryapp.domain.menu.controller;

import com.sparta.fooddeliveryapp.domain.menu.dto.MenuRequestDto;
import com.sparta.fooddeliveryapp.domain.menu.dto.MenuResponseDto;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    // 해당 매장에 특정 메뉴를 조회함
    @GetMapping
    public ResponseEntity<?> getAllMenu(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Menu> menuPage = menuService.getAllMenu(storeId, page, size);

        if (menuPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 매장에 메뉴가 없습니다.");
        }

        List<MenuResponseDto> response = menuPage.stream().map(menu -> new MenuResponseDto(
                menu.getStore().getStoreId(),
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getIntro(),
                menu.getPrice()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // 메뉴 등록 (점주 유저)
    @PostMapping
    public ResponseEntity<?> createMenu(@RequestHeader("Authorization") String token, @RequestBody MenuRequestDto menuRequestDto) {
        menuService.createMenu(menuRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴등록 완료");
    }

    // 메뉴 수정 (점주 유저)
    @PatchMapping("/{menuId}")
    public ResponseEntity<?> updateMenu(
            @RequestHeader("Authorization") String token,
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto menuRequestDto) {
        menuService.updateMenu(menuId, menuRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴수정 완료");
    }

    // 메뉴 삭제 (점주 유저)
    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> deleteMenu(
            @RequestHeader("Authorization") String token,
            @PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴삭제 완료");
    }
}