package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/patient")
public class PatientController {
    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> registerPatient(@RequestBody PatientDTO.AddPatient patient, @RequestParam("userId") Long userId) {
        return patientService.addNewPatient(patient, userId);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> editDoctor(@RequestBody PatientDTO.EditPatient patient, @RequestParam("patientId") long patientId) {
        return patientService.editPatient(patient, patientId);
    }

}
