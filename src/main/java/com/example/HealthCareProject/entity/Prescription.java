package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Prescription"
)
public class Prescription extends Common {
    @Column(name ="diagnosis")
    private String diagnosis;

    @Column(name = "prescription_description")
    private String prescriptionDescription;

    @Column(name = "medicine")
    private String medicine;

    @OneToOne
    @JoinColumn(name = "appointment_slot_id")
    private AppointmentSlot appointmentSlot;

}
