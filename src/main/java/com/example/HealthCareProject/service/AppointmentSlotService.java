package com.example.HealthCareProject.service;

import com.example.HealthCareProject.utils.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
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
    public AppointmentSlotDTO.AddSlotResponse makeAppointmentSlot(AppointmentSlotDTO appointmentSlotDTO) {

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
        return response;

    }

    public List<AppointmentSlotDTO.AppointmentSlotDetails> getAppointmentSlotsByDoctorId(long doctorId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<AppointmentSlot> results;

        results = appointmentSlotRepository.findAppointmentSlotsByDoctorId(doctorId, paging);

        List<AppointmentSlotDTO.AppointmentSlotDetails> appointmentSlotList = results.getContent().stream()
                .map(result -> ConvertToDTOUtils.convertToAppointmentSlotDetailsDTO(result)).collect(Collectors.toList());
        return appointmentSlotList;
        //        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "success",
//                appointmentSlotList
//                //results
//                , ConvertToDTOUtils.convertToPagingDTO(results)), HttpStatus.OK);
    }
}
