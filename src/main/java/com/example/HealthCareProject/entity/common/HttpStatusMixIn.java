package com.example.HealthCareProject.entity.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;

public class HttpStatusMixIn {
    @JsonCreator
    public static HttpStatus resolve(int statusCode) {
        return HttpStatus.NO_CONTENT;
    }
}
