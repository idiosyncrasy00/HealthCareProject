package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.catalina.connector.Response;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableCaching
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserDataRepository userDataRepository;
    private final AppointmentRepository appointmentRepository;
    private HttpServletResponse res = new Response();

    public PatientService(PatientRepository patientRepository, UserDataRepository userDataRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.userDataRepository = userDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Cacheable("patient")
    public int checkUserIdIsPatientId(long doctorId, long userId) {
        return patientRepository.checkUserIdIsPatientId(doctorId, userId);
    }

    @Cacheable("appointments_patient")
    public List<AppointmentDTO> getAppointments(long patientId, String doctorFullName, Collection<Integer> status, int page, int size) {
        Page<Appointment> results;
        Pageable paging = PageRequest.of(page, size);
        if (status.isEmpty()) {
            results = appointmentRepository.findAllByPatientId(patientId, paging);
        } else {
            results = appointmentRepository.findAppointmentsFromPatient(doctorFullName, patientId, status, paging);
        }
        List<AppointmentDTO> appointmentList = results.getContent().stream().map(ConvertToDTOUtils::convertToAppointDetailsDTOPatient).collect(Collectors.toList());
        return appointmentList;
    }

    public ResponseEntity<?> addNewPatient(PatientDTO.AddPatient addedPatient, long userId) {
        //find user
        //find user id?
        Optional<UserData> userData = userDataRepository.findById(userId);
        if (!userData.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
            "User with id " + userId + " not found."));
        }
        //build doctor
        Patient patient = Patient.builder()
                .fullName(addedPatient.getFullName())
                .address(addedPatient.getAddress())
                .gender(addedPatient.getGender())
                .userData(userData.get()).dob(addedPatient.getDob()).build(); //adding userid
        patientRepository.save(patient);

        PatientDTO.AddPatientResponse response = PatientDTO.AddPatientResponse.builder()
                .fullName(patient.getFullName())
                .address(patient.getAddress())
                .gender(patient.getGender())
                .userDataDTO(ConvertToDTOUtils.convertToUserDataDTO(patient.getUserData()))
                .dob(patient.getDob())
                .build();
        res.setStatus(StatusCode.SuccessCode);
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Patient from user id " + userId + " added.", response, res));
    }

    @Transactional
    public ResponseEntity<?> editPatient(PatientDTO.EditPatient patient, long patientId) {
        //find user id?
        Optional<Patient> currentPatient = patientRepository.findById(patientId);

        if (!currentPatient.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Patient with id " + patientId + " not found."));
        }
        currentPatient.get().setFullName(patient.getFullName());
        currentPatient.get().setAddress(patient.getAddress());
        currentPatient.get().setDob(patient.getDob());
        currentPatient.get().setGender(patient.getGender());

        PatientDTO.EditPatientResponse response = PatientDTO.EditPatientResponse.builder()
                .fullName(currentPatient.get().getFullName())
                .address(currentPatient.get().getAddress())
                .gender(currentPatient.get().getGender())
                .dob(currentPatient.get().getDob())
                .build();
        res.setStatus(StatusCode.SuccessCode);
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Patient id " + patientId + "edited.", response, res));
    }
}
