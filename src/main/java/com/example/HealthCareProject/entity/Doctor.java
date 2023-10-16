package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.dto.UserDataDTO;
import com.example.HealthCareProject.entity.common.Common;
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
public class Doctor extends Common {
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
