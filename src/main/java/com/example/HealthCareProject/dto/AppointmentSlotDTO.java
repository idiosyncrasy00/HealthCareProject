package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.common.Common;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AppointmentSlotDTO extends Common implements Serializable {
    private LocalDate appointment_date;
    private LocalTime appointment_time;
    private Doctor doctor;

    @Builder
    @Getter
    @Setter
    public static class AppointmentSlotDetails {
        private long id;
        private LocalDate appointment_date;
        private LocalTime appointment_time;
        private long doctorId;
        private String doctorName;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddSlotRequest {
        private LocalDate appointment_date;
        private LocalTime appointment_time;
        private long doctor_id;
    }

    @Builder
//    @Getter
//    @Setter
    public static class AddSlotResponse extends Common {
        private long id;
        private LocalDate appointment_date;
        private LocalTime appointment_time;
        private long doctor_id;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditSlot {
        private LocalDate appointment_date;
        private LocalTime appointment_time;
        private long doctor_id;
    }

}
