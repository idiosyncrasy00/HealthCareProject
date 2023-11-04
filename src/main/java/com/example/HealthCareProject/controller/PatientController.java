package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.HealthRecordRepository;
import com.example.HealthCareProject.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/patient")
public class PatientController {
    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/view/appointments")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> getAppointmentsFromPatient(@RequestParam long patientId,
                                                               @RequestParam String doctorFullName,
                                                               @RequestParam(required = false) Collection<Integer> status,
                                                               @RequestParam long id,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "2") int size) throws JsonProcessingException {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return patientService.getAppointments(patientId, doctorFullName, status, page, size);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> registerPatient(@RequestBody PatientDTO.AddPatient patient, @RequestParam long id) {
        return patientService.addNewPatient(patient, id);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> editPatient(@RequestBody PatientDTO.EditPatient patient, @RequestParam("patientId") long patientId,
                                         @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return patientService.editPatient(patient, patientId);
    }

}
