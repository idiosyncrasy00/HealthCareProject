package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
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
    public CustomeResponseEntity<?> makeAppointment(@RequestBody Appointment appointment,
                                                    @RequestParam long id
    ) {
        return appointmentService.makeAppointment(appointment, id);
    }

    @PostMapping("/accept")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> acceptAppointment(@RequestParam long appointmentId,
                                               @RequestParam long doctorId,
                                               @RequestParam long id
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return appointmentService.acceptAppointment(appointmentId, doctorId);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> rejectAppointment(@RequestParam long appointmentId, @RequestParam long doctorId,
                                               @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return appointmentService.rejectAppointment(appointmentId);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> deleteAppointment(@RequestParam long appointmentId, @RequestParam long patientId,
                                               @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) <= 0) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return appointmentService.deleteAppointment(appointmentId);
    }

}
