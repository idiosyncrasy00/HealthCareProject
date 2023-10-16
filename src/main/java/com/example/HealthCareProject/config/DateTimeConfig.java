package com.example.HealthCareProject.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConfig {
    public static String getCurrentDateTime(String dateTimeFormat) {
        //dd/MM/yyyy - HH:mm:ss
        return DateTimeFormatter.ofPattern(dateTimeFormat)
                .format(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
    }
}
