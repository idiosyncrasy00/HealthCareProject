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
    Patient patient_id;
    Doctor doctor_id;
    Date appointmentTime;
    String message;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppointmentDeletedResponse {
        long id;
        String message = "Cancel appoint successfully!";
    }
}
