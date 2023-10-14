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

    @GetMapping("/view/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserData>> getUsersData() {
        return new ResponseEntity<List<UserData>>(userDataService.getUsersData(), HttpStatus.OK);
    }

    @GetMapping("/view/email")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Optional<UserData>> getUserDataByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<Optional<UserData>>(userDataService.getSingleUserDataByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/view/id/")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Optional<UserData>> getUserDataByUserDataID(@RequestParam("userId") long userId) {
        return new ResponseEntity<Optional<UserData>>(userDataService.getSingleUserDataByUserDataID(userId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> registerUserData(@RequestBody UserData user) {
        return new ResponseEntity<>(userDataService.addNewUserData(user), HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserData(@RequestParam("userId") long userId) {
        return new ResponseEntity<String>(userDataService.deleteUserData(userId), HttpStatus.OK);
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<UserDataDTO.updateUserData> updateUserData(@RequestBody UserDataDTO.updateUserData user,
                                                                     @RequestParam("userId") long userId) throws JsonProcessingException {
        return new ResponseEntity<UserDataDTO.updateUserData>(userDataService.updateUserData(user, userId), HttpStatus.OK);
    }
}
