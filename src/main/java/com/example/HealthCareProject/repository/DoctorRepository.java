package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//    @Query("SELECT u FROM Doctor u WHERE u.phoneNumber=?1")
//    Optional<Doctor> findByPhoneNumber(long phoneNumber);
}
