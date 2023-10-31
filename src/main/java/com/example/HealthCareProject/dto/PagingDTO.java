package com.example.HealthCareProject.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PagingDTO<T> implements Serializable {
    protected int currentPage;
    protected long totalRecords;
    protected int totalPages;

}
