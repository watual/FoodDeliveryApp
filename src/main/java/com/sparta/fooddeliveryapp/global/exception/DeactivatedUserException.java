package com.sparta.fooddeliveryapp.global.exception;

public class DeactivatedUserException extends IllegalArgumentException {
    public DeactivatedUserException() {
        super("비활성화된 유저입니다.");
    }
}
