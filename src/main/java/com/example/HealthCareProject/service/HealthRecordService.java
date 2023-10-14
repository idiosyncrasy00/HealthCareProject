package com.example.HealthCareProject.service;

import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class HealthRecordService {
    private final HealthRecordRepository healthRecordRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public HealthRecordService(HealthRecordRepository healthRecordRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.healthRecordRepository = healthRecordRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public HealthRecord viewHealthRecordByPatient(Long healthRecordId, Long patientId) {
        boolean isPatientExists = patientRepository.findById(patientId).isPresent();
        if (!isPatientExists) {
            throw new IllegalStateException("Patient with id " + patientId + " does not exists!");
        }
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId).
                orElseThrow(() -> new IllegalStateException("health record with id " + healthRecordId + " does not exist!"));
        
        return healthRecord;
    }

    public HealthRecordDTO.viewHealthRecordDTO viewHealthRecordByDoctor(Long doctorId, Long patientId) {
        int checkAcceptedAppointment = appointmentRepository.getAcceptedAppointment(doctorId, patientId);
        if (checkAcceptedAppointment <= 0) {
            throw new IllegalStateException("Doctor cannot view this patient's health record!");
        }

        HealthRecord healthRecord = healthRecordRepository.findByPatientID(patientId);
        HealthRecordDTO.viewHealthRecordDTO viewHealthRecordDTO = HealthRecordDTO.viewHealthRecordDTO
                .builder()
                .id(healthRecord.getId())
                .bloodType(healthRecord.getBloodType())
                .height(healthRecord.getHeight())
                .heightUnit(healthRecord.getHeightUnit())
                .weight(healthRecord.getWeight())
                .weightUnit(healthRecord.getWeightUnit())
                .patient_id(patientId)
                .build();
        return viewHealthRecordDTO;
    }

    public HealthRecord addHealthRecord(HealthRecord healthRecord) {
        long patientID = healthRecord.getPatient_id().getId();
        boolean isPatient = patientRepository.findById(patientID).isPresent();
        if (!isPatient) {
            throw new IllegalStateException("Patient with " + patientID + " does not exist!");
        }
        healthRecordRepository.save(healthRecord);
        return healthRecord;
    }

    @Transactional
    public HealthRecordDTO editHealthRecord(HealthRecordDTO.EditHealthRecordDTO editHealthRecordDTO,
                                                               Long healthRecordID, Long patientID) {
        boolean isPatient = patientRepository.findById(patientID).isPresent();
        if (!isPatient) {
            throw new IllegalStateException("Patient with " + patientID + " does not exist!");
        }
        HealthRecord currentHealthRecord = healthRecordRepository.findById(healthRecordID).orElseThrow(
                () -> new IllegalStateException("Health record id " + healthRecordID + " does not exists!")
        );
        currentHealthRecord.setBloodType(editHealthRecordDTO.getBloodType());
        currentHealthRecord.setHeight(editHealthRecordDTO.getHeight());
        currentHealthRecord.setHeightUnit(editHealthRecordDTO.getHeightUnit());
        currentHealthRecord.setWeight(editHealthRecordDTO.getWeight());
        currentHealthRecord.setWeightUnit(editHealthRecordDTO.getWeightUnit());

        HealthRecordDTO healthRecordResponse = HealthRecordDTO.builder()
                .id(currentHealthRecord.getId())
                .bloodType(currentHealthRecord.getBloodType())
                .height(currentHealthRecord.getHeight())
                .heightUnit(currentHealthRecord.getHeightUnit())
                .weight(currentHealthRecord.getWeight())
                .weightUnit(currentHealthRecord.getWeightUnit())
                .patient_id(patientID)
                .build();
        return healthRecordResponse;
    }
}
