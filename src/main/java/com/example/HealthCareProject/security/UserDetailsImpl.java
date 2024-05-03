package com.example.HealthCareProject.security;

import com.example.HealthCareProject.entity.Doctor;
import com.example.HealthCareProject.entity.Patient;
import com.example.HealthCareProject.entity.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

//    private String first_name;
//    private String last_name;
//    private String address;

    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, String email,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

//    public UserDetailsImpl(Long id, String username, String password, String email, Patient patient,
//                           Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.patient = patient;
//        this.authorities = authorities;
//    }
//
//    public UserDetailsImpl(Long id, String username, String password, String email, Doctor doctor,
//                           Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.doctor = doctor;
//        this.authorities = authorities;
//    }

    public static UserDetailsImpl build(UserData userData) {
        List<GrantedAuthority> authorities = userData.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        System.out.println("Authorities " + authorities.toString());
        if (authorities.toString().contains("DOCTOR")) {
            return new UserDetailsImpl(
                    userData.getId(),
                    userData.getUsername(),
                    userData.getPassword(),
//                userData.getFirst_name(),
//                userData.getLast_name(),
//                userData.getAddress(),
                    userData.getEmail(),
                    authorities
            );
        }
        return new UserDetailsImpl(
                userData.getId(),
                userData.getUsername(),
                userData.getPassword(),
//                userData.getFirst_name(),
//                userData.getLast_name(),
//                userData.getAddress(),
                userData.getEmail(),
                authorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
