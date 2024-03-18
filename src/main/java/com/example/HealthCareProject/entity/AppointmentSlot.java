package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "AppointmentSlot"
)
public class AppointmentSlot extends Common implements Serializable {

//    @Temporal(TemporalType.TIMESTAMP)
//    @NotBlank
    private LocalDate appointment_date; // Using timestamp

//    @Column(columnDefinition = "TIME")
//    @NotBlank
    private LocalTime appointment_time;

    @Column(name="status")
    /**
     * 0: not yet to the clinic
     * 1: in the progress
     * 2: the healthcheck is over. Doctor can prescribe to the patient
     */
    private int status = 0;

    @OneToOne(mappedBy = "appointmentSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


}
