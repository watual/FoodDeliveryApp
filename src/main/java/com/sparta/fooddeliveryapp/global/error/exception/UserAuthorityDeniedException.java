package com.sparta.fooddeliveryapp.global.error.exception;

public class UserAuthorityDeniedException extends IllegalArgumentException {
    public UserAuthorityDeniedException() {
        super("다른 사용자의 정보를 접근할수 없습니다.");
    }
}
