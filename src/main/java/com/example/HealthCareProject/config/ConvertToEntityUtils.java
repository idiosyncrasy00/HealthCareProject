package com.example.HealthCareProject.config;

import com.example.HealthCareProject.dto.AppointmentSlotDTO;
import com.example.HealthCareProject.entity.AppointmentSlot;

public class ConvertToEntityUtils {
    public static AppointmentSlot convertToAppointmentSlotEntity(AppointmentSlotDTO appointmentSlotDTO) {
        return AppointmentSlot.builder()
                .appointment_date(appointmentSlotDTO.getAppointment_date())
                .appointment_time(appointmentSlotDTO.getAppointment_time())
                .doctor(appointmentSlotDTO.getDoctor())
                .build();
    }
}
