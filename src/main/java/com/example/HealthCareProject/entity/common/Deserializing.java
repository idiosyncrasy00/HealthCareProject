package com.example.HealthCareProject.entity.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@NoArgsConstructor
public class Deserializing<T> {
    public static Object deserializeEntity(CustomeResponseEntity<?> entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(CustomeResponseEntity.class, CustomResponseEntityMixin.class);
        objectMapper.addMixIn(HttpStatus.class, HttpStatusMixIn.class);

        String json = objectMapper.writeValueAsString(entity);
        TypeReference ref = new TypeReference<CustomeResponseEntity<Object>>() {
        };
        //CustomeResponseEntity<?> result = objectMapper.readValue(json, ref);
        return objectMapper.readValue(json, ref);
    }
}
