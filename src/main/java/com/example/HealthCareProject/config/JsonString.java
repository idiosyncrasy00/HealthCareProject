package com.example.HealthCareProject.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonString {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
