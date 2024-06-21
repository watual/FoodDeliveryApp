package com.sparta.fooddeliveryapp.domain.user.service;

import com.sparta.fooddeliveryapp.domain.user.dto.SignupRequestDto;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.domain.user.entity.UserStatusEnum;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto) {
        String loginId = signupRequestDto.getLoginId();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String name = signupRequestDto.getName();
        String nickname = signupRequestDto.getNickname();
        String address = signupRequestDto.getAddress();
        String phone = signupRequestDto.getPhone();
        String email = signupRequestDto.getEmail();
        String intro = signupRequestDto.getIntro();
        UserRoleEnum role = signupRequestDto.getRole();
        UserStatusEnum status = UserStatusEnum.ACTIVE;

        User user = new User(loginId, password, name, nickname, address, phone, email, intro, role, status);
        userRepository.save(user);
        log.info("회원가입 완료");
    }
}
