package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.HealthRecordRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.RequestViewHealthRecordRepository;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.HealthRecordService;
import com.example.HealthCareProject.service.PatientService;
import com.example.HealthCareProject.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/health")
public class HealthRecordController {
    private HealthRecordService healthRecordService;
    private HealthRecordRepository healthRecordRepository;

    private PatientService patientService;
    private DoctorService doctorService;
    private PatientRepository patientRepository;
    private RequestViewHealthRecordRepository requestViewHealthRecordRepository;
    @Autowired
    public HealthRecordController(HealthRecordService healthRecordService, DoctorService doctorService, PatientService patientService,
    PatientRepository patientRepository, RequestViewHealthRecordRepository requestViewHealthRecordRepository,
                                  HealthRecordRepository healthRecordRepository) {
        this.healthRecordService = healthRecordService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.requestViewHealthRecordRepository = requestViewHealthRecordRepository;
        this.healthRecordRepository = healthRecordRepository;
    }

    @GetMapping(value="/view/patient")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> viewHealthRecordByPatient(@RequestParam("patientId") Long patientId,
                                                       @RequestParam("healthRecordId") Long healthRecordId,
    @RequestParam Long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("success",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        boolean isPatientExists = patientRepository.findById(patientId).isPresent();
        if (!isPatientExists) {
            return ResponseHandler.generateResponse("Patient with id " + patientId + " does not exists!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        boolean isHealthRecordExists = healthRecordRepository.findById(healthRecordId).isPresent();
        if (!isHealthRecordExists) {
            return ResponseHandler.generateResponse("Health Record with id " + healthRecordId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            HealthRecord healthRecord = healthRecordService.viewHealthRecordByPatient(healthRecordId, patientId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    healthRecord
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return healthRecordService.viewHealthRecordByPatient(healthRecordId, patientId);
    }

    @GetMapping("/view/doctor")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> viewHealthRecordByDoctor(@RequestParam("doctorId") Long doctorId,
                                                      @RequestParam("patientId") Long patientId,
                                                      @RequestParam Long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        int check = requestViewHealthRecordRepository.countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 1);
        if (check <= 0) {
            return ResponseHandler.generateResponse("Doctor cannot view this patient's health record!",
                    HttpStatus.NOT_FOUND,
                    null
            );
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "Doctor cannot view this patient's health record!"), HttpStatus.NOT_FOUND);
        }

        try {
            HealthRecordDTO healthRecordDTO = healthRecordService.viewHealthRecordByDoctor(doctorId,patientId);
            return ResponseHandler.generateResponse("Success",
                    HttpStatus.OK,
                    healthRecordDTO
            );
            //return healthRecordService.viewHealthRecordByDoctor(doctorId,patientId);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return healthRecordService.viewHealthRecordByDoctor(doctorId,patientId);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> addHealthRecord(@RequestBody HealthRecordDTO healthRecordDTO,
                                             @RequestParam long id,
                                             @RequestParam long patientId) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        boolean isPatient = patientRepository.findById(patientId).isPresent();

        if (!isPatient) {
            return ResponseHandler.generateResponse("Patient with " + patientId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            HealthRecordDTO healthRecord = healthRecordService.addHealthRecord(healthRecordDTO, id, patientId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    healthRecord
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> editHealthRecord(@RequestBody HealthRecordDTO editHealthRecordDTO,
                                              @RequestParam("healthRecordID") Long healthRecordID,
                                              @RequestParam Long patientId,
                                              @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        if (healthRecordRepository.checkHealthRecordIdIsPatientId(healthRecordID, patientId) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        boolean isPatient = patientRepository.findById(patientId).isPresent();
        Optional<HealthRecord> currentHealthRecord = healthRecordRepository.findById(healthRecordID);
        if (!isPatient) {
            return ResponseHandler.generateResponse("Patient with " + patientId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        if (!currentHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("Health record id " + healthRecordID + " does not exists!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            HealthRecordDTO healthRecordDTO = healthRecordService.editHealthRecord(editHealthRecordDTO, healthRecordID, patientId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    healthRecordDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
