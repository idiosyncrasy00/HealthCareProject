package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
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

    public int checkHealthRecordIdIsPatientId(long healthRecordId, long patientId) {
        return healthRecordRepository.checkHealthRecordIdIsPatientId(healthRecordId, patientId);
    }

    public CustomeResponseEntity<?> viewHealthRecordByPatient(Long healthRecordId, Long patientId) {
        boolean isPatientExists = patientRepository.findById(patientId).isPresent();
        if (!isPatientExists) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "Patient with id " + patientId + " does not exists!"), HttpStatus.NOT_FOUND);
        }
        boolean isHealthRecordExists = healthRecordRepository.findById(healthRecordId).isPresent();
        if (!isHealthRecordExists) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "health record with id " + healthRecordId + " does not exist!"), HttpStatus.NOT_FOUND);
            //throw new IllegalStateException("Patient with id " + patientId + " does not exists!");
        }
        Optional<HealthRecord> healthRecord = healthRecordRepository.findById(healthRecordId);
                //orElseThrow(() -> new IllegalStateException("health record with id " + healthRecordId + " does not exist!"));
        //return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO(StatusCode.SuccessCode, "Success", healthRecord));
        return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.SuccessCode, healthRecord), HttpStatus.OK);

    }

    public CustomeResponseEntity<?> viewHealthRecordByDoctor(Long doctorId, Long patientId) {
        int checkAcceptedAppointment = appointmentRepository.countByStatusEqualsAndDoctorIdAndPatientId(1,doctorId, patientId);
        if (checkAcceptedAppointment <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "Doctor cannot view this patient's health record!"), HttpStatus.NOT_FOUND);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.SuccessCode, viewHealthRecordDTO), HttpStatus.OK);
    }

    public CustomeResponseEntity<?> addHealthRecord(HealthRecordDTO healthRecordDTO, long id, long patientId) {
//        long patientID = healthRecord.getPatient().getId();
        boolean isPatient = patientRepository.findById(patientId).isPresent();

        if (!isPatient) {
//            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                    "Patient with " + patientId + " does not exist!"));
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "Patient with " + patientId + " does not exist!"), HttpStatus.NOT_FOUND);
        }
        Patient patient = new Patient();
        patient.setId(patientId);
        HealthRecord healthRecord = HealthRecord.builder()
                .height(healthRecordDTO.getHeight())
                .heightUnit(healthRecordDTO.getHeightUnit())
                .weight(healthRecordDTO.getWeight())
                .weightUnit(healthRecordDTO.getWeightUnit())
                .bloodType(healthRecordDTO.getBloodType())
                .patient(patient)
                .build();
        healthRecordRepository.save(healthRecord);
        HealthRecordDTO response = HealthRecordDTO.builder()
                .id(healthRecord.getId())
                .bloodType(healthRecord.getBloodType())
                .height(healthRecord.getHeight())
                .heightUnit(healthRecord.getHeightUnit())
                .weight(healthRecord.getWeight())
                .weightUnit(healthRecord.getWeightUnit())
                .patient(healthRecord.getPatient().getId())
                .build();
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response), HttpStatus.OK);
    }

    @Transactional
    public CustomeResponseEntity<?> editHealthRecord(HealthRecordDTO editHealthRecordDTO,
                                                               Long healthRecordID, Long patientID) {
        boolean isPatient = patientRepository.findById(patientID).isPresent();
        Optional<HealthRecord> currentHealthRecord = healthRecordRepository.findById(healthRecordID);
        if (!isPatient) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "Patient with " + patientID + " does not exist!"), HttpStatus.NOT_FOUND);
        }

        if (!currentHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "Health record id " + healthRecordID + " does not exists!"), HttpStatus.NOT_FOUND);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                healthRecordResponse), HttpStatus.OK);
    }
}
