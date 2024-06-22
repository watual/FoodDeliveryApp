package com.sparta.fooddeliveryapp.domain.menu.controller;

import com.sparta.fooddeliveryapp.domain.menu.dto.MenuResponseDto;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping // 해당 매장의 메뉴를 조회함
    public ResponseEntity<?> getAllMenu(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Menu> menuPage = menuService.getAllMenu(storeId, page, size);

        if (menuPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 매장의 메뉴가 없습니다.");
        }

        List<MenuResponseDto> response = menuPage.stream().map(menu -> new MenuResponseDto(
                menu.getStoreId(),
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getIntro(),
                menu.getPrice()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
