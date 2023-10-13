package com.example.HealthCareProject.entity;

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
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String bloodType;
    private double height;
    private double weight;

    @OneToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id")
    private Patient patient_id;
}
