package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.RequestViewHealthRecord;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.RequestViewHealthRecordRepository;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;
import com.example.HealthCareProject.service.RequestViewHealthRecordService;
import com.example.HealthCareProject.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/request")
public class RequestViewHealthRecordController {
    private RequestViewHealthRecordService requestViewHealthRecordService;
    private DoctorService doctorService;
    private PatientService patientService;

    private RequestViewHealthRecordRepository requestViewHealthRecordRepository;


    @Autowired
    public RequestViewHealthRecordController(RequestViewHealthRecordService requestViewHealthRecordService,
                                             PatientService patientService,
                                             DoctorService doctorService,
                                             RequestViewHealthRecordRepository requestViewHealthRecordRepository) {
        this.requestViewHealthRecordService = requestViewHealthRecordService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.requestViewHealthRecordRepository = requestViewHealthRecordRepository;
    }
    //doctor requests viewing health record (doctorid, patientid, status = 0) post request
    @PostMapping("/doctor/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<?> requestViewHealthRecordFromDoctor(@RequestParam long doctorId,
                                                               @RequestParam long patientId,
                                                               @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) <= 0) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        int check = requestViewHealthRecordRepository.countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 0);
        //boolean check = requestViewHealthRecordRepository
        if (check > 0) {
            return ResponseHandler.generateResponse("You/The patient already makes the request!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        try {
            RequestViewHealthRecord requestViewHealthRecord = requestViewHealthRecordService.doctorMakesRequest(doctorId, patientId);
            return ResponseHandler.generateResponse("Invite sent to the patient successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return requestViewHealthRecordService.doctorMakesRequest(doctorId, patientId);
    }

    //patient accepts viewing health record (doctorid, patientid, status = 1) put request
    @PutMapping("/patient/accept")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> acceptViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }

        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        try {
            RequestViewHealthRecord updateRequestViewHealthRecord = requestViewHealthRecordService.patientAcceptsRequest(requestId, patientId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    updateRequestViewHealthRecord
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //patient refuses viewing health record (doctorid, patientid, status = 2) put request
    @PutMapping("/patient/refuse")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> refuseViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            RequestViewHealthRecord requestViewHealthRecord1 = requestViewHealthRecordService.patientRefusesRequest(requestId, patientId);
            return ResponseHandler.generateResponse("Refuse request from doctor successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord1
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //patient cancels viewing health record (doctorid, patientid, status = 3) put request
    @PutMapping("/patient/cancel")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> cancelViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            RequestViewHealthRecord requestViewHealthRecord1 = requestViewHealthRecordService.patientCancelsRequest(requestId, patientId);
            return ResponseHandler.generateResponse("success",
                    HttpStatus.OK,
                    requestViewHealthRecord1
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //patient requests viewing health record (doctorid, patientid, status = 0) post request
    @PostMapping("/patient/make")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<Object> requestViewHealthRecordFromPatient(@RequestParam long doctorId,
                                                                       @RequestParam long patientId,
                                                                       @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        int check = requestViewHealthRecordRepository.countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 0);
        //boolean check = requestViewHealthRecordRepository
        if (check > 0) {
            return ResponseHandler.generateResponse("You/The doctor already makes the request!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        try {
            RequestViewHealthRecord requestViewHealthRecord = requestViewHealthRecordService.patientMakesRequest(doctorId, patientId);
            return ResponseHandler.generateResponse( "Invite sent to the doctor successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //doctor accepts viewing health record (doctorid, patientid, status = 1) put request
    @PutMapping("/doctor/accept")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> acceptViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.OK,
                    null
            );
        }

        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO(StatusCode.NotFoundCode,
//                    "The given request is not found!"), HttpStatus.NOT_FOUND);
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            RequestViewHealthRecord requestViewHealthRecord1 = requestViewHealthRecordService.doctorAcceptsRequest(doctorId, doctorId);
            return ResponseHandler.generateResponse("Invite sent to the doctor successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord1
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //doctor refuses viewing health record (doctorid, patientid, status = 2) put request
    @PutMapping("/doctor/refuse")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public ResponseEntity<Object> refuseViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);
        if (!requestViewHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        try {
            RequestViewHealthRecord requestViewHealthRecord1 = requestViewHealthRecordService.doctorRefusesRequest(requestId, doctorId);
            return ResponseHandler.generateResponse("Refuse request from patient successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord1
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //doctor cancels viewing health record (doctorid, patientid, status = 3) put request
    @PutMapping("/doctor/cancel")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public ResponseEntity<?> cancelViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
//            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
//                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
            return ResponseHandler.generateResponse("You cannot do this operation!",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
        Optional<RequestViewHealthRecord> requestViewHealthRecord = requestViewHealthRecordRepository.findById(requestId);

        if (!requestViewHealthRecord.isPresent()) {
            return ResponseHandler.generateResponse("The given request is not found!",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }

        try {
            RequestViewHealthRecord requestViewHealthRecord1 = requestViewHealthRecordService.doctorCancelsRequest(requestId);
            return ResponseHandler.generateResponse("Cancel request from patient successfully!",
                    HttpStatus.OK,
                    requestViewHealthRecord1
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
