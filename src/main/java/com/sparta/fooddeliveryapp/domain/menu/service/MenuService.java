package com.sparta.fooddeliveryapp.domain.menu.service;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public Page<Menu> getAllMenu(Long storeId, int page, int size) {
        return menuRepository.findByStoreId(storeId, PageRequest.of(page, size));
    }
}
