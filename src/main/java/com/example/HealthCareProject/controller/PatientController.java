package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.HealthRecordRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import com.example.HealthCareProject.service.PatientService;
import com.example.HealthCareProject.utils.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/patient")
public class PatientController {
    private PatientService patientService;
    private PatientRepository patientRepository;
    private UserDataRepository userDataRepository;

    @Autowired
    public PatientController(PatientService patientService, PatientRepository patientRepository,
                             UserDataRepository userDataRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.userDataRepository = userDataRepository;
    }

    @GetMapping("/view/appointments")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> getAppointmentsFromPatient(@RequestParam long patientId,
                                                               @RequestParam String doctorFullName,
                                                               @RequestParam(required = false) Collection<Integer> status,
                                                               @RequestParam long id,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "2") int size) throws JsonProcessingException {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.OK,
                    null
            );
        }
        try {
            List<AppointmentDTO> appointments = patientService.getAppointments(patientId, doctorFullName, status, page, size);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    appointments
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return patientService.getAppointments(patientId, doctorFullName, status, page, size);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> registerPatient(@RequestBody PatientDTO.AddPatient patient, @RequestParam long id) {
        Optional<UserData> userData = userDataRepository.findById(id);
        if (!userData.isPresent()) {
            return ResponseHandler.generateResponse("User with id " + id + " not found.",
                    HttpStatus.OK,
                    null
            );
        }
        try {
            PatientDTO.AddPatientResponse addPatientResponse = patientService.addNewPatient(patient, id);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    addPatientResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> editPatient(@RequestBody PatientDTO.EditPatient patient, @RequestParam("patientId") long patientId,
                                         @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.OK,
                    null
            );
        }

        Optional<Patient> currentPatient = patientRepository.findById(patientId);

        if (!currentPatient.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                    "Patient with id " + patientId + " not found."), HttpStatus.NOT_FOUND);
            return ResponseHandler.generateResponse("Patient with id " + patientId + " not found.",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            PatientDTO.EditPatientResponse editPatientResponse = patientService.editPatient(patient, patientId);
            return ResponseHandler.generateResponse("Success",
                    HttpStatus.OK,
                    editPatientResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return patientService.editPatient(patient, patientId);
    }

}
