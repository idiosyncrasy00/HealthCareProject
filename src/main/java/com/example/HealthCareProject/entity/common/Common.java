package com.example.HealthCareProject.entity.common;

import com.example.HealthCareProject.config.DateTimeConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import lombok.AccessLevel;



@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Common implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected Long id;

    @JsonIgnore
    @CreatedDate
    //@Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Column(name = "created_at", updatable = false)
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String createdAt = DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss");

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated_at")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String updatedAt = DateTimeConfig.getCurrentDateTime("dd/MM/yyyy - HH:mm:ss");

//    @CreatedDate
////    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "createdAt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    protected LocalDateTime createdAt;
//
//    @LastModifiedDate
////    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updatedAt", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    protected LocalDateTime updatedAt;
}
