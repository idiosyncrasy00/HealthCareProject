package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.UserData;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private long patient_id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private UserDataDTO userDataDTO;
    private String dob;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddPatient {
        private String firstName;
        private String lastName;
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
    public static class EditPatient {
        private String firstName;
        private String lastName;
        private String address;
        private String gender;
        private String dob;
    }
}
