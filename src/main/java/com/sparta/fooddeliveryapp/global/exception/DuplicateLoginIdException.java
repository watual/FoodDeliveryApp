package com.sparta.fooddeliveryapp.global.exception;

public class DuplicateLoginIdException extends IllegalArgumentException {
    public DuplicateLoginIdException() {
        super("중복된 login ID 입니다");
    }
}
