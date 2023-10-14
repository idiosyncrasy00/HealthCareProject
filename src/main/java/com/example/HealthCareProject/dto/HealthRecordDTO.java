package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.HealthRecord;
import com.example.HealthCareProject.entity.Patient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HealthRecordDTO {
    private long id;
    private String bloodType;
    private double height;
    private String heightUnit;
    private double weight;
    private String weightUnit;
    private long patient_id;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class viewHealthRecordDTO {
        private long id;
        private String bloodType;
        private double height;
        private String heightUnit;
        private double weight;
        private String weightUnit;
        private long patient_id;
    }
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditHealthRecordDTO {
        private long id;
        private String bloodType;
        private double height;
        private String heightUnit;
        private double weight;
        private String weightUnit;
    }
}
