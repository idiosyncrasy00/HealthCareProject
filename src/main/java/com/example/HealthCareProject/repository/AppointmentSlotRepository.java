package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long>, PagingAndSortingRepository<AppointmentSlot, Long> {
    Page<AppointmentSlot> findAppointmentSlotsByDoctorId(long doctorId, Pageable pageable); //ByDoctorId
}
