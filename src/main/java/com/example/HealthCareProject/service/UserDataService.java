package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ModelMapperConfig;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.repository.UserDataRepository;
import com.example.HealthCareProject.entity.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDataService {
    @Autowired
    private ModelMapperConfig mapper;
    private final UserDataRepository userDataRepository;
    @Qualifier("passwordEncoder")
    @Autowired
    private PasswordEncoder encoder;
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> getUsersData() {
        return userDataRepository.findAll();
    }

    public Optional<UserData> getSingleUserDataByEmail(String email) {
        Optional<UserData> checkEmailExists = userDataRepository.findByEmail(email);
        if (!checkEmailExists.isPresent()) {
            throw new IllegalStateException("No user found!");
        }
        return checkEmailExists;
    }

    public Optional<UserData> getSingleUserDataByUserName(String username) {
        Optional<UserData> checkUserNameExists = userDataRepository.findByUserName(username);
        if (!checkUserNameExists.isPresent()) {
            throw new IllegalStateException("Username not found!");
        }
        return checkUserNameExists;
    }

    public Optional<UserData> getSingleUserDataByUserDataID(long userId) {
        Optional<UserData> checkUserDataIDExists = userDataRepository.findById(userId);
        if (!checkUserDataIDExists.isPresent()) {
            throw new IllegalStateException("No user found!");
        }
        return checkUserDataIDExists;
    }

    public String addNewUserData(UserData user) {
        //check if email exists?
        Optional<UserData> checkEmailExists = userDataRepository.findByEmail(user.getEmail());
        if (checkEmailExists.isPresent()) {
            throw new IllegalStateException("Email is already taken!");
        }
        userDataRepository.save(user);
        UserDataDTO userDataDTO = mapper.modelMapper().map(user, UserDataDTO.class);
        return "UserData added " + userDataDTO.toString();
    }
    public String deleteUserData(long id) {
        Optional<UserData> checkIdExists = userDataRepository.findById(id);
        if (!checkIdExists.isPresent()) {
            throw new IllegalStateException("User does not exist!");
        }
        System.out.println("UserData with id " + id + " deleted.");
        userDataRepository.deleteById(id);
        return "UserData with id " + id + " deleted.";
    }
    @Transactional
    public UserDataDTO.updateUserData updateUserData(UserDataDTO.updateUserData _updatedUserData, long userId) {
        UserData currentUserData = userDataRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!"));
        if (currentUserData.getEmail() != null && currentUserData.getUsername() != null) {
            if (currentUserData.getEmail() != null) {
                currentUserData.setEmail(_updatedUserData.getEmail());
            }
            if (currentUserData.getPassword() != null) {
                currentUserData.setPassword(encoder.encode(_updatedUserData.getPassword()));
            }
            if (currentUserData.getUsername() != null) {
                currentUserData.setUsername(_updatedUserData.getUsername());
            }
            return _updatedUserData;
        } else {
            throw new IllegalStateException("user with id " + userId + " does not exist!");
        }
    }

//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Transactional
//    public long saveUserData(UserData userData) {
//        userData.setPassword(bCryptPasswordEncoder()
//                .encode(userData.getPassword()));
//        return userData.getId();
//    }
}
