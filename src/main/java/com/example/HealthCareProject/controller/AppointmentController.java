package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.service.AppointmentService;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;
import com.example.HealthCareProject.utils.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/appointment")
@Tag(name = "Appointment", description = "Appointment management APIs")
public class AppointmentController {
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    private AppointmentRepository appointmentRepository;

//    @Autowired
//    public AppointmentController(AppointmentService appointmentService) {
//        this.appointmentService = appointmentService;
//    }

    @Autowired
    public AppointmentController(AppointmentService appointmentService,AppointmentRepository appointmentRepository,
                                 PatientRepository patientRepository,
                                 DoctorService doctorService,
                                    PatientService patientService,
                                 DoctorRepository doctorRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Operation(summary = "Make an appointment")
    @PostMapping("/make")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> makeAppointment(@RequestBody AppointmentDTO.makeAppointmentRequest makeAppointmentRequest,
                                                  @RequestParam long id
    )
    {
        long patientId = makeAppointmentRequest.getPatientID();
        long doctorId = makeAppointmentRequest.getDoctorID();
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (!patient.isPresent()) {
            return ResponseHandler.generateResponse("patient with id \" + patientId + \" does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
                    );
        }
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return ResponseHandler.generateResponse("doctor with id " + doctorId + " does not exist!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        if (patientRepository.checkUserIdIsPatientId(patientId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        try {
            AppointmentDTO.makeAppointmentResponse makeAppointmentResponse = appointmentService.makeAppointment(makeAppointmentRequest, id);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    makeAppointmentResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

//    @PostMapping("/accept")
//    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
//    public CustomeResponseEntity<?> acceptAppointment(@RequestParam long appointmentId,
//                                               @RequestParam long doctorId,
//                                               @RequestParam long id
//    ) {
//        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
//                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
//        }
//        return appointmentService.acceptAppointment(appointmentId, doctorId);
//    }
//
//    @PostMapping("/reject")
//    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
//    public CustomeResponseEntity<?> rejectAppointment(@RequestParam long appointmentId, @RequestParam long doctorId,
//                                               @RequestParam long id) {
//        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
//            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.BadRequestCode,
//                            "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
//        }
//        return appointmentService.rejectAppointment(appointmentId);
//    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> deleteAppointment(@RequestParam long appointmentId, @RequestParam long patientId,
                                               @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

//        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
//        if (!appointment.isPresent()) {
//            return ResponseHandler.generateResponse("Appoint with id " + appointmentId + " does not exist!",
//                    HttpStatus.NOT_FOUND,
//                    null
//            );
//        }
        try {
            AppointmentDTO.AppointmentDeletedResponse appointmentDeletedResponse = appointmentService.deleteAppointment(appointmentId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    appointmentDeletedResponse
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
