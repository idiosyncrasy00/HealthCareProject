package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    @Query("SELECT u FROM UserData u WHERE u.email=?1")
    Optional<UserData> findByEmail(String email);

    @Query("SELECT u FROM UserData u WHERE u.id=?1")
    Optional<UserDataDTO> findByID(long id);

    @Query("SELECT u FROM UserData u WHERE u.username=?1")
    Optional<UserData> findByUserName(String username);

//    @Query("UPDATE UserData u SET u.username=?1, u.email where ")
//    Optional<UserData> updateUserData(long id);

    @Modifying
    @Query(value = "DELETE FROM UserData u WHERE u.email=?1", nativeQuery = true)
    int deleteByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
