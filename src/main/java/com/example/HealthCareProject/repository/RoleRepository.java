package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.consts.ERole;
import com.example.HealthCareProject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
