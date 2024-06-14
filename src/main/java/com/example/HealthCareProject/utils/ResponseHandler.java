package com.example.HealthCareProject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", responseObj);
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponseWithPaging(String message, HttpStatus status,
                                                                    int currentPage,
                                                                    int pageSize,
                                                                    int totalPages,
                                                                    int totalRecords,
                                                                    Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> pagingMap = new HashMap<>();

        pagingMap.put("currentPage", currentPage);
        pagingMap.put("pageSize", pageSize);
        pagingMap.put("totalPages", totalPages);
        pagingMap.put("totalRecords", totalRecords);
        pagingMap.put("records", responseObj);

        map.put("message", message);
        map.put("status", status.value());
        map.put("data", pagingMap);

        return new ResponseEntity<Object>(map,status);
    }
}
