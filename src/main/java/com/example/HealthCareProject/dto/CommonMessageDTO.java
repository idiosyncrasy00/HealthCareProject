package com.example.HealthCareProject.dto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonMessageDTO<T> implements Serializable {
    protected int statusCode;
    protected String messageDetails = "success";
    protected T result;

    public CommonMessageDTO(int statusCode, String messageDetails) {
        this.statusCode = statusCode;
        this.messageDetails = messageDetails;
    }
    public CommonMessageDTO(int statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }
}
