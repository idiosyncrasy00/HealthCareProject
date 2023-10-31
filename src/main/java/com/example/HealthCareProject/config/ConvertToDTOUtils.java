package com.example.HealthCareProject.config;

import com.example.HealthCareProject.dto.*;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import org.springframework.data.domain.Page;

public class ConvertToDTOUtils {
    public static UserDataDTO convertToUserDataDTO(UserData userData) {
        return UserDataDTO.builder()
                .id(userData.getId())
                .username(userData.getUsername())
                .email(userData.getEmail())
                .phoneNumber(userData.getPhoneNumber())
                .roles(userData.getRoles())
                .build();
    }

    public static PatientDTO convertToPatientDTO(Patient patient) {
        return PatientDTO.builder()
                .patient_id(patient.getId())
                .fullName(patient.getFullName())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .dob(patient.getDob())
                .build();
    }

    public static DoctorDTO convertToDoctorDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .doctor_id(doctor.getId())
                .fullName(doctor.getFullName())
                .address(doctor.getAddress())
                .doctorType(doctor.getDoctorType())
                .gender(doctor.getGender())
                .description(doctor.getDescription())
                .email(doctor.getUserData().getEmail())
                .phoneNumber(doctor.getUserData().getPhoneNumber())
                .build();
    }

    public static AppointmentDTO convertToAppointDetailsDTO(Appointment appointment, Page<Appointment> appointmentPage) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientInfo(ConvertToDTOUtils.convertToPatientDTO(appointment.getPatient()))
                .appointmentTime(appointment.getAppointmentTime())
                .message(appointment.getMessage())
                .status(appointment.getStatus())
                .pagingDTO(PagingDTO.builder()
                        .totalPages(appointmentPage.getTotalPages())
                        .currentPage(appointmentPage.getNumber())
                        .totalRecords(appointmentPage.getTotalElements())
                        .build())
                .build();
    }

    public static AppointmentDTO convertToAppointDetailsDTOPatient(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctorDTO(ConvertToDTOUtils.convertToDoctorDTO(appointment.getDoctor()))
                .appointmentTime(appointment.getAppointmentTime())
                .message(appointment.getMessage())
                .status(appointment.getStatus())
                .build();
    }
}
