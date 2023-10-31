package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.PagingDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.persistence.Converter;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@EnableCaching
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserDataRepository userDataRepository;
    private final AppointmentRepository appointmentRepository;
    public DoctorService(DoctorRepository doctorRepository, UserDataRepository userDataRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.userDataRepository = userDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Cacheable("doctor")
    public int checkUserIdIsDoctorId(long doctorId, long userId) {
        return doctorRepository.checkUserIdIsDoctorId(doctorId, userId);
    }

        @Cacheable("appointments_doctor")
    public List<AppointmentDTO> getAppointments(String patientFullName, long doctorId,
                                             Collection<Integer> status, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Appointment> results;
        if (status.isEmpty()) {
            results = appointmentRepository.findAllByDoctorId(doctorId, paging);
        } else {
            results = appointmentRepository.findAppointmentsFromDoctor(patientFullName, doctorId, status, paging);
        }
        List<AppointmentDTO> appointmentList = results.getContent().stream().map(result -> ConvertToDTOUtils.convertToAppointDetailsDTO(result, results)).collect(Collectors.toList());
        return appointmentList;
    }

    @Cacheable("doctor")
    public Object viewDoctorDetails(long doctorId) {
        boolean isDoctorPresent = true;
        Optional<Doctor> doctor = doctorRepository.findByDoctorID(doctorId);
                //.orElseThrow(() -> new IllegalStateException("doctor with id " + doctorId + " does not exist!"));
        if (!doctor.isPresent()) {
            isDoctorPresent = false;
            return isDoctorPresent;
        }
        DoctorDTO.ViewDoctorResponse response = DoctorDTO.ViewDoctorResponse.builder()
                .fullName(doctor.get().getFullName())
                .address(doctor.get().getAddress())
                .description(doctor.get().getDescription())
                .gender(doctor.get().getGender())
                .doctorType(doctor.get().getDoctorType())
                .email(doctor.get().getUserData().getEmail())
                .phoneNumber(doctor.get().getUserData().getPhoneNumber())
                .build();
        return response;
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
                    .fullName(addedDoctor.getFullName())
                    .address(addedDoctor.getAddress())
                    .doctorType(addedDoctor.getDoctorType())
                    .gender(addedDoctor.getGender())
                    .userData(userData.get())
                    .description(addedDoctor.getDescription()).build();
            doctorRepository.save(doctor);
            DoctorDTO.AddDoctorResponse response = DoctorDTO.AddDoctorResponse.builder()
                    .fullName(addedDoctor.getFullName())
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
        currentDoctor.get().setFullName(doctor.getFullName());
        currentDoctor.get().setAddress(doctor.getAddress());
        currentDoctor.get().setDoctorType(doctor.getDoctorType());
        currentDoctor.get().setGender(doctor.getGender());
        currentDoctor.get().setDescription(doctor.getDescription());
        currentDoctor.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));


        DoctorDTO.EditDoctorResponse response = DoctorDTO.EditDoctorResponse.builder()
                .fullName(currentDoctor.get().getFullName())
                .address(currentDoctor.get().getAddress())
                .doctorType(currentDoctor.get().getDoctorType())
                .gender(currentDoctor.get().getGender())
                .description(currentDoctor.get().getDescription())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response));
    }
}
