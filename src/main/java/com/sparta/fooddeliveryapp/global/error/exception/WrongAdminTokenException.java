package com.sparta.fooddeliveryapp.global.error.exception;

public class WrongAdminTokenException extends IllegalArgumentException {
    public WrongAdminTokenException() {
        super("admin token 값이 일치하지 않습니다.");
    }
}
