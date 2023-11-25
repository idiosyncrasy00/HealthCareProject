package com.example.HealthCareProject.service;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.RequestViewHealthRecord;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.RequestViewHealthRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RequestViewHealthRecordService {

    private final RequestViewHealthRecordRepository requestViewHealthRecordRepository;

    public RequestViewHealthRecordService(RequestViewHealthRecordRepository requestViewHealthRecordRepository) {
        this.requestViewHealthRecordRepository = requestViewHealthRecordRepository;
    }


    //doctor requests viewing health record (doctorid, patientid, status = 0) post request
    public CustomeResponseEntity<?> doctorMakesRequest(long doctorId, long patientId) {
        int check = requestViewHealthRecordRepository.countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 0);
        //boolean check = requestViewHealthRecordRepository
        if (check > 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.BadRequestCode,
                    "You/The patient already makes the request!"), HttpStatus.BAD_REQUEST);
        }
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Patient patient = new Patient();
        patient.setId(patientId);
        RequestViewHealthRecord requestViewHealthRecord = RequestViewHealthRecord.builder()
                .doctor(doctor)
                .patient(patient)
                .build();
        requestViewHealthRecordRepository.save(requestViewHealthRecord);
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "Invite sent to the patient successfully!"), HttpStatus.OK);
    }

    //patient accepts viewing health record (doctorid, patientid, status = 1) put request
    @Transactional
    public  CustomeResponseEntity<?> patientAcceptsRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(1);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Accept request from doctor successfully!"
        ), HttpStatus.OK);
    }

    //patient refuses viewing health record (doctorid, patientid, status = 2) put request
    @Transactional
    public  CustomeResponseEntity<?> patientRefusesRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(2);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Refuse request from doctor successfully!"
        ), HttpStatus.OK);
    }

    //patient cancels viewing health record (doctorid, patientid, status = 3) put request
    @Transactional
    public  CustomeResponseEntity<?> patientCancelsRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(3);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Cancel request from doctor successfully!"
        ), HttpStatus.OK);
    }

    //patient requests viewing health record (doctorid, patientid, status = 0) post request
    @Transactional
    public  CustomeResponseEntity<?> patientMakesRequest(long doctorId, long patientId) {
        int check = requestViewHealthRecordRepository.countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 0);
        //boolean check = requestViewHealthRecordRepository
        if (check > 0) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.BadRequestCode,
                    "You/The doctor already makes the request!"), HttpStatus.BAD_REQUEST);
        }
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Patient patient = new Patient();
        patient.setId(patientId);
        RequestViewHealthRecord requestViewHealthRecord = RequestViewHealthRecord.builder()
                .doctor(doctor)
                .patient(patient)
                .build();
        requestViewHealthRecordRepository.save(requestViewHealthRecord);
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "Invite sent to the doctor successfully!"), HttpStatus.OK);
    }

    //doctor accepts viewing health record (doctorid, patientid, status = 1) put request
    @Transactional
    public  CustomeResponseEntity<?> doctorAcceptsRequest(long requestId, long doctorId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(1);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Accept request from patient successfully!"
        ), HttpStatus.OK);
    }

    //doctor refuses viewing health record (doctorid, patientid, status = 2) put request
    @Transactional
    public CustomeResponseEntity<?> doctorRefusesRequest(long requestId, long doctorId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(2);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Refuse request from patient successfully!"
        ), HttpStatus.OK);
    }

    //doctor cancels viewing health record (doctorid, patientid, status = 3) put request
    public  CustomeResponseEntity<?> doctorCancelsRequest(long requestId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
                    "The given request is not found!"), HttpStatus.NOT_FOUND);
        }
        requestViewHealthRecord.get().setStatus(3);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "Cancel request from patient successfully!"
        ), HttpStatus.OK);
    }
}
