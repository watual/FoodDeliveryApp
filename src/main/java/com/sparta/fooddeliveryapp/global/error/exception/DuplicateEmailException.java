package com.sparta.fooddeliveryapp.global.error.exception;

public class DuplicateEmailException extends IllegalArgumentException {
    public DuplicateEmailException() {
        super("중복된 이메일 주소 입니다.");
    }
}
