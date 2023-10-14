package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "update Appointment a set a.status = 1 where a.id=?1", nativeQuery = true)
    Appointment acceptAppointment(long appointmentID);

    @Query(value = "update Appointment a set a.status = 2 where a.id=?1", nativeQuery = true)
    Appointment rejectAppointment(long appointmentID);

    @Query(value = "select count(*) from Appointment a " +
            "where a.doctor_id=?1 and a.patient_id=?2 and a.status=1", nativeQuery = true)
    int getAcceptedAppointment(Long doctorId, Long patientId);
}
