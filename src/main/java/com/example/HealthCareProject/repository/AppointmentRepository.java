package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("update Appointment a set a.status = 1 where a.id=?1")
    Appointment acceptAppointment(long appointmentID);

    @Query("update Appointment a set a.status = 2 where a.id=?1")
    Appointment rejectAppointment(long appointmentID);
}
