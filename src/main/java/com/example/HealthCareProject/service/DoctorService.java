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
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
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
import org.springframework.http.HttpStatus;
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
    public CustomeResponseEntity<?> getAppointments(String patientFullName, long doctorId,
                                             Collection<Integer> status, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Appointment> results;
        if (status.isEmpty()) {
            results = appointmentRepository.findAllByDoctorId(doctorId, paging);
        } else {
            results = appointmentRepository.findAppointmentsFromDoctor(patientFullName, doctorId, status, paging);
        }
        if (results.getContent().isEmpty()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "There are no available appointments!"), HttpStatus.NOT_FOUND);
        }
        List<AppointmentDTO> appointmentList = results.getContent().stream().map(result -> ConvertToDTOUtils.convertToAppointDetailsDTO(result)).collect(Collectors.toList());
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "sucess",
                appointmentList, ConvertToDTOUtils.convertToPagingDTO(results)), HttpStatus.OK);
    }

    @Cacheable("doctor")
    public CustomeResponseEntity<?> viewDoctorDetails(long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findByDoctorID(doctorId);
        if (!doctor.isPresent()) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"), HttpStatus.NOT_FOUND);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response), HttpStatus.OK);
    }
    @Transactional
    public CustomeResponseEntity<?> addNewDoctor(DoctorDTO.AddDoctor addedDoctor, long userId) {
            //find user
            //find user id?
            Optional<UserData> userData = userDataRepository.findById(userId);
        if (userData.isEmpty()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "user with id " + userId + " does not exist!"), HttpStatus.NOT_FOUND);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response), HttpStatus.OK);
    }

    @Transactional
    public CustomeResponseEntity<?> editDoctor(DoctorDTO.EditDoctor doctor, long doctorId) {
        //find user id?
        Optional<Doctor> currentDoctor = doctorRepository.findByDoctorID(doctorId);
        if (currentDoctor.isPresent()) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"), HttpStatus.NOT_FOUND);
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
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                response), HttpStatus.OK);
    }
}
