package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.dto.UserDataDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Doctor"
)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long doctor_id;

    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="address")
    private String address;
    @Column(name="doctorType")
    private String doctorType;
    @Column(name="gender")
    private String gender;
    @Column(name="description")
    private String description;

    @OneToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserData userData;

}
