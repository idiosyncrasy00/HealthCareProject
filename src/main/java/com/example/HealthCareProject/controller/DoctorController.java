package com.example.HealthCareProject.controller;


import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.PagingDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
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
    private HttpServletResponse res = new Response();
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<?> viewDoctorDetails(@RequestParam("doctorId") Long doctorId) {

        if (doctorService.viewDoctorDetails(doctorId) instanceof Boolean) {
            return ResponseEntity.status(StatusCode.NotFoundCode)
            .body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
            "doctor with id " + doctorId + " does not exist!"));
        }
        res.setStatus(StatusCode.SuccessCode);
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "success",doctorService.viewDoctorDetails(doctorId), res));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorDTO.AddDoctor doctor,
                                            @RequestParam("userId") Long userId) {
        return doctorService.addNewDoctor(doctor, userId);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> editDoctor(@RequestBody DoctorDTO.EditDoctor doctor,
                                        @RequestParam("doctorId") long doctorId, @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseEntity.status(StatusCode.BadRequestCode)
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"));
        }
        return doctorService.editDoctor(doctor, doctorId);
    }

    //long patientId, long doctorId, int status
    @GetMapping("/view/appointments")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> viewAppointmentsByDoctor(@RequestParam String patientFullName,
                                              @RequestParam("doctorId") long doctorId,
                                              @RequestParam(required = false) Collection<Integer> status,
                                                      @RequestParam long id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseEntity.status(StatusCode.BadRequestCode)
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                            "You cannot do this operation!"));
        }

        if (doctorService.getAppointments(patientFullName, doctorId, status, page, size).isEmpty()) {
            return ResponseEntity.status(StatusCode.NotFoundCode)
                    .body(
                            CommonMessageDTO.builder()
                                    .statusCode(StatusCode.NotFoundCode)
                                    .messageDetails("There are no available appointments!")
                                    .build()
                    );
        }
        List<AppointmentDTO> results = doctorService.getAppointments(patientFullName, doctorId, status, page, size);
        return ResponseEntity.status(StatusCode.SuccessCode)
                .body(
            CommonMessageDTO.builder()
                    .statusCode(StatusCode.SuccessCode)
                    .messageDetails("Success")
                    .result(results)
                    .build()
                );
    }
}
