package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Payload;
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
    public DoctorDTO.ViewDoctorResponse viewDoctorDetails(long doctorId) {
        doctorRepository.findByDoctorID(doctorId)
                .orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        Object getDoctorInfo = doctorRepository.getDoctorInformation(doctorId);
        DoctorDTO.ViewDoctorResponse response = DoctorDTO.ViewDoctorResponse.builder()
//                .firstName(getDoctorInfo[0].toString())
//                .lastName(getDoctorInfo[1].toString())
//                .address(getDoctorInfo[2].toString())
//                .description(getDoctorInfo[3].toString())
//                .gender(getDoctorInfo[4].toString())
//                .doctorType(getDoctorInfo[5].toString())
//                .email(getDoctorInfo[6].toString())
//                .phoneNumber(Long.parseLong(getDoctorInfo[7].toString()))
                .build();
        System.out.println("Doctor info " + getDoctorInfo);
        return response;

    }
    public DoctorDTO.AddDoctorResponse addNewDoctor(DoctorDTO.AddDoctor addedDoctor, long userId) {
            //find user
            //find user id?
            UserData userData = userDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist!"));;
                    //build doctor
            Doctor doctor = Doctor.builder()
                    .firstName(addedDoctor.getFirstName())
                    .lastName(addedDoctor.getLastName())
                    .address(addedDoctor.getAddress())
                    .doctorType(addedDoctor.getDoctorType())
                    .gender(addedDoctor.getGender())
                    .userData(userData)
                    .description(addedDoctor.getDescription()).build();
            doctorRepository.save(doctor);
            DoctorDTO.AddDoctorResponse response = DoctorDTO.AddDoctorResponse.builder()
                    .firstName(doctor.getFirstName())
                    .lastName(doctor.getLastName())
                    .address(doctor.getAddress())
                    .doctorType(doctor.getDoctorType())
                    .gender(doctor.getGender())
                    .description(doctor.getDescription())
                    .userDataDTO(ConvertToDTOUtils.convertToUserDataDTO(userData))
                    .build();
            return response;
    }

    @Transactional
    public DoctorDTO.EditDoctorResponse editDoctor(DoctorDTO.EditDoctor doctor, long doctorId) {
        //find user id?
        Doctor currentDoctor = doctorRepository.findByDoctorID(doctorId)
                .orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        currentDoctor.setFirstName(doctor.getFirstName());
        currentDoctor.setLastName(doctor.getLastName());
        currentDoctor.setAddress(doctor.getAddress());
        currentDoctor.setDoctorType(doctor.getDoctorType());
        currentDoctor.setGender(doctor.getGender());
        currentDoctor.setDescription(doctor.getDescription());


        DoctorDTO.EditDoctorResponse response = DoctorDTO.EditDoctorResponse.builder()
                .firstName(currentDoctor.getFirstName())
                .lastName(currentDoctor.getLastName())
                .address(currentDoctor.getAddress())
                .doctorType(currentDoctor.getDoctorType())
                .gender(currentDoctor.getGender())
                .description(currentDoctor.getDescription())
                .build();
        return response;
    }
}
