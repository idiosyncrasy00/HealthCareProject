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
import org.springframework.http.ResponseEntity;
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
    public RequestViewHealthRecord doctorMakesRequest(long doctorId, long patientId) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Patient patient = new Patient();
        patient.setId(patientId);
        RequestViewHealthRecord requestViewHealthRecord = RequestViewHealthRecord.builder()
                .doctor(doctor)
                .patient(patient)
                .build();
        requestViewHealthRecordRepository.save(requestViewHealthRecord);
        return requestViewHealthRecord;
        //return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "Invite sent to the patient successfully!"), HttpStatus.OK);
    }

    //patient accepts viewing health record (doctorid, patientid, status = 1) put request
    @Transactional
    public  RequestViewHealthRecord patientAcceptsRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(1);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return requestViewHealthRecord.get();
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "Accept request from doctor successfully!"
//        ), HttpStatus.OK);
    }

    //patient refuses viewing health record (doctorid, patientid, status = 2) put request
    @Transactional
    public RequestViewHealthRecord patientRefusesRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(2);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        return requestViewHealthRecord.get();
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "Refuse request from doctor successfully!"
//        ), HttpStatus.OK);
    }

    //patient cancels viewing health record (doctorid, patientid, status = 3) put request
    @Transactional
    public RequestViewHealthRecord patientCancelsRequest(long requestId, long patientId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(3);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        return requestViewHealthRecord.get();
    }

    //patient requests viewing health record (doctorid, patientid, status = 0) post request
    @Transactional
    public  RequestViewHealthRecord patientMakesRequest(long doctorId, long patientId) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Patient patient = new Patient();
        patient.setId(patientId);
        RequestViewHealthRecord requestViewHealthRecord = RequestViewHealthRecord.builder()
                .doctor(doctor)
                .patient(patient)
                .build();
        return requestViewHealthRecordRepository.save(requestViewHealthRecord);
        //return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "Invite sent to the doctor successfully!"), HttpStatus.OK);
    }

    //doctor accepts viewing health record (doctorid, patientid, status = 1) put request
    @Transactional
    public  RequestViewHealthRecord doctorAcceptsRequest(long requestId, long doctorId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(1);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));
        return requestViewHealthRecord.get();
        //return "Accept request from patient successfully!";
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "Accept request from patient successfully!"
//        ), HttpStatus.OK);
    }

    //doctor refuses viewing health record (doctorid, patientid, status = 2) put request
    @Transactional
    public RequestViewHealthRecord doctorRefusesRequest(long requestId, long doctorId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(2);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return requestViewHealthRecord.get();

//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "Refuse request from patient successfully!"
//        ), HttpStatus.OK);
    }

    //doctor cancels viewing health record (doctorid, patientid, status = 3) put request
    public  RequestViewHealthRecord doctorCancelsRequest(long requestId) {
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
//        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
//        }
        requestViewHealthRecord.get().setStatus(3);
        requestViewHealthRecord.get().setUpdatedAt(DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss"));

        return requestViewHealthRecord.get();
//        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
//                "Cancel request from patient successfully!"
//        ), HttpStatus.OK);
    }
}
