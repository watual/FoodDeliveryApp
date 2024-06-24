package com.sparta.fooddeliveryapp.global.error.exception;

public class WrongPasswordException extends IllegalArgumentException {
    public WrongPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
