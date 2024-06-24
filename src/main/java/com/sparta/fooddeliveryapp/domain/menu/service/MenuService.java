package com.sparta.fooddeliveryapp.domain.menu.service;

import com.sparta.fooddeliveryapp.domain.menu.dto.MenuRequestDto;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public Page<Menu> getAllMenu(Long storeId, int page, int size) {
        Store store = storeRepository.findById(storeId).orElseThrow(UserNotFoundException::new);
        return menuRepository.findByStore(store, PageRequest.of(page, size));
    }

    public void createMenu(MenuRequestDto menuRequestDto) {
        Menu menu = new Menu();
        menu.setStore(findStoreById(menuRequestDto.getStoreId()));
        menu.setMenuName(menuRequestDto.getMenuName());
        menu.setIntro(menuRequestDto.getIntro());
        menu.setPrice(menuRequestDto.getPrice());
        menuRepository.save(menu);
    }

    public void updateMenu(Long menuId, MenuRequestDto menuRequestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다."));
        menu.setStore(findStoreById(menuRequestDto.getStoreId()));
        menu.setMenuName(menuRequestDto.getMenuName());
        menu.setIntro(menuRequestDto.getIntro());
        menu.setPrice(menuRequestDto.getPrice());
        menuRepository.save(menu);
    }

    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다."));
        menuRepository.delete(menu);
    }

    // 메뉴에 대한 작업시 해당 메뉴를 가진 가게가 존재하는지, 폐업했는지 확인
    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(UserNotFoundException::new);
    }
}