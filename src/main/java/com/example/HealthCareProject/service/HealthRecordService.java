package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> viewHealthRecordByPatient(Long healthRecordId, Long patientId) {
        boolean isPatientExists = patientRepository.findById(patientId).isPresent();
        if (!isPatientExists) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO(StatusCode.NotFoundCode, "Patient with id " + patientId + " does not exists!"));
            //throw new IllegalStateException("Patient with id " + patientId + " does not exists!");
        }
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId).
                orElseThrow(() -> new IllegalStateException("health record with id " + healthRecordId + " does not exist!"));
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO(StatusCode.SuccessCode, "Success", healthRecord));
    }

    public ResponseEntity<?> viewHealthRecordByDoctor(Long doctorId, Long patientId) {
        int checkAcceptedAppointment = appointmentRepository.getAcceptedAppointment(doctorId, patientId);
        if (checkAcceptedAppointment <= 0) {
            //throw new IllegalStateException("Doctor cannot view this patient's health record!");
            return ResponseEntity.status(StatusCode.BadRequestCode).body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "Doctor cannot view this patient's health record!"));
        }

        HealthRecord healthRecord = healthRecordRepository.findByPatientID(patientId);
        HealthRecordDTO viewHealthRecordDTO = HealthRecordDTO
                .builder()
                .id(healthRecord.getId())
                .bloodType(healthRecord.getBloodType())
                .height(healthRecord.getHeight())
                .heightUnit(healthRecord.getHeightUnit())
                .weight(healthRecord.getWeight())
                .weightUnit(healthRecord.getWeightUnit())
                .patient(patientId)
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                viewHealthRecordDTO));
    }

    public ResponseEntity<?> addHealthRecord(HealthRecord healthRecord) {
        long patientID = healthRecord.getPatient().getId();
        boolean isPatient = patientRepository.findById(patientID).isPresent();

        if (!isPatient) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Patient with " + patientID + " does not exist!"));
//            throw new IllegalStateException("Patient with " + patientID + " does not exist!");
        }
        healthRecordRepository.save(healthRecord);
        HealthRecordDTO healthRecordDTO = HealthRecordDTO.builder()
                .id(healthRecord.getId())
                .bloodType(healthRecord.getBloodType())
                .height(healthRecord.getHeight())
                .heightUnit(healthRecord.getHeightUnit())
                .weight(healthRecord.getWeight())
                .weightUnit(healthRecord.getWeightUnit())
                .patient(healthRecord.getPatient().getId())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                healthRecordDTO));
    }

    @Transactional
    public ResponseEntity<?> editHealthRecord(HealthRecordDTO editHealthRecordDTO,
                                                               Long healthRecordID, Long patientID) {
        boolean isPatient = patientRepository.findById(patientID).isPresent();
        Optional<HealthRecord> currentHealthRecord = healthRecordRepository.findById(healthRecordID);
        if (!isPatient) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Patient with " + patientID + " does not exist!"));
        }

        if (!currentHealthRecord.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Health record id " + healthRecordID + " does not exists!"));
        }

        currentHealthRecord.get().setBloodType(editHealthRecordDTO.getBloodType());
        currentHealthRecord.get().setHeight(editHealthRecordDTO.getHeight());
        currentHealthRecord.get().setHeightUnit(editHealthRecordDTO.getHeightUnit());
        currentHealthRecord.get().setWeight(editHealthRecordDTO.getWeight());
        currentHealthRecord.get().setWeightUnit(editHealthRecordDTO.getWeightUnit());
        currentHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        HealthRecordDTO healthRecordResponse = HealthRecordDTO.builder()
                .id(currentHealthRecord.get().getId())
                .bloodType(currentHealthRecord.get().getBloodType())
                .height(currentHealthRecord.get().getHeight())
                .heightUnit(currentHealthRecord.get().getHeightUnit())
                .weight(currentHealthRecord.get().getWeight())
                .weightUnit(currentHealthRecord.get().getWeightUnit())
                .patient(patientID)
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                healthRecordResponse));
    }
}
