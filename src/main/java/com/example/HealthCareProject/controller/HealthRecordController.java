package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.dto.HealthRecordDTO;
import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.HealthRecordService;
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
    @Autowired
    public HealthRecordController(HealthRecordService healthRecordService) {
        this.healthRecordService = healthRecordService;
    }

    @GetMapping(value="/view/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> viewHealthRecordByPatient(@RequestParam("patientId") Long patientId,
                                                       @RequestParam("healthRecordId") Long healthRecordId) {
        return new ResponseEntity<>(healthRecordService.viewHealthRecordByPatient(healthRecordId, patientId), HttpStatus.OK);
    }

    @GetMapping("/view/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> viewHealthRecordByDoctor(@RequestParam("doctorId") Long doctorId,
                                                      @RequestParam("healthRecordId") Long healthRecordId) {
        return new ResponseEntity<>(healthRecordService.viewHealthRecordByDoctor(doctorId,healthRecordId), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> addHealthRecord(@RequestBody HealthRecord healthRecord) {
        return new ResponseEntity<>(healthRecordService.addHealthRecord(healthRecord), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> editHealthRecord(@RequestBody HealthRecordDTO.EditHealthRecordDTO editHealthRecordDTO,
                                              @RequestParam("healthRecordID") Long healthRecordID,
                                              @RequestParam("patientID") Long patientID) {
        return new ResponseEntity<>(healthRecordService.editHealthRecord(editHealthRecordDTO, healthRecordID, patientID), HttpStatus.OK);
    }
}
