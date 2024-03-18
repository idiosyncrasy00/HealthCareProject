package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PrescriptionDTO;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/prescription")
public class PrescriptionController {
    private PrescriptionService prescriptionService;

    private DoctorService doctorService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, DoctorService doctorService) {
        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> addNewPrescription(@RequestBody PrescriptionDTO prescriptionDTO,
                                                        @RequestParam long id,
                                                        @RequestParam long doctorId
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return prescriptionService.addNewPrescription(prescriptionDTO);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> editPrescription(
            @RequestBody PrescriptionDTO.EditPrescription prescriptionDTO,
            @RequestParam long id,
            @RequestParam long doctorId)
    {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return prescriptionService.editPrescription(prescriptionDTO);
    }

}
