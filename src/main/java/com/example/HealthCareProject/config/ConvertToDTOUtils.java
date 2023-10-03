package com.example.HealthCareProject.config;

import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.entity.UserData;

public class ConvertToDTOUtils {
    public static UserDataDTO convertToDTO(UserData userData) {
        return UserDataDTO.builder()
                .id(userData.getId())
                .username(userData.getUsername())
                .email(userData.getEmail())
                .phoneNumber(userData.getPhoneNumber())
                .roles(userData.getRoles())
                .build();
    }
}
