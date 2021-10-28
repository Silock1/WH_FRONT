package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime dateOfCreation;
    private Boolean isDone = false;

    private Long executorId;
    private String executorName;

    private Long contractorId;
    private String contractorName;

    private Long documentId;
    private String documentName;
}
