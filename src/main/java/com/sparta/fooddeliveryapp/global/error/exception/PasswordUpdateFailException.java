package com.sparta.fooddeliveryapp.global.error.exception;

public class PasswordUpdateFailException extends IllegalArgumentException{
    public PasswordUpdateFailException() {
        super("새 비밀번호는 최근 사용한 4개의 비밀번호와 달라야합니다.");
    }
}
