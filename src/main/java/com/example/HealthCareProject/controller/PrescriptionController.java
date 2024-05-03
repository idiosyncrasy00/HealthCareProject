package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PrescriptionDTO;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Prescription;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.AppointmentSlotRepository;
import com.example.HealthCareProject.repository.PrescriptionRepository;
import com.example.HealthCareProject.service.*;
import com.example.HealthCareProject.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/prescription")
public class PrescriptionController {
    private PrescriptionService prescriptionService;

    private DoctorService doctorService;
    private AppointmentSlotRepository appointmentSlotRepository;
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, DoctorService doctorService,
                                  AppointmentSlotRepository appointmentSlotRepository,
                                  PrescriptionRepository prescriptionRepository
                                  ) {
        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;
        this.appointmentSlotRepository = appointmentSlotRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> addNewPrescription(@RequestBody PrescriptionDTO prescriptionDTO,
                                                @RequestParam long id,
                                                @RequestParam long doctorId
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        long appointmentSlotId = prescriptionDTO.getAppointmentSlot().getId();
        Optional<AppointmentSlot> appointmentSlot = appointmentSlotRepository.findById(appointmentSlotId);
        if (!appointmentSlot.isPresent()) {
            return ResponseHandler.generateResponse("appointment with id " + appointmentSlotId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        int getStatus = appointmentSlot.get().getStatus();
        //the healthcheck is not over
        if (getStatus != 2) {
            return ResponseHandler.generateResponse("The healthcheck is not over!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        try {
            Prescription prescription = prescriptionService.addNewPrescription(prescriptionDTO);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    prescription
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> editPrescription(
            @RequestBody PrescriptionDTO.EditPrescription prescriptionDTO,
            @RequestParam long id,
            @RequestParam long doctorId)
    {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }


        long prescriptionId = prescriptionDTO.getId();
        Optional<Prescription> currentPrescription = prescriptionRepository.findById(prescriptionId);

        if (!currentPrescription.isPresent()) {
            return ResponseHandler.generateResponse("Prescription with id " + prescriptionId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            Prescription updatedPrescription = prescriptionService.editPrescription(prescriptionDTO);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    updatedPrescription
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

        //return prescriptionService.editPrescription(prescriptionDTO);
    }

}
