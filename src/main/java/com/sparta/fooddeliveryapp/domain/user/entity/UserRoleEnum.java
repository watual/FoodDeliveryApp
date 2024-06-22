package com.sparta.fooddeliveryapp.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    USER(Authority.USER),      // 일반 사용자
    SELLER(Authority.SELLER),  // 판매자
    ADMIN(Authority.ADMIN);    // 어드민

    private final String authority;

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String SELLER = "ROLE_SELLER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
