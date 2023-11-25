package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.AppointmentSlotRepository;
import com.example.HealthCareProject.repository.DoctorRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class AppointmentSlotService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;

    public AppointmentSlotService(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentSlotRepository appointmentSlotRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }

    @Transactional
    public CustomeResponseEntity<?> makeAppointmentSlot(AppointmentSlotDTO appointmentSlotDTO) {
        long doctorId = appointmentSlotDTO.getDoctor().getId();

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"), HttpStatus.NOT_FOUND);
        }

        //TODO: check if time is in correct format

        //TODO: check if date is in correct format

        AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                .appointment_date(appointmentSlotDTO.getAppointment_date())
                .appointment_time(appointmentSlotDTO.getAppointment_time())
                .doctor(appointmentSlotDTO.getDoctor())
                .build();

        appointmentSlotRepository.save(appointmentSlot);

        AppointmentSlotDTO.AddSlotResponse response = AppointmentSlotDTO.AddSlotResponse.builder()
                .id(appointmentSlot.getId())
                .appointment_date(appointmentSlot.getAppointment_date())
                .appointment_time(appointmentSlot.getAppointment_time())
                .doctor_id(appointmentSlot.getDoctor().getId())
                .build();
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
                response, null), HttpStatus.OK);

    }

    public CustomeResponseEntity<?> getAppointmentSlotsByDoctorId(long doctorId, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "doctor with id " + doctorId + " does not exist!"), HttpStatus.NOT_FOUND);
        }
        Pageable paging = PageRequest.of(page, size);
        Page<AppointmentSlot> results;

        results = appointmentSlotRepository.findAppointmentSlotsByDoctorId(doctorId, paging);

        if (results.getContent().isEmpty()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "There are no available appointments!"), HttpStatus.NOT_FOUND);
        }

        List<AppointmentSlotDTO.AppointmentSlotDetails> appointmentSlotList = results.getContent().stream()
                .map(result -> ConvertToDTOUtils.convertToAppointmentSlotDetailsDTO(result)).collect(Collectors.toList());
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
                appointmentSlotList, ConvertToDTOUtils.convertToPagingDTO(results)), HttpStatus.OK);
    }
}
