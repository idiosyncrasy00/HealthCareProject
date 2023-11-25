package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.RequestViewHealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestViewHealthRecordRepository extends JpaRepository<RequestViewHealthRecord, Long> {
    int countByDoctorIdAndPatientIdAndStatus(long doctorId, long patientId, int status);
}
