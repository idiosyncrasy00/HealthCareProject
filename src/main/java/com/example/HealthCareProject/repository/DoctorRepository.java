package com.example.HealthCareProject.repository;

import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.entity.Doctor;
import jakarta.validation.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
//    @Query("SELECT u FROM Doctor u WHERE u.phoneNumber=?1")
//    Optional<Doctor> findByPhoneNumber(long phoneNumber);

    @Query("SELECT u FROM Doctor u where u.id=?1")
    Optional<Doctor> findByDoctorID(long doctorId);

    @Query(value = "SELECT d.first_name, d.last_name, d.address, d.description, d.gender, d.doctor_type, u.email, u.phone_number " +
            "from Doctor d " +
            "join user_data u on u.user_id = d.user_id where d.id=?1",
    nativeQuery = true)
    Object getDoctorInformation(long doctorId);

}
