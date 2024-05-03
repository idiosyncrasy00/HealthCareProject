package com.example.HealthCareProject.controller;


import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/doctor")
public class DoctorController {
    private DoctorService doctorService;
    private DoctorRepository doctorRepository;
    private UserDataRepository userDataRepository;
    @Autowired
    public DoctorController(DoctorService doctorService, DoctorRepository doctorRepository,
    UserDataRepository userDataRepository) {
        this.doctorService = doctorService;
        this.doctorRepository = doctorRepository;
        this.userDataRepository = userDataRepository;
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<Object> viewDoctorDetails(@RequestParam("doctorId") Long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findByDoctorID(doctorId);
        if (!doctor.isPresent()) {
            return ResponseHandler.generateResponse("doctor with id " + doctorId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            DoctorDTO.ViewDoctorResponse viewDoctorDetails = doctorService.viewDoctorDetails(doctorId);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, viewDoctorDetails);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> registerDoctor(@RequestBody DoctorDTO.AddDoctor doctor,
                                            @RequestParam("userId") Long userId) {
        Optional<UserData> userData = userDataRepository.findById(userId);
        if (userData.isEmpty()) {
            return ResponseHandler.generateResponse("user with id " + userId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            DoctorDTO.AddDoctorResponse addNewDoctor = doctorService.addNewDoctor(doctor, userId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    addNewDoctor
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> editDoctor(@RequestBody DoctorDTO.EditDoctor doctor,
                                        @RequestParam("doctorId") long doctorId, @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        Optional<Doctor> currentDoctor = doctorRepository.findByDoctorID(doctorId);
        if (!currentDoctor.isPresent()) {
            return ResponseHandler.generateResponse("doctor with id " + doctorId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            DoctorDTO.EditDoctorResponse editDoctorResponse = doctorService.editDoctor(doctor, doctorId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    editDoctorResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //long patientId, long doctorId, int status
    @GetMapping("/view/appointments")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> viewAppointmentsByDoctor(@RequestParam String patientFullName,
                                                      @RequestParam("doctorId") long doctorId,
                                                      @RequestParam(required = false) Collection<Integer> status,
                                                      @RequestParam long id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        try {
            List<AppointmentDTO> getAppointments = doctorService.getAppointments(patientFullName, doctorId, status, page, size);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    getAppointments
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
