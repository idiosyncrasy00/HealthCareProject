package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Patient"
)
public class Patient extends Common {

    @Column(name="fullName")
    private String fullName;
    @Column(name="address")
    private String address;
    @Column(name="dateOfBirth")
    private String dob; //yyyy-mm-dd
    @Column(name="Gender")
    private String gender;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.MERGE)
    //@OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserData userData;

//    @Version
//    private Long version;

}
