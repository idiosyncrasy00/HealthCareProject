package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.entity.Appointment;
import com.example.HealthCareProject.entity.AppointmentSlot;
import com.example.HealthCareProject.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long>, PagingAndSortingRepository<AppointmentSlot, Long> {
//    @Query(value = "SELECT a.id, a.appointment_date, a.appointment_time, " +
//            "a.doctor.id,a.doctor.fullName,a.doctor.address, a.doctor.gender," +
//            "p.prescriptionDescription, p.diagnosis, p.medicine, a.status " +
//            "from AppointmentSlot a " +
//            "left join Prescription p " +  "on a.id = p.appointmentSlot.id " +
//            "where a.doctor.id = ?1")
//    @Query(value = "SELECT a.id, a.appointment_date, a.appointment_time, " +
//            "d.id,d.full_name,d.address, d.gender," +
//            "p.prescription_description, p.diagnosis, p.medicine, a.status " +
//            "from appointment_slot a " +
//            "join doctor d " + "on a.doctor_id = d.id " +
//            "left join prescription p " +  "on a.id = p.appointment_slot_id " +
//            "where d.id = ?1", nativeQuery = true)
    //Page<Object[]> findAppointmentSlotsByDoctorId(long doctorId, Pageable pageable); //ByDoctorId

    Page<AppointmentSlot> findAppointmentSlotsByDoctorId(long doctorId, Pageable pageable); //ByDoctorId

}
