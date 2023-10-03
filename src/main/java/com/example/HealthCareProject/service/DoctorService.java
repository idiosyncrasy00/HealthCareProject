package com.example.HealthCareProject.service;

import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public DoctorDTO.AddDoctor addNewDoctor(DoctorDTO.AddDoctor addedDoctor, long userId) {
            //find user
            //find user id?
            UserData userData = userDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist!"));;
        addedDoctor.setUserData(userData);
        addedDoctor.getUserData().setPassword("");
                    //build doctor
            Doctor doctor = Doctor.builder()
                    .firstName(addedDoctor.getFirstName())
                    .lastName(addedDoctor.getLastName())
                    .address(addedDoctor.getAddress())
                    //.phoneNumber(addedDoctor.getPhoneNumber())
                    .doctorType(addedDoctor.getDoctorType())
                    .gender(addedDoctor.getGender())
                    .userData(userData) //adding userid
                    .description(addedDoctor.getDescription()).build();
            doctorRepository.save(doctor);
            return addedDoctor;
    }

    @Transactional
    public DoctorDTO.EditDoctor editDoctor(DoctorDTO.EditDoctor doctor, long doctorId) {
        //find user id?
        Doctor currentDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        currentDoctor.setFirstName(doctor.getFirstName());
        currentDoctor.setLastName(doctor.getLastName());
        currentDoctor.setAddress(doctor.getAddress());
        currentDoctor.setDoctorType(doctor.getDoctorType());
        currentDoctor.setGender(doctor.getGender());
        currentDoctor.setDescription(doctor.getDescription());
        return doctor;
    }
}
