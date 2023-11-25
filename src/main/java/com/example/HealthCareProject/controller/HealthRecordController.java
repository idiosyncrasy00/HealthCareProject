package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.HealthRecordRepository;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.HealthRecordService;
import com.example.HealthCareProject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/health")
public class HealthRecordController {
    private HealthRecordService healthRecordService;
    private HealthRecordRepository healthRecordRepository;

    private PatientService patientService;
    private DoctorService doctorService;
    @Autowired
    public HealthRecordController(HealthRecordService healthRecordService, DoctorService doctorService, PatientService patientService) {
        this.healthRecordService = healthRecordService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping(value="/view/patient")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> viewHealthRecordByPatient(@RequestParam("patientId") Long patientId,
                                                       @RequestParam("healthRecordId") Long healthRecordId,
    @RequestParam Long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return healthRecordService.viewHealthRecordByPatient(healthRecordId, patientId);
    }

    @GetMapping("/view/doctor")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> viewHealthRecordByDoctor(@RequestParam("doctorId") Long doctorId,
                                                      @RequestParam("patientId") Long patientId,
                                                      @RequestParam Long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return healthRecordService.viewHealthRecordByDoctor(doctorId,patientId);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> addHealthRecord(@RequestBody HealthRecordDTO healthRecordDTO,
                                             @RequestParam long id,
                                             @RequestParam long patientId) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
//            return ResponseEntity.status(StatusCode.BadRequestCode)
//                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
//                            "You cannot do this operation!"));
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return healthRecordService.addHealthRecord(healthRecordDTO, id, patientId);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> editHealthRecord(@RequestBody HealthRecordDTO editHealthRecordDTO,
                                              @RequestParam("healthRecordID") Long healthRecordID,
                                              @RequestParam Long patientId,
                                              @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        if (healthRecordRepository.checkHealthRecordIdIsPatientId(healthRecordID, patientId) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return healthRecordService.editHealthRecord(editHealthRecordDTO, healthRecordID, patientId);
    }
}
