package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
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

//    public ResponseEntity<?> getAppointmentsByStatus(long doctorID, long patientID, int status) {
//        Optional<Appointment> appointmentList = appointmentRepository.
//    }

//    @Cacheable("appointments_patient")
    public CommonMessageDTO makeAppointment(Appointment appointmentBody, long id, HttpServletResponse res) throws ServletException, IOException {
        long patientId = appointmentBody.getPatient().getId();
        long doctorId = appointmentBody.getDoctor().getId();
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (!patient.isPresent()) {
//            return ResponseEntity.status(StatusCode.NotFoundCode)
//                    .body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                            "patient with id " + patientId + " does not exist!"));
            res.setStatus(StatusCode.NotFoundCode);
            return new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "patient with id " + patientId + " does not exist!", null, res);
        }
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
//            return ResponseEntity.status(StatusCode.NotFoundCode)
//                    .body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
//                            "doctor with id " + doctorId + " does not exist!"));
            res.setStatus(StatusCode.NotFoundCode);
            return new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!", null, res);
        }

        if (patientRepository.checkUserIdIsPatientId(patientId, id) <= 0) {
//            return ResponseEntity.status(StatusCode.BadRequestCode)
//                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
//                        "You cannot do this operation!"));
            res.setStatus(StatusCode.BadRequestCode);
            return new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!", null, res);
        }

        appointmentRepository.save(
                appointmentBody
        );
        AppointmentDTO.makeAppointmentResponse response = AppointmentDTO.makeAppointmentResponse.builder()
                .id(appointmentBody.getId())
                .patientId(appointmentBody.getPatient().getId())
                .doctorId(appointmentBody.getDoctor().getId())
                .appointmentTime(appointmentBody.getAppointmentTime())
                .status(appointmentBody.getStatus())
                .message(appointmentBody.getMessage())
                .build();
//        return ResponseEntity.status(StatusCode.SuccessCode).body(
//                new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
//                        response));
        res.setStatus(StatusCode.SuccessCode);
        return new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
                response, res);
    }

    //ResponseEntity<?>
    @Transactional
    public ResponseEntity<?> deleteAppointment(long appointmentID, HttpServletResponse res) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
                //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        if (!appointment.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(
                    new CommonMessageDTO<>(StatusCode.NotFoundCode,
                            "Appoint with id " + appointmentID + " does not exist!"
                    ));
        }
        appointmentRepository.deleteById(appointmentID);
        AppointmentDTO.AppointmentDeletedResponse response = AppointmentDTO.AppointmentDeletedResponse.builder()
                .id(appointmentID)
                .build();
        res.setStatus(StatusCode.SuccessCode);
        return ResponseEntity.status(StatusCode.SuccessCode).body(
                new CommonMessageDTO<>(StatusCode.SuccessCode, "appointment cancelled successfully", response, res
        ));
//        return response;
    }

    @Transactional
    public ResponseEntity<?> acceptAppointment(long appointmentID, long doctorId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
        //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        if (!appointment.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(
            new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Appoint with id " + appointmentID + " does not exist!"
            ));
        }
        appointment.get().setStatus(1);
        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        String patientName = appointment.get().getPatient().getFullName();
        return ResponseEntity.status(StatusCode.SuccessCode).body(
                new CommonMessageDTO<>(StatusCode.SuccessCode, "appointment accepted for patient " + patientName));
    }

    @Transactional
    public ResponseEntity<?> rejectAppointment(long appointmentID) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
        //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        if (!appointment.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(
                    new CommonMessageDTO<>(StatusCode.NotFoundCode,
                            "Appoint with id " + appointmentID + " does not exist!"
                    ));
        }
        appointment.get().setStatus(2);
        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        String patientName = appointment.get().getPatient().getFullName();
        return ResponseEntity.status(StatusCode.SuccessCode).body(
                new CommonMessageDTO<>(StatusCode.SuccessCode, "appointment rejected for patient " + patientName));
    }

}

