package com.sparta.fooddeliveryapp.domain.user.service;

import com.sparta.fooddeliveryapp.domain.user.dto.*;
import com.sparta.fooddeliveryapp.domain.user.entity.UsedPassword;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.domain.user.entity.UserStatusEnum;
import com.sparta.fooddeliveryapp.domain.user.repository.UsedPasswordRepository;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.exception.*;
import com.sparta.fooddeliveryapp.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.query.results.Builders.fetch;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsedPasswordRepository usedpasswordRepository;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UsedPasswordRepository usedPasswordRepository;


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

        UsedPassword newPassword = new UsedPassword();
        newPassword.setUser(user);
        newPassword.setPassword(password);
        List<UsedPassword> pwdList = new ArrayList<>();
        pwdList.add(newPassword);
        user.setUsedPasswordList(pwdList);

        userRepository.save(user);

        log.info("회원가입 완료");
    }

    @Transactional
    public void deactivateUser(DeactivateRequestDto requestDto, User user) {
        User tempUser = loadUserByLoginId(user.getLoginId());
        if(!( passwordEncoder.matches(requestDto.getPassword(), tempUser.getPassword()) )) {
            throw new WrongPasswordException();
        }
        tempUser.setStatusDeactivated();
        log.info("user deactivated");
    }

    @Transactional
    public void updateProfile(UpdateProfileRequestDto requestDto, User user) {
        User tempUser = loadUserByLoginId(user.getLoginId());
        if(requestDto.getName() != null) {
            tempUser.updateName(requestDto.getName());
        }
        if(requestDto.getNickname() != null) {
            tempUser.updateNickname(requestDto.getNickname());
        }
        if(requestDto.getAddress() != null) {
            tempUser.updateAddress(requestDto.getAddress());
        }
        if(requestDto.getIntro() != null) {
            tempUser.updateIntro(requestDto.getIntro());
        }
        log.info("profile updated");
    }

    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
    }

    public ProfileResponseDto getProfile(User user) {

        Long userId = user.getUserId();
        String name = user.getName();
        String nickname = user.getNickname();
        String address = user.getAddress();
        String phone = user.getPhone();
        String email = user.getEmail();
        String intro = user.getIntro();

        ProfileResponseDto result = new ProfileResponseDto(userId, name, nickname, address, phone, email, intro);
        log.info("프로필 조회 완료");
        return result;
    }

    public List<UserSearchResponseDto> UserSearch(String nickname) {
        List<User> searchedUsers = userRepository.findAllByNickname(nickname);
        List<UserSearchResponseDto> result =new ArrayList<>();
        for(User user : searchedUsers){
            String email = user.getEmail();
            String intro = user.getIntro();
            UserStatusEnum status = user.getStatus();
            result.add(new UserSearchResponseDto(nickname, email, intro, status));
        }
        log.info("유저 조회 완료");
        return result;
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        User user = loadUserByLoginId(jwtUtil.extractLoginId(accessToken));

        // 서버에서 인증 상태를 초기화
        LocalDateTime accessExpiration = jwtUtil.extractAllClaims(accessToken).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime refreshExpiration = jwtUtil.extractAllClaims(refreshToken).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        SecurityContextHolder.clearContext();
        user.setRefreshToken(null);

        log.info("logout success");
    }



    @Transactional
    public void updatePassword(UpdatePasswordRequestDto requestDto, User user) {
        User tempUser = loadUserByLoginId(user.getLoginId());

        if (!passwordEncoder.matches(requestDto.getOldPassword(), tempUser.getPassword())) {
            throw new WrongPasswordException();
        }

        // 가장 최근 4개의 비번을 가져온다(현재 비번 포함)
        List<UsedPassword> pwdList = usedPasswordRepository.findAllByUserId(tempUser.getUserId());

        log.info(String.valueOf(pwdList.size()));
        // Check if the new password has been used before
        for (UsedPassword pwd : pwdList) {
            if (passwordEncoder.matches(requestDto.getNewPassword(), pwd.getPassword())) {
                log.info("전에 4번 과 같은 비번");
                throw new PasswordUpdateFailException();
            }
        }

        String newPwd = passwordEncoder.encode(requestDto.getNewPassword());
        UsedPassword newPassword = new UsedPassword();
        newPassword.setUser(tempUser);
        newPassword.setPassword(newPwd);

        pwdList.add(newPassword);

        log.info("------");
        log.info(String.valueOf(pwdList.size()));
        if(pwdList.size() >= 5){
            Long pwdId = usedpasswordRepository.findOldestValueByUserId(tempUser.getUserId());
            usedPasswordRepository.deleteById(pwdId);
        }
        usedPasswordRepository.save(newPassword);
        tempUser.updatePassword(newPwd);
    }
}
