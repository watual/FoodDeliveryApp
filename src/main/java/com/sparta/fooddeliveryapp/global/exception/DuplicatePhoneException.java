package com.sparta.fooddeliveryapp.global.exception;

public class DuplicatePhoneException extends IllegalArgumentException {
    public DuplicatePhoneException() {
        super("중복된 핸드폰 번호 입니다.");
    }
}
