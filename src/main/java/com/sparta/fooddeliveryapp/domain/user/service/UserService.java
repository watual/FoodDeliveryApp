package com.sparta.fooddeliveryapp.domain.user.service;

import com.sparta.fooddeliveryapp.domain.user.dto.SignupRequestDto;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.domain.user.entity.UserStatusEnum;
import com.sparta.fooddeliveryapp.global.exception.DuplicateEmailException;
import com.sparta.fooddeliveryapp.global.exception.DuplicatePhoneException;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.exception.DuplicateLoginIdException;
import com.sparta.fooddeliveryapp.global.exception.WrongAdminTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

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

        // 중복 체크
        Optional<User> checkLoginId = userRepository.findByLoginId(loginId);
        if(checkLoginId.isPresent()){
            throw new DuplicateLoginIdException();
        }
        Optional<User> checkPhone = userRepository.findByPhone(phone);
        if(checkPhone.isPresent()){
            throw new DuplicatePhoneException();
        }
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()){
            throw new DuplicateEmailException();
        }

        // admin token 확인
        if (role == UserRoleEnum.ADMIN) {
            if (!ADMIN_TOKEN.equals(signupRequestDto.getAdminToken())) {
                throw new WrongAdminTokenException();
            }
        }

        User user = new User(loginId, password, name, nickname, address, phone, email, intro, role, status);
        userRepository.save(user);
        log.info("회원가입 완료");
    }
}
