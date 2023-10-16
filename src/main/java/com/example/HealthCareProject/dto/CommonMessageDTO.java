package com.example.HealthCareProject.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonMessageDTO<T> {
    private int statusCode;
    private String messageDetails = "success";
    private T result;

    public CommonMessageDTO(int statusCode, String messageDetails) {
        this.statusCode = statusCode;
        this.messageDetails = messageDetails;
    }

    public CommonMessageDTO(int statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }
}
