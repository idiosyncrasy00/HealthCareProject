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

    @PostMapping("/add/userId={userId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorDTO.AddDoctor doctor, @PathVariable Long userId) {
        return new ResponseEntity<>(doctorService.addNewDoctor(doctor, userId), HttpStatus.OK);
    }

    @PutMapping("/edit/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> editDoctor(@RequestBody DoctorDTO.EditDoctor doctor, @PathVariable long doctorId) {
        return new ResponseEntity<>(doctorService.editDoctor(doctor, doctorId), HttpStatus.OK);
    }
}
