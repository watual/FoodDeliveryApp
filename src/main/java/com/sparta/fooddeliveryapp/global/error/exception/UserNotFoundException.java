package com.sparta.fooddeliveryapp.global.error.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException() {
        super("해당 사용자를 찾을 수 없습니다.");
    }
}
