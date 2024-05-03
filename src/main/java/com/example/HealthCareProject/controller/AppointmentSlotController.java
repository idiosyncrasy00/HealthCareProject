package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.AppointmentSlotRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.service.AppointmentService;
import com.example.HealthCareProject.service.AppointmentSlotService;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;

import com.example.HealthCareProject.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/appointmentslot")
public class AppointmentSlotController {
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentSlotService appointmentSlotService;

    private DoctorRepository doctorRepository;

    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    public AppointmentSlotController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService,
                                     AppointmentSlotService appointmentSlotService,
                                     DoctorRepository doctorRepository,
                                     AppointmentSlotRepository appointmentSlotRepository
    ) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentSlotService = appointmentSlotService;
        this.doctorRepository = doctorRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> makeAppointmentSlot(@RequestBody AppointmentSlotDTO appointmentSlotDTO,
                                                 @RequestParam long id,
                                                 @RequestParam long doctorId
    ) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseHandler.generateResponse( "You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                null
            );
        }
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return ResponseHandler.generateResponse( "doctor with id " + doctorId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            AppointmentSlotDTO.AddSlotResponse addSlotResponse = appointmentSlotService.makeAppointmentSlot(appointmentSlotDTO);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    addSlotResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> getAppointmentSlotsByDoctorId(@RequestParam long id,
                                                                  @RequestParam long doctorId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "2") int size)
    {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return ResponseHandler.generateResponse("doctor with id " + doctorId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        long totalRecords = appointmentSlotRepository.count();
        try {
            List<AppointmentSlotDTO.AppointmentSlotDetails> appointmentSlotList = appointmentSlotService.getAppointmentSlotsByDoctorId(doctorId, page, size);
            return ResponseHandler.generateResponseWithPaging("success",
                    HttpStatus.OK,
                    page,
                    size,
                    (int) Math.floor(totalRecords / size),
                    (int) totalRecords,
                    appointmentSlotList
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
