package com.example.HealthCareProject.entity.common;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;


//https://stackoverflow.com/questions/34984786/spring-web-responseentity-cant-serialization
@Getter
@Setter
public class CustomeResponseEntity<T> extends ResponseEntity implements Serializable {
    private static final long serialVersionUID = 7156526077883281625L;

    public CustomeResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public CustomeResponseEntity(Object body, HttpStatusCode status) {
        super(body, status);
    }

    public CustomeResponseEntity(MultiValueMap headers, HttpStatusCode status) {
        super(headers, status);
    }

    public CustomeResponseEntity(Object body, MultiValueMap headers, HttpStatusCode status) {
        super(body, headers, status);
    }

    public CustomeResponseEntity(int rawStatus, Object body, MultiValueMap headers) {
        super(body, headers, rawStatus);
    }

}
