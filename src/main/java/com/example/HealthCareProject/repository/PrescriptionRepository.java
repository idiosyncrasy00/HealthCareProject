package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.consts.ERole;
import com.example.HealthCareProject.entity.Prescription;
import com.example.HealthCareProject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query(value = "select p from Prescription p where p.appointmentSlot.id = ?1")
    Optional<Prescription> findByAppointmentSlotId(long appointmentSlotId);

}
