package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.security.JwtUtils;
import com.example.HealthCareProject.security.UserDetailsImpl;
import com.example.HealthCareProject.payload.JwtResponse;
import com.example.HealthCareProject.payload.MessageResponse;

import com.example.HealthCareProject.entity.Role;
import com.example.HealthCareProject.repository.RoleRepository;
import com.example.HealthCareProject.consts.ERole;

import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.UserDataRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    RoleRepository roleRepository;

    @Qualifier("passwordEncoder")
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    private HttpServletResponse res = new Response();

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDataDTO.LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("User Details " + userDetails.toString());
        System.out.println("User authorities " + userDetails.getAuthorities());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println("ROles " + roles.toString());
        return ResponseEntity.ok(new CommonMessageDTO<JwtResponse>(StatusCode.SuccessCode, "Login successfully",
                    new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles)));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDataDTO.RegisterRequest signUpRequest) {
        System.out.println("username is " + signUpRequest.getUsername());
        if (userDataRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode
                            , "Error: Username is already taken!"));
        }

        if (userDataRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new CommonMessageDTO<>(StatusCode.BadRequestCode
                            , "Error: Email is already in use!"));
        }

        // Create new user's account
        UserData userData = new UserData(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhoneNumber()
                );

        Set<String> strRoles = signUpRequest.getRole();

        System.out.println("Set strRoles " + strRoles);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "doctor":
                        Role modRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role patientRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(patientRole);
                }
            });
        }
        userData.setRoles(roles);
        System.out.println(userData.toString());
        userDataRepository.save(userData);
        return ResponseEntity.ok(new CommonMessageDTO(StatusCode.SuccessCode, "User registered successfully!"));
    }
}
