package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Payload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserDataRepository userDataRepository;
    public DoctorService(DoctorRepository doctorRepository, UserDataRepository userDataRepository) {
        this.doctorRepository = doctorRepository;
        this.userDataRepository = userDataRepository;
    }

    //d.first_name, d.last_name, d.address, d.description, d.gender, d.doctor_type, u.email, u.phone_number
    public ResponseEntity<?> viewDoctorDetails(long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findByDoctorID(doctorId);
                //.orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        if (doctor.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode)
                    .body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"));
        }
//        Object getDoctorInfo = doctorRepository.getDoctorInformation(doctorId);
        DoctorDTO.ViewDoctorResponse response = DoctorDTO.ViewDoctorResponse.builder()
                .firstName(doctor.get().getFirstName())
                .lastName(doctor.get().getLastName())
                .address(doctor.get().getAddress())
                .description(doctor.get().getDescription())
                .gender(doctor.get().getGender())
                .doctorType(doctor.get().getDoctorType())
                .email(doctor.get().getUserData().getEmail())
                .phoneNumber(doctor.get().getUserData().getPhoneNumber())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response));
    }
    public ResponseEntity<?> addNewDoctor(DoctorDTO.AddDoctor addedDoctor, long userId) {
            //find user
            //find user id?
            Optional<UserData> userData = userDataRepository.findById(userId);
//                    .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist!"));
        if (!userData.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "user with id " + userId + " does not exist!"));
        }
        //build doctor
            Doctor doctor = Doctor.builder()
                    .firstName(addedDoctor.getFirstName())
                    .lastName(addedDoctor.getLastName())
                    .address(addedDoctor.getAddress())
                    .doctorType(addedDoctor.getDoctorType())
                    .gender(addedDoctor.getGender())
                    .userData(userData.get())
                    .description(addedDoctor.getDescription()).build();
            doctorRepository.save(doctor);
            DoctorDTO.AddDoctorResponse response = DoctorDTO.AddDoctorResponse.builder()
                    .firstName(doctor.getFirstName())
                    .lastName(doctor.getLastName())
                    .address(doctor.getAddress())
                    .doctorType(doctor.getDoctorType())
                    .gender(doctor.getGender())
                    .description(doctor.getDescription())
                    .userDataDTO(ConvertToDTOUtils.convertToUserDataDTO(userData.get()))
                    .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response));
    }

    @Transactional
    public ResponseEntity<?> editDoctor(DoctorDTO.EditDoctor doctor, long doctorId) {
        //find user id?
        Optional<Doctor> currentDoctor = doctorRepository.findByDoctorID(doctorId);
        if (currentDoctor.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"));
        }
                //.orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        currentDoctor.get().setFirstName(doctor.getFirstName());
        currentDoctor.get().setLastName(doctor.getLastName());
        currentDoctor.get().setAddress(doctor.getAddress());
        currentDoctor.get().setDoctorType(doctor.getDoctorType());
        currentDoctor.get().setGender(doctor.getGender());
        currentDoctor.get().setDescription(doctor.getDescription());
        currentDoctor.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));


        DoctorDTO.EditDoctorResponse response = DoctorDTO.EditDoctorResponse.builder()
                .firstName(currentDoctor.get().getFirstName())
                .lastName(currentDoctor.get().getLastName())
                .address(currentDoctor.get().getAddress())
                .doctorType(currentDoctor.get().getDoctorType())
                .gender(currentDoctor.get().getGender())
                .description(currentDoctor.get().getDescription())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response));
    }
}
