package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    @Query(value = "SELECT * FROM health_record h where h.patient_id=?1", nativeQuery = true)
    HealthRecord findByPatientID(Long patientID);
}
