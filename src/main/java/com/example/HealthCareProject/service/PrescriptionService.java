package com.example.HealthCareProject.service;

import com.example.HealthCareProject.consts.StatusCode;
import com.example.HealthCareProject.dto.CommonMessageDTO;
import com.example.HealthCareProject.dto.PrescriptionDTO;
import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Prescription;
import com.example.HealthCareProject.entity.common.CustomeResponseEntity;
import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.AppointmentSlotRepository;
import com.example.HealthCareProject.repository.PrescriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
//    private final AppointmentRepository appointmentRepository;

    private final AppointmentSlotRepository appointmentSlotRepository;


    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               //AppointmentRepository appointmentRepository,
                               AppointmentSlotRepository appointmentSlotRepository) {
        this.prescriptionRepository = prescriptionRepository;
//        this.appointmentRepository = appointmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }

//    public CustomeResponseEntity<?> viewPrescriptionsByDoctorId(long prescriptionId) {
//
//        return null;
//    }
//
//    public CustomeResponseEntity<?> viewPrescriptionsByPatientId(long prescriptionId) {
//        return null;
//    }

    @Transactional
    public CustomeResponseEntity<?> addNewPrescription(PrescriptionDTO prescriptionDTO) {
        long appointmentSlotId = prescriptionDTO.getAppointmentSlot().getId();
        Optional<AppointmentSlot> appointmentSlot = appointmentSlotRepository.findById(appointmentSlotId);
        if (!appointmentSlot.isPresent()) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "appointment with id " + appointmentSlotId + " does not exist!"), HttpStatus.NOT_FOUND);
        }
        int getStatus = appointmentSlot.get().getStatus();
        //the healthcheck is not over
        if (getStatus != 2) {
            return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.BadRequestCode,
                    "The healthcheck is not over!"), HttpStatus.BAD_REQUEST);
        }

        Prescription prescription = Prescription.builder()
                .diagnosis(prescriptionDTO.getDiagnosis())
                .prescriptionDescription(prescriptionDTO.getPrescriptionDescription())
                .medicine(prescriptionDTO.getMedicine())
                .appointmentSlot(prescriptionDTO.getAppointmentSlot())
                .build();
        prescriptionRepository.save(prescription);

        //update appointmentslot
        appointmentSlot.get().setPrescription(prescription);

        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode, "success"), HttpStatus.OK);
    }

    @Transactional
    public CustomeResponseEntity<?> editPrescription(PrescriptionDTO.EditPrescription prescription) {
        long prescriptionId = prescription.getId();
        Optional<Prescription> currentPrescription = prescriptionRepository.findById(prescriptionId);
        if (!currentPrescription.isPresent()) {
            return new CustomeResponseEntity(new CommonMessageDTO<>(StatusCode.NotFoundCode,
                    "Prescription with id " + prescriptionId + " does not exist!"), HttpStatus.NOT_FOUND);
        }
        currentPrescription.get().setDiagnosis(prescription.getDiagnosis());
        currentPrescription.get().setPrescriptionDescription(prescription.getPrescriptionDescription());
        currentPrescription.get().setMedicine(prescription.getMedicine());
        return new CustomeResponseEntity<>(new CommonMessageDTO<>(StatusCode.SuccessCode,
                "success"), HttpStatus.OK);
    }
}
