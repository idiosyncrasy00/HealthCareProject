package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.common.Common;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AppointmentDTO extends Common {
    private Long id;
    private PatientDTO patientInfo;
    private DoctorDTO doctorDTO;
    private long appointmentSlotId;
    private String message;
    //private int status;

    @Builder
    public static class makeAppointmentResponse {
        private long id;
        private long doctorId;
        private long patientId;
        private long appointmentSlotId;
        //private int status;
        private String message;
    }

    @Builder
    @Getter
    public static class AppointmentDetailsFromDoctor extends Common {
        private PatientDTO patientInfo;
        private long appointmentSlotId;
        private String message;
        //private int status;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppointmentDetailsFromPatient extends Common {
        private DoctorDTO doctorDTO;
        private long appointmentSlotId;
        private String message;
        //private int status;
    }

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
