package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.common.Common;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDTO implements Serializable {
    private String diagnosis;
    private String prescriptionDescription;
    private String medicine;
    private AppointmentSlot appointmentSlot;

    @Builder
    public static class AddPrescriptionResponse extends Common {
//        private long id;
//        private long doctor_id;
    }

    @Getter
    @Setter
    @Builder
    public static class PrescriptionDetails {
        //private long id;
        private String diagnosis;
        private String prescriptionDescription;
        private String medicine;
    }

    @Getter
    @Setter
    @Builder
    public static class EditPrescription extends Common {
        private String diagnosis;
        private String prescriptionDescription;
        private String medicine;
    }

}
