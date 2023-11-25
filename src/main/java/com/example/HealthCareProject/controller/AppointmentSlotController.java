package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.service.AppointmentService;
import com.example.HealthCareProject.service.AppointmentSlotService;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/appointmentslot")
public class AppointmentSlotController {
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentSlotService appointmentSlotService;

    @Autowired
    public AppointmentSlotController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, AppointmentSlotService appointmentSlotService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentSlotService = appointmentSlotService;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> makeAppointmentSlot(@RequestBody AppointmentSlotDTO appointmentSlotDTO,
                                                        @RequestParam long id,
                                                        @RequestParam long doctorId
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return appointmentSlotService.makeAppointmentSlot(appointmentSlotDTO);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> getAppointmentSlotsByDoctorId(@RequestParam long id,
                                                                  @RequestParam long doctorId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "2") int size)
    {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return appointmentSlotService.getAppointmentSlotsByDoctorId(doctorId, page, size);
    }

}
