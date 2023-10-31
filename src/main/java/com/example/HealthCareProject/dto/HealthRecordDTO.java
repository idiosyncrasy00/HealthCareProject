package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.Patient;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HealthRecordDTO implements Serializable {
    private long id;
    private String bloodType;
    private double height;
    private String heightUnit;
    private double weight;
    private String weightUnit;
    private long patient;
}
