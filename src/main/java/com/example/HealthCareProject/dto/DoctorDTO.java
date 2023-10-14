package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

public class DoctorDTO extends Doctor {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewDoctorResponse {
        private String firstName;
        private String lastName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
        private String email;
        private Long phoneNumber;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddDoctor {
        private String firstName;
        private String lastName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
        private UserDataDTO userDataDTO;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddDoctorResponse {
        private String firstName;
        private String lastName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
        private UserDataDTO userDataDTO;
    }



    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditDoctor {
        private String firstName;
        private String lastName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditDoctorResponse {
        private long doctorID;
        private String firstName;
        private String lastName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
    }
}
