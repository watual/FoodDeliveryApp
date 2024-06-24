package com.sparta.fooddeliveryapp.global.security;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.exception.LoggedOutUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UsernameNotFoundException("Not Found : " + loginId)
        );
        // 로그아웃 된 유저 걸러내기, 로그아웃 하면 accesstoken 살아있어도 인가 막아버림
        if(user.getRefreshToken() == null){
            throw new LoggedOutUserException();
        }

        return new UserDetailsImpl(user);
    }
}
