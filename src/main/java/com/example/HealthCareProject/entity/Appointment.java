package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Appointment"
)
public class Appointment extends Common {
//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE) //todo
    @JoinColumn(name = "appointment_slot_id", unique = true)
    //@NotBlank
    private AppointmentSlot appointmentSlot;

//    @Column(name="status")
//    /**
//     * 0: not yet to the clinic
//     * 1: in the progress
//     * 2: the healthcheck is over. Doctor can prescribe to the patient
//     */
//    private int status = 0;
    @Column(name="message")
    private String message;

    @Version
    private Long version; // This is the version/timestamp field for optimistic locking

    public Appointment(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
        //this.status = status;
    }
}
