package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.service.UserDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/userdata")
public class UserDataController {
    private final UserDataService userDataService;
    @Autowired
    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/view")
    @PreAuthorize("(hasRole('PATIENT') or hasRole('DOCTOR')) and #id == authentication.principal.id")
    public ResponseEntity<?> getUserDataByUserDataID(@RequestParam("id") long id) {
        return userDataService.getSingleUserDataByUserDataID(id);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("(hasRole('PATIENT') or hasRole('DOCTOR')) and #id == authentication.principal.id")
    public ResponseEntity<?> deleteUserData(@RequestParam("id") long id) {
        return userDataService.deleteUserData(id);
    }
    @PutMapping("/update")
    @PreAuthorize("(hasRole('PATIENT') or hasRole('DOCTOR')) and #id == authentication.principal.id")
    public ResponseEntity<?> updateUserData(@RequestBody UserDataDTO.updateUserData user,
                                                                     @RequestParam("id") long id) {
        return userDataService.updateUserData(user, id);
    }
}
