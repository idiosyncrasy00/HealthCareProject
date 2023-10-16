//package com.example.HealthCareProject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.auditing.DateTimeProvider;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.ZonedDateTime;
//import java.time.temporal.TemporalAccessor;
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
//public class JpaAuditingConfig {
//    @Bean
//    public DateTimeProvider auditingDateTimeProvider() {
//        return () -> Optional.of(ZonedDateTime.now());
//    }
//}
