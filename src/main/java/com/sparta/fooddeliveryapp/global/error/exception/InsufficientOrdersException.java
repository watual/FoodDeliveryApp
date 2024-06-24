package com.sparta.fooddeliveryapp.global.error.exception;

public class InsufficientOrdersException extends RuntimeException{
    public InsufficientOrdersException(String message) {
        super(message);
    }
}
