package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.UserData;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO implements Serializable {
    private long patient_id;
    private String fullName;
    private String address;
    private String gender;
    private String dob;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddPatient implements Serializable {
        private String fullName;
        private String address;
        private String gender;
        private UserData userData;
        private String dob;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddPatientResponse implements Serializable {
        private String fullName;
        private String address;
        private String gender;
        private UserDataDTO userDataDTO;
        private String dob;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditPatient implements Serializable {
        private String fullName;
        private String address;
        private String gender;
        private String dob;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditPatientResponse implements Serializable {
        private String fullName;
        private String address;
        private String gender;
        private String dob;
    }
}
