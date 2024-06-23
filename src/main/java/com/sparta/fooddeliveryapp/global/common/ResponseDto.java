package com.sparta.fooddeliveryapp.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto {
    private final String status;
    private final String message;
    private final Object data;

    @Builder
    public ResponseDto(HttpStatus status, String message, Object data) {
        this.status = status.name() + " : " + status.value();
        this.message = message;
        this.data = data;
    }
}
