package com.sparta.fooddeliveryapp.domain.menu.service;

import com.sparta.fooddeliveryapp.domain.menu.dto.MenuRequestDto;
import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Page<Menu> getAllMenu(Long storeId, int page, int size) {
        return menuRepository.findByStoreId(storeId, PageRequest.of(page, size));
    }

    public void createMenu(MenuRequestDto menuRequestDto) {
        Menu menu = new Menu();
        menu.setStoreId(menuRequestDto.getStoreId());
        menu.setMenuName(menuRequestDto.getMenuName());
        menu.setIntro(menuRequestDto.getIntro());
        menu.setPrice(menuRequestDto.getPrice());
        menuRepository.save(menu);
    }
}
