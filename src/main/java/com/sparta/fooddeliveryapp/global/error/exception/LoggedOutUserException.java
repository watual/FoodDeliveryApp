package com.sparta.fooddeliveryapp.global.error.exception;

// 로그아웃된 유저 걸러내는 예외
public class LoggedOutUserException extends IllegalArgumentException{
    public LoggedOutUserException(){
        super("로그아웃 된 사용자입니다.");
    }
}
