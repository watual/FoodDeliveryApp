package com.sparta.fooddeliveryapp.domain.user.repository;

import com.sparta.fooddeliveryapp.domain.user.entity.UsedPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedPasswordRepository extends JpaRepository<UsedPassword, Long> {

    @Query(value = "SELECT * FROM used_password WHERE user_id = ?1 ORDER BY id DESC", nativeQuery = true)
    List<UsedPassword> findAllByUserId(Long userId);

    @Query(value = "SELECT id FROM used_password WHERE user_id = ?1 ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Long findOldestValueByUserId(Long userId);
}
