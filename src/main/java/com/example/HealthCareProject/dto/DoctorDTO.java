package com.example.HealthCareProject.dto;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO implements Serializable {
    private long doctor_id;
    private String fullName;
    private String address;
    private String doctorType;
    private String gender;
    private String description;
    private String email;
    private String phoneNumber;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewDoctorResponse implements Serializable {
        private String fullName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
        private String email;
        private String phoneNumber;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddDoctor {
        private String fullName;
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
        private String fullName;
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
        private String fullName;
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
    public static class EditDoctorResponse {
        private long doctorID;
        private String fullName;
        private String address;
        private String doctorType;
        private String gender;
        private String description;
        //private UserDataDTO userDataDTO;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class doctorDetails {
        private long doctorId;
        private String doctorName;
        private String address;
        private String doctorType;
        private String gender;
    }
}
