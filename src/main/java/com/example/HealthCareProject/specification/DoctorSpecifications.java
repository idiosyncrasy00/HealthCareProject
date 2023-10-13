package com.example.HealthCareProject.specification;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.UserData;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecifications {
//    public static Specification<Doctor> hasDoctorWithUserId(long userId) {
//        return (root, query, criteriaBuilder) -> {
//            Join<UserData,Doctor> userDataDoctor = root.join("UserData");
//            return criteriaBuilder.equal(userDataDoctor.get("userId"), userId);
//        };
//    }
}
