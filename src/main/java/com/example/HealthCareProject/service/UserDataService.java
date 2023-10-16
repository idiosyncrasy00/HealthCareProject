package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.config.ModelMapperConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.payload.MessageResponse;
import com.example.HealthCareProject.repository.UserDataRepository;
import com.example.HealthCareProject.entity.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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

//    public List<UserData> getUsersData() {
//        return userDataRepository.findAll();
//    }
//
//    public Optional<UserData> getSingleUserDataByEmail(String email) {
//        Optional<UserData> checkEmailExists = userDataRepository.findByEmail(email);
//        if (!checkEmailExists.isPresent()) {
//            throw new IllegalStateException("No user found!");
//        }
//        return checkEmailExists;
//    }
//
//    public Optional<UserData> getSingleUserDataByUserName(String username) {
//        Optional<UserData> checkUserNameExists = userDataRepository.findByUserName(username);
//        if (!checkUserNameExists.isPresent()) {
//            throw new IllegalStateException("Username not found!");
//        }
//        return checkUserNameExists;
//    }

    //Optional<UserData>
    public ResponseEntity<?> getSingleUserDataByUserDataID(long userId) {
        Optional<UserData> checkUserDataIDExists = userDataRepository.findById(userId);
        if (!checkUserDataIDExists.isPresent()) {
            //throw new IllegalStateException("No user found!");
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode, "User Not found!"));
            //return ResponseEntity.notFound().build(new CommonMessageDTO<>(StatusCode.SuccessCode, "Success", checkUserDataIDExists));
        }
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode, "Success", checkUserDataIDExists));
    }

    public ResponseEntity<?> addNewUserData(UserData user) {
        //check if email exists?
        Optional<UserData> checkEmailExists = userDataRepository.findByEmail(user.getEmail());
        Optional<UserData> checkUsernameExists = userDataRepository.findByUserName(user.getUsername());
        if (checkEmailExists.isPresent()) {
//            throw new IllegalStateException("Email is already taken!");
            return ResponseEntity.badRequest().body(new CommonMessageDTO<>(StatusCode.BadRequestCode,"Email is already taken!"));
        }
        if (checkUsernameExists.isPresent()) {
            return ResponseEntity.badRequest().body(new CommonMessageDTO<>(StatusCode.BadRequestCode,"Username is already taken!"));
        }
        userDataRepository.save(user);
        UserDataDTO userDataDTO = mapper.modelMapper().map(user, UserDataDTO.class);
        return ResponseEntity.ok(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "User registered successfully!", userDataDTO));
//        return "UserData added " + userDataDTO.toString();
    }
    public ResponseEntity<?> deleteUserData(long id) {
        Optional<UserData> checkIdExists = userDataRepository.findById(id);
        if (!checkIdExists.isPresent()) {
//            throw new IllegalStateException("User does not exist!");
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already taken!"));
        }
        System.out.println("UserData with id " + id + " deleted.");
        userDataRepository.deleteById(id);
        return ResponseEntity.ok(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "UserData with id " + id + " deleted.", id));
    }
    @Transactional
    public ResponseEntity<?> updateUserData(UserDataDTO.updateUserData _updatedUserData, long userId) {
        Optional<UserData> currentUserData = userDataRepository.findById(userId);
                //.orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!"));
        if (!currentUserData.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "User with id " + userId + " does not exist!"));
        }
        if (currentUserData.get().getEmail() != null && currentUserData.get().getUsername() != null) {
            if (currentUserData.get().getEmail() != null) {
                currentUserData.get().setEmail(_updatedUserData.getEmail());
            }
            if (currentUserData.get().getPassword() != null) {
                currentUserData.get().setPassword(encoder.encode(_updatedUserData.getPassword()));
            }
            if (currentUserData.get().getUsername() != null) {
                currentUserData.get().setUsername(_updatedUserData.getUsername());
            }
            currentUserData.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
            return ResponseEntity.ok(new CommonMessageDTO<>(StatusCode.SuccessCode,
                    "User with id " + userId + " updated.", _updatedUserData));
        } else {
            return ResponseEntity.status(StatusCode.BadRequestCode).body(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "Some fields are missing."));
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
