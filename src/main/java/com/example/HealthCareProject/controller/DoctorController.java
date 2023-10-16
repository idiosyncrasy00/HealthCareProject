package com.example.HealthCareProject.controller;


import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/doctor")
public class DoctorController {
    private DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<?> viewDoctorDetails(@RequestParam("doctorId") Long doctorId) {
        return doctorService.viewDoctorDetails(doctorId);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorDTO.AddDoctor doctor,
                                            @RequestParam("userId") Long userId) {
        return doctorService.addNewDoctor(doctor, userId);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> editDoctor(@RequestBody DoctorDTO.EditDoctor doctor,
                                        @RequestParam("doctorId") long doctorId) {
        return doctorService.editDoctor(doctor, doctorId);
    }
}
