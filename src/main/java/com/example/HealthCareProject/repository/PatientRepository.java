package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select count(*) from Patient p where p.id = ?1 and p.userData.id = ?2")
    int checkUserIdIsPatientId(long patientId, long userId);
}
