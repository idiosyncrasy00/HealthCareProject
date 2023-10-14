package com.example.HealthCareProject.service;

import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AppointmentService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    public AppointmentService(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment makeAppointment(Appointment appointmentBody) {
        long patientId = appointmentBody.getPatient_id().getId();
        long doctorId = appointmentBody.getDoctor_id().getId();
        patientRepository.findById(patientId).orElseThrow(() -> new IllegalStateException("patient with id " + patientId + " does not exist!"));
        doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalStateException("doctor with id " + doctorId + " does not exist!")
        );
        appointmentRepository.save(appointmentBody);
        return appointmentBody;
    }

    @Transactional
    public AppointmentDTO.AppointmentDeletedResponse deleteAppointment(long appointmentID) {
        appointmentRepository.findById(appointmentID).orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        appointmentRepository.deleteById(appointmentID);
        AppointmentDTO.AppointmentDeletedResponse response = AppointmentDTO.AppointmentDeletedResponse.builder()
                .id(appointmentID)
                .build();
        return response;
    }

    @Transactional
    public Appointment acceptAppointment(long appointmentID) {
        Appointment appointment = appointmentRepository.findById(appointmentID).orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        appointment.setStatus(1);
        return appointment;

    }

    @Transactional
    public Appointment rejectAppointment(long appointmentID) {
        Appointment appointment = appointmentRepository.findById(appointmentID).orElseThrow(() -> new IllegalStateException("Appoint with id " + appointmentID + " does not exist!"));
        appointment.setStatus(2);
        return appointment;

    }

}

