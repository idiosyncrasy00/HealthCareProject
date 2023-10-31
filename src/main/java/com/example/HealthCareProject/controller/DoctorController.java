package com.example.HealthCareProject.controller;


import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.PagingDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.service.DoctorService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public CustomeResponseEntity<?> viewDoctorDetails(@RequestParam("doctorId") Long doctorId) {
        return doctorService.viewDoctorDetails(doctorId);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> registerDoctor(@RequestBody DoctorDTO.AddDoctor doctor,
                                            @RequestParam("userId") Long userId) {
        return doctorService.addNewDoctor(doctor, userId);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> editDoctor(@RequestBody DoctorDTO.EditDoctor doctor,
                                        @RequestParam("doctorId") long doctorId, @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return doctorService.editDoctor(doctor, doctorId);
    }

    //long patientId, long doctorId, int status
    @GetMapping("/view/appointments")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> viewAppointmentsByDoctor(@RequestParam String patientFullName,
                                              @RequestParam("doctorId") long doctorId,
                                              @RequestParam(required = false) Collection<Integer> status,
                                                      @RequestParam long id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        //List<AppointmentDTO> results = doctorService.getAppointments(patientFullName, doctorId, status, page, size);
        return doctorService.getAppointments(patientFullName, doctorId, status, page, size);
    }
}
