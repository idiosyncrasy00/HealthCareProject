package com.example.HealthCareProject.service;

import java.util.Optional;

import com.example.HealthCareProject.repository.AppointmentRepository;
import com.example.HealthCareProject.repository.UserDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.HealthCareProject.dto.DoctorDTO;
import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
  @Mock
  private DoctorRepository doctorRepository;

  @Mock
  private UserDataRepository userDataRepository;

  @Mock
  private AppointmentRepository appointmentRepository;

  @Mock
  private DoctorService doctorService;

  // @InjectMocks
  // DoctorDTO currentDoctor;

  @InjectMocks
  private Doctor currentDoctor;

  @BeforeEach
  public void setup() {
    //doctorRepository = Mockito.mock(DoctorRepository.class);
    doctorService = new DoctorService(doctorRepository, userDataRepository, appointmentRepository);
    currentDoctor = Doctor.builder()
        .fullName("Hoang")
        .address("Hanoi")
        .doctorType("General")
        .gender("Male")
        .description("Good doctor")
        // .email("test@gmail.com")
        // .phoneNumber("0123456789")
        .build();
    currentDoctor.setId(1L);

    doctorRepository.save(currentDoctor);
  }

  @Test
  @DisplayName("Should get doctor information by id")
  public void shouldGetDoctorInfo() {
    DoctorDTO.ViewDoctorResponse expectedDoctorInfo = DoctorDTO.ViewDoctorResponse.builder()
            .doctor_id(1L)
        .fullName("Hoang")
        .address("Hanoi")
        .doctorType("General")
        .gender("Male")
        .description("Good doctor")
        // .email("test@gmail.com")
        // .phoneNumber("0123456789")
        .build();
    Mockito.when(doctorRepository.findByDoctorID(1L)).thenReturn(Optional.of(currentDoctor));
    DoctorDTO.ViewDoctorResponse actualDoctorInfo = doctorService.viewDoctorDetails(1L);
    // Assertions.assertThat(actualDoctorInfo.g()).isEqualTo(expectedStudentInfo.getStudentId());
    Assertions.assertThat(actualDoctorInfo.getFullName()).isEqualTo(expectedDoctorInfo.getDoctorType());
    Assertions.assertThat(actualDoctorInfo.getDoctorType()).isEqualTo(expectedDoctorInfo.getDoctorType());
    Assertions.assertThat(actualDoctorInfo.getGender()).isEqualTo(expectedDoctorInfo.getGender());
  }

}
