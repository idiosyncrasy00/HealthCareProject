package com.example.HealthCareProject.dto;
import com.example.HealthCareProject.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private long id;
    private String username;
    private String email;
    private Long phoneNumber;
    private Set<Role> roles = new HashSet<>();

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank
        @Size(min = 3, max = 20)
        private String username;
        @NotBlank
        @Size(min = 6, max = 40)
        private String password;
        @NotBlank
        @Size(max = 50)
        @Email
        private String email;
        @Size(max = 12)
        @Email
        private Long phoneNumber;
        @Column(nullable = false)
        private Set<String> role;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateUserData {
        private String username;
        private String email;
        private String password;
    }
}
