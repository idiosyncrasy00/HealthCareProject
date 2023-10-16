package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "HealthRecord"
)
public class HealthRecord extends Common {
    private String bloodType;
    private double height;
    private String heightUnit;
    private double weight;
    private String weightUnit;

    @OneToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
