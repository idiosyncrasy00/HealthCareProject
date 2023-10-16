package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Patient patient;
    private Doctor doctor;
    private Date appointmentTime;
    private String message;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppointmentDeletedResponse {
        long id;
        String message = "Cancel appointment successfully!";
    }
}
