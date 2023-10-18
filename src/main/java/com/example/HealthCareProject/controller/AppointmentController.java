package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.service.AppointmentService;
import com.example.HealthCareProject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

//    @Autowired
//    public AppointmentController(AppointmentService appointmentService) {
//        this.appointmentService = appointmentService;
//    }

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> makeAppointment(@RequestBody Appointment appointment) {
//        return new ResponseEntity<>(appointmentService.makeAppointment(appointment), HttpStatus.OK);
        return appointmentService.makeAppointment(appointment);
    }

    @PostMapping("/accept/:appointmentID")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> acceptAppointment(@RequestParam long appointmentID) {
        return new ResponseEntity<>(appointmentService.acceptAppointment(appointmentID), HttpStatus.OK);
    }

    @PostMapping("/reject/:appointmentID")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> rejectAppointment(@RequestParam long appointmentID) {
        return new ResponseEntity<>(appointmentService.rejectAppointment(appointmentID), HttpStatus.OK);
    }

    @PostMapping("/delete/:appointmentID")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> deleteAppointment(@RequestParam long appointmentID) {
        return new ResponseEntity<>(appointmentService.deleteAppointment(appointmentID), HttpStatus.OK);
    }

}
