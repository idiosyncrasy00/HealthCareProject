package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.consts.ERole;
import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.*;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
