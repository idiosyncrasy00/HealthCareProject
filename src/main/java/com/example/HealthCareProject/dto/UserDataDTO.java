package com.example.HealthCareProject.dto;
import com.example.HealthCareProject.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO implements Serializable {
    private long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Set<Role> roles = new HashSet<>();

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "Username is not allowed to be empty!")
        @Size(min = 5, max = 32, message = "Username between 5-32 characters long")
        private String username;
        @NotBlank(message = "Password is not allowed to be empty!")
        @Size(min = 8, max = 32, message = "Password between 8-32 characters long")
        private String password;
        @NotBlank(message = "Email is not allowed to be empty!")
        @Email(message = "Invalid email format!")
        private String email;
        @NotBlank(message = "Phone number is not allowed to be empty!")
        @Size(min = 10, max = 12, message = "Phone number between 10-12 digits long")
        private String phoneNumber;
        @Column(nullable = false)
        private Set<String> role;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Username is not allowed to be empty!")
        private String username;
        @NotBlank(message = "Password is not allowed to be empty!")
        @Size(min = 8, max = 32, message = "Password between 8-32 characters long")
        private String password;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateUserData {
        @NotBlank(message = "Username is not allowed to be empty!")
        private String username;
        @NotBlank(message = "Email is not allowed to be empty!")
        @Email(message = "Invalid email format!")
        private String email;
        @NotBlank(message = "Password is not allowed to be empty!")
        @Size(min = 8, max = 32, message = "Password between 8-32 characters long")
        private String password;
    }
}
