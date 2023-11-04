package com.example.HealthCareProject.entity.common;

import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serial;
import java.io.Serializable;


//https://stackoverflow.com/questions/34984786/spring-web-responseentity-cant-serialization
@Getter
@Setter
public class CustomeResponseEntity<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    HttpStatusCode status;
    Object body;
    HttpHeaders headers;

//    public CustomeResponseEntity(HttpStatusCode status) {
//        super(status);
//    }

    public CustomeResponseEntity(Object body, HttpStatusCode status) {
        this.body = body;
        this.status = status;
    }

    public CustomeResponseEntity(Object body, HttpStatusCode status, HttpHeaders headers) {
        this.body = body;
        this.status = status;
        this.headers = headers;
    }
//
//    public CustomeResponseEntity(MultiValueMap headers, HttpStatusCode status) {
//        super(headers, status);
//    }
//
//    public CustomeResponseEntity(Object body, MultiValueMap headers, HttpStatusCode status) {
//        super(body, headers, status);
//    }
//
//    public CustomeResponseEntity(int rawStatus, Object body, MultiValueMap headers) {
//        super(body, headers, rawStatus);
//    }

}
