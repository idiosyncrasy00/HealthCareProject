package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
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

//    public ResponseEntity<?> getAppointmentsByStatus(long doctorID, long patientID, int status) {
//        Optional<Appointment> appointmentList = appointmentRepository.
//    }

    @Transactional
    public CustomeResponseEntity<?> makeAppointment(Appointment appointmentBody, long id) {
        long patientId = appointmentBody.getPatient().getId();
        long doctorId = appointmentBody.getDoctor().getId();
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (!patient.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                            "patient with id " + patientId + " does not exist!"), HttpStatus.NOT_FOUND);
        }
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"), HttpStatus.NOT_FOUND);
        }

        if (patientRepository.checkUserIdIsPatientId(patientId, id) <= 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
                        response), HttpStatus.OK);
    }

    //ResponseEntity<?>
    @Transactional
    public CustomeResponseEntity<?> deleteAppointment(long appointmentID) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
                //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        if (!appointment.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Appoint with id " + appointmentID + " does not exist!"
            ), HttpStatus.NOT_FOUND);
        }
        appointmentRepository.deleteById(appointmentID);
        AppointmentDTO.AppointmentDeletedResponse response = AppointmentDTO.AppointmentDeletedResponse.builder()
                .id(appointmentID)
                .build();
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "appointment cancelled successfully"
        ), HttpStatus.OK);
    }

    @Transactional
    public CustomeResponseEntity<?> acceptAppointment(long appointmentID, long doctorId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
        if (!appointment.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Appoint with id " + appointmentID + " does not exist!"
            ), HttpStatus.NOT_FOUND);
        }
        appointment.get().setStatus(1);
        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        String patientName = appointment.get().getPatient().getFullName();

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "appointment accepted for patient " + patientName
        ), HttpStatus.OK);
    }

    @Transactional
    public CustomeResponseEntity<?> rejectAppointment(long appointmentID) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentID);
        //.orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        if (!appointment.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Appoint with id " + appointmentID + " does not exist!"
            ), HttpStatus.NOT_FOUND);
        }
        appointment.get().setStatus(2);
        appointment.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        String patientName = appointment.get().getPatient().getFullName();
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "appointment rejected for patient " + patientName
        ), HttpStatus.OK);
    }

}

