package com.sparta.fooddeliveryapp.domain.user.repository;

import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    // createStore 유저 정보 가져오기
    Optional<User> findByName(String name);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    List<User> findAllByNickname(String nickname);

    Optional<User> findByKakaoId(Long kakaoId);

}
