package com.example.HealthCareProject.config;

import com.example.HealthCareProject.dto.*;
import com.example.HealthCareProject.entity.*;
import com.example.HealthCareProject.repository.AppointmentSlotRepository;
import com.example.HealthCareProject.repository.PrescriptionRepository;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ConvertToDTOUtils {
    //private static final PrescriptionRepository prescriptionRepository = null;

//    public ConvertToDTOUtils(PrescriptionRepository prescriptionRepository) {
//        this.prescriptionRepository = prescriptionRepository;
//    }

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

    public static AppointmentDTO convertToAppointDetailsDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientInfo(ConvertToDTOUtils.convertToPatientDTO(appointment.getPatient()))
                .appointmentSlotId(appointment.getAppointmentSlot().getId())
                .message(appointment.getMessage())
                //.status(appointment.getStatus())
                .build();
    }

    public static AppointmentSlotDTO.AppointmentSlotDetails convertToAppointmentSlotDetailsDTO(AppointmentSlot appointmentSlot) {
        //doctor details
//        DoctorDTO.doctorDetails doctorDetails =  DoctorDTO.doctorDetails.builder()
//                .doctorId((Long) appointmentSlot[3])
//                .doctorName((String) appointmentSlot[4])
//                .address((String) appointmentSlot[5])
//                .gender((String) appointmentSlot[6])
//                .build();

        DoctorDTO.doctorDetails doctorDetails =  DoctorDTO.doctorDetails.builder()
                .doctorId(appointmentSlot.getDoctor().getId())
                .doctorName(appointmentSlot.getDoctor().getFullName())
                .address(appointmentSlot.getDoctor().getAddress())
                .gender(appointmentSlot.getDoctor().getGender())
                .doctorType(appointmentSlot.getDoctor().getDoctorType())
                .build();
        //add prescription
        PrescriptionDTO.PrescriptionDetails prescriptionDetails = null;
        if (appointmentSlot.getPrescription() != null) {
            prescriptionDetails = PrescriptionDTO.PrescriptionDetails.builder()
                    .diagnosis(appointmentSlot.getPrescription().getDiagnosis())
                    .prescriptionDescription(appointmentSlot.getPrescription().getPrescriptionDescription())
                    .medicine(appointmentSlot.getPrescription().getMedicine())
                    .build();
        }

        return AppointmentSlotDTO.AppointmentSlotDetails.builder()
               .id(appointmentSlot.getId())
                .appointment_date( appointmentSlot.getAppointment_date())
                .appointment_time(appointmentSlot.getAppointment_time())
                .doctorDetails(doctorDetails)
                .prescriptionDetails(prescriptionDetails)
                .build();
    }

    public static PagingDTO convertToPagingDTO(Page<?> paging) {
        return PagingDTO.builder()
                .currentPage(paging.getNumber())
                .totalPages(paging.getTotalPages())
                .totalRecords(paging.getTotalElements())
                .build();
    }

    public static AppointmentDTO convertToAppointDetailsDTOPatient(Appointment appointment, Page<Appointment> appointmentPage) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctorDTO(ConvertToDTOUtils.convertToDoctorDTO(appointment.getDoctor()))
                .appointmentSlotId(appointment.getAppointmentSlot().getId())
                .message(appointment.getMessage())
                //.status(appointment.getStatus())
//                .pagingDTO(PagingDTO.builder()
//                        .totalPages(appointmentPage.getTotalPages())
//                        .currentPage(appointmentPage.getNumber())
//                        .totalRecords(appointmentPage.getTotalElements())
//                        .build())
                .build();
    }
}
