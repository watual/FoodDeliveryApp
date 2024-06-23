package com.sparta.fooddeliveryapp.domain.user.repository;

import com.sparta.fooddeliveryapp.domain.user.entity.UsedPassword;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedPasswordRepository extends JpaRepository<UsedPassword, Long> {
    @Query(value = "SELECT * FROM used_password WHERE user_id = ?1 ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<UsedPassword> findFourOldestValueByUserId(Long userId);
}
