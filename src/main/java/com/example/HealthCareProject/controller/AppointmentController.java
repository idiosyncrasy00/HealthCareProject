package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.service.AppointmentService;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;

//    @Autowired
//    public AppointmentController(AppointmentService appointmentService) {
//        this.appointmentService = appointmentService;
//    }

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CommonMessageDTO makeAppointment(@RequestBody Appointment appointment,
                                             @RequestParam long id, HttpServletResponse res
    ) throws ServletException, IOException {
        return appointmentService.makeAppointment(appointment, id, res);
    }

    @PostMapping("/accept")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> acceptAppointment(@RequestParam long appointmentId,
                                               @RequestParam long doctorId,
                                               @RequestParam long id
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseEntity.status(StatusCode.BadRequestCode)
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"));
        }
        return new ResponseEntity<>(appointmentService.acceptAppointment(appointmentId, doctorId), HttpStatus.OK);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> rejectAppointment(@RequestParam long appointmentId, @RequestParam long doctorId,
                                               @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseEntity.status(StatusCode.BadRequestCode)
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"));
        }
        return new ResponseEntity<>(appointmentService.rejectAppointment(appointmentId), HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> deleteAppointment(@RequestParam long appointmentId, @RequestParam long patientId,
                                               @RequestParam long id, HttpServletResponse res) {
        if (patientService.checkUserIdIsPatientId(patientId, id) <= 0) {
            return ResponseEntity.status(StatusCode.BadRequestCode)
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"));
        }
        return new ResponseEntity<>(appointmentService.deleteAppointment(appointmentId, res), HttpStatus.OK);
    }

}
