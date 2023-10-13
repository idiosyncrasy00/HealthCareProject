package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.ConvertToDTOUtils;
import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.dto.PatientDTO;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.example.HealthCareProject.repository.PatientRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserDataRepository userDataRepository;

    public PatientService(PatientRepository patientRepository, UserDataRepository userDataRepository) {
        this.patientRepository = patientRepository;
        this.userDataRepository = userDataRepository;
    }

    public PatientDTO.AddPatientResponse addNewPatient(PatientDTO.AddPatient addedPatient, long userId) {
        //find user
        //find user id?
        UserData userData = userDataRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist!"));;
        //build doctor
        Patient patient = Patient.builder()
                .firstName(addedPatient.getFirstName())
                .lastName(addedPatient.getLastName())
                .address(addedPatient.getAddress())
                //.phoneNumber(addedDoctor.getPhoneNumber())
                .gender(addedPatient.getGender())
                .userData(userData).dob(addedPatient.getDob()).build(); //adding userid
        patientRepository.save(patient);

        PatientDTO.AddPatientResponse response = PatientDTO.AddPatientResponse.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .address(patient.getAddress())
                .gender(patient.getGender())
                .userDataDTO(ConvertToDTOUtils.convertToUserDataDTO(patient.getUserData()))
                .dob(patient.getDob())
                .build();
        return response;
    }

    @Transactional
    public PatientDTO.EditPatientResponse editPatient(PatientDTO.EditPatient patient, long patientId) {
        //find user id?
        Patient currentPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalStateException("patient with id " + patientId + " does not exist!"));
        currentPatient.setFirstName(patient.getFirstName());
        currentPatient.setLastName(patient.getLastName());
        currentPatient.setAddress(patient.getAddress());
        currentPatient.setDob(patient.getDob());
        currentPatient.setGender(patient.getGender());

        PatientDTO.EditPatientResponse response = PatientDTO.EditPatientResponse.builder()
                .firstName(currentPatient.getFirstName())
                .lastName(currentPatient.getLastName())
                .address(currentPatient.getAddress())
                .gender(currentPatient.getGender())
                .dob(currentPatient.getDob())
                .build();
        return response;
    }
}
