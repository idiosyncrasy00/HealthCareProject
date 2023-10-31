package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.dto.AppointmentDTO;
import com.example.HealthCareProject.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, PagingAndSortingRepository<Appointment, Long> {
//    @Query(value = "update Appointment a set a.status = 1 where a.id=?1", nativeQuery = true)
//    Appointment acceptAppointment(long appointmentID);
//
//    @Query(value = "update Appointment a set a.status = 2 where a.id=?1", nativeQuery = true)
//    Appointment rejectAppointment(long appointmentID);

//    @Query(value = "select count(*) from Appointment a " +
//            "where a.doctor_id=?1 and a.patient_id=?2 and a.status=1", nativeQuery = true)
//    int getAcceptedAppointment(Long doctorId, Long patientId);

    //int findAppointmentByStatusEqualsAndDoctorAndPatient_Id(int status,Long doctorId, Long patientId);
    int countByStatusEqualsAndDoctorIdAndPatientId(int status,long doctorId, long patientId);

    Page<Appointment> findAllByPatientId(long patientId, Pageable pageable);
    //List<Appointment> findAllByPatientId(long patientId);


    Page<Appointment> findAllByDoctorId(long doctorId, Pageable pageable);

//    @Query("SELECT a from Appointment a where a.patient.id ")
//    Collection<Appointment> findAppointmentsByPatientIdAndDoctorIdAndStatusIn


    @Query("SELECT a from Appointment a inner join Patient p " +
            "on a.patient.id = p.id and a.doctor.id = ?2 where a.status in ?3 and p.fullName like %?1%")
    Page<Appointment> findAppointmentsFromDoctor(String patientFullName,
                                                 long doctorId, Collection<Integer> status, Pageable pageable);
    @Query("SELECT a from Appointment a inner join Doctor d " +
            "on a.doctor.id = d.id and a.patient.id = ?2 where a.status in ?3 and d.fullName like %?1%")
    Page<Appointment> findAppointmentsFromPatient(String doctorFullName, long patientId, Collection<Integer> status, Pageable pageable);

}
