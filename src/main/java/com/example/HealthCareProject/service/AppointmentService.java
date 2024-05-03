package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.*;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.catalina.connector.Response;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Optional;

@Service
@EnableCaching
public class AppointmentService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
//    private HttpServletResponse res;


    public AppointmentService(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public AppointmentDTO.makeAppointmentResponse makeAppointment(
            //Appointment appointmentBody
            AppointmentDTO.makeAppointmentRequest makeAppointmentRequest,
            long id) {
        Patient patient = new Patient();
        patient.setId(makeAppointmentRequest.getPatientID());
        Doctor doctor = new Doctor();
        doctor.setId(makeAppointmentRequest.getDoctorID());
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(makeAppointmentRequest.getAppointmentSlotId());
        //AppointmentDTO.makeAppointmentRequest
        Appointment appointmentBody = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentSlot(appointmentSlot)
                .message(makeAppointmentRequest.getMessage())
                .build();
        appointmentRepository.save(
                appointmentBody
        );
        AppointmentDTO.makeAppointmentResponse response = AppointmentDTO.makeAppointmentResponse.builder()
                .id(appointmentBody.getId())
                .patientId(appointmentBody.getPatient().getId())
                .doctorId(appointmentBody.getDoctor().getId())
                .appointmentSlotId(appointmentBody.getAppointmentSlot().getId())
                .message(appointmentBody.getMessage())
                .build();
        return response;
    }

    //ResponseEntity<?>
    @Transactional
    public AppointmentDTO.AppointmentDeletedResponse deleteAppointment(long appointmentID) {
        //Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
                //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
//        if (!appointment.isPresent()) {
////            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
////                    "Appoint with id " + appointmentID + " does not exist!"
////            ), HttpStatus.NOT_FOUND);
//            return null;
//        }
        appointmentRepository.deleteById(appointmentID);
        AppointmentDTO.AppointmentDeletedResponse response = AppointmentDTO.AppointmentDeletedResponse.builder()
                .id(appointmentID)
                .build();
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "appointment cancelled successfully"
//        ), HttpStatus.OK);
        return response;
    }

//    @Transactional
//    public CustomeResponseEntity<?> acceptAppointment(long appointmentID, long doctorId) {
//        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
//        if (!appointment.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                    "Appoint with id " + appointmentID + " does not exist!"
//            ), HttpStatus.NOT_FOUND);
//        }
//        appointment.get().setStatus(1);
//        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
//        String patientName = appointment.get().getPatient().getFullName();
//
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "appointment accepted for patient " + patientName
//        ), HttpStatus.OK);
//    }
//
//    @Transactional
//    public CustomeResponseEntity<?> rejectAppointment(long appointmentID) {
//        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
//        //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
//        if (!appointment.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                    "Appoint with id " + appointmentID + " does not exist!"
//            ), HttpStatus.NOT_FOUND);
//        }
//        appointment.get().setStatus(2);
//        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
//        String patientName = appointment.get().getPatient().getFullName();
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "appointment rejected for patient " + patientName
//        ), HttpStatus.OK);
//    }

}

