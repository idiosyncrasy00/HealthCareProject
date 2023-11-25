package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.service.DoctorService;
import com.example.HealthCareProject.service.PatientService;
import com.example.HealthCareProject.service.RequestViewHealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/request")
public class RequestViewHealthRecordController {
    private RequestViewHealthRecordService requestViewHealthRecordService;
    private DoctorService doctorService;
    private PatientService patientService;


    @Autowired
    public RequestViewHealthRecordController(RequestViewHealthRecordService requestViewHealthRecordService,
                                             PatientService patientService,
                                             DoctorService doctorService) {
        this.requestViewHealthRecordService = requestViewHealthRecordService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }
    //doctor requests viewing health record (doctorid, patientid, status = 0) post request
    @PostMapping("/doctor/make")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> requestViewHealthRecordFromDoctor(@RequestParam long doctorId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.doctorMakesRequest(doctorId, patientId);
    }

    //patient accepts viewing health record (doctorid, patientid, status = 1) put request
    @PutMapping("/patient/accept")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> acceptViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.patientAcceptsRequest(requestId, patientId);
    }

    //patient refuses viewing health record (doctorid, patientid, status = 2) put request
    @PutMapping("/patient/refuse")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> refuseViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.patientRefusesRequest(requestId, patientId);

    }

    //patient cancels viewing health record (doctorid, patientid, status = 3) put request
    @PutMapping("/patient/cancel")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> cancelViewHealthRecordFromPatient(@RequestParam long requestId,
                                                                      @RequestParam long patientId,
                                                                      @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.patientCancelsRequest(requestId, patientId);
    }




    //patient requests viewing health record (doctorid, patientid, status = 0) post request
    @PostMapping("/patient/make")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> requestViewHealthRecordFromPatient(@RequestParam long doctorId,
                                                                       @RequestParam long patientId,
                                                                       @RequestParam long id) {
        if (patientService.checkUserIdIsPatientId(patientId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.patientMakesRequest(doctorId, patientId);
    }

    //doctor accepts viewing health record (doctorid, patientid, status = 1) put request
    @PutMapping("/doctor/accept")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> acceptViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.doctorAcceptsRequest(requestId, doctorId);
    }

    //doctor refuses viewing health record (doctorid, patientid, status = 2) put request
    @PutMapping("/doctor/refuse")
    @PreAuthorize("hasRole('DOCTOR') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> refuseViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.doctorRefusesRequest(requestId, doctorId);

    }

    //doctor cancels viewing health record (doctorid, patientid, status = 3) put request
    @PutMapping("/doctor/cancel")
    @PreAuthorize("hasRole('PATIENT') and #id == authentication.principal.id")
    public CustomeResponseEntity<?> cancelViewHealthRecordFromDoctor(@RequestParam long requestId,
                                                                     @RequestParam long doctorId,
                                                                     @RequestParam long id) {
        if (doctorService.checkUserIdIsDoctorId(doctorId, id) < 1) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "You cannot do this operation!"), HttpStatus.BAD_REQUEST);
        }
        return requestViewHealthRecordService.doctorRefusesRequest(requestId, doctorId);

    }
}
