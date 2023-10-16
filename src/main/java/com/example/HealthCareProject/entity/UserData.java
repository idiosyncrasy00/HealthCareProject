package com.example.HealthCareProject.entity;

import com.example.HealthCareProject.entity.common.Common;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "UserData",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class UserData extends Common {
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="phoneNumber", unique = true)
    private String phoneNumber;
    @Column(nullable = false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserData(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserData(long id, String username, String email, String phoneNumber, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
