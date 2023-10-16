package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserDataRepository userDataRepository;

    public PatientService(PatientRepository patientRepository, UserDataRepository userDataRepository) {
        this.patientRepository = patientRepository;
        this.userDataRepository = userDataRepository;
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
                .firstName(addedPatient.getFirstName())
                .lastName(addedPatient.getLastName())
                .address(addedPatient.getAddress())
                .gender(addedPatient.getGender())
                .userData(userData.get()).dob(addedPatient.getDob()).build(); //adding userid
        patientRepository.save(patient);

        PatientDTO.AddPatientResponse response = PatientDTO.AddPatientResponse.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .address(patient.getAddress())
                .gender(patient.getGender())
                .userDataDTO(ConvertToDTOUtils.convertToUserDataDTO(patient.getUserData()))
                .dob(patient.getDob())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Patient from user id " + userId + " added.", response));
    }

    @Transactional
    public ResponseEntity<?> editPatient(PatientDTO.EditPatient patient, long patientId) {
        //find user id?
        Optional<Patient> currentPatient = patientRepository.findById(patientId);

        if (!currentPatient.isPresent()) {
            return ResponseEntity.status(StatusCode.NotFoundCode).body(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Patient with id " + patientId + " not found."));
        }
        currentPatient.get().setFirstName(patient.getFirstName());
        currentPatient.get().setLastName(patient.getLastName());
        currentPatient.get().setAddress(patient.getAddress());
        currentPatient.get().setDob(patient.getDob());
        currentPatient.get().setGender(patient.getGender());

        PatientDTO.EditPatientResponse response = PatientDTO.EditPatientResponse.builder()
                .firstName(currentPatient.get().getFirstName())
                .lastName(currentPatient.get().getLastName())
                .address(currentPatient.get().getAddress())
                .gender(currentPatient.get().getGender())
                .dob(currentPatient.get().getDob())
                .build();
        return ResponseEntity.status(StatusCode.SuccessCode).body(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Patient id " + patientId + "edited.", response));
    }
}
