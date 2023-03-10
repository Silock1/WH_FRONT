package com.warehouse_accounting.models.dto;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksDto {
    private Long id;
    private String description; //описание
    private String deadline; // дата конца
    private Boolean isDone = false;

    private Long employeeId;
    private String employeeName;

    private Long contractorId; //контрагент
    private String contractorName;

    private Long contractId; //документ
    private String contractNumber;

    public LocalDateTime getDeadLineAsLocalDateTime(){
        return LocalDateTime.parse(deadline);
    }

}
