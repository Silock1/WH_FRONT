package com.warehouse_accounting.models.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallDto {

    private Long id;

    private Date callTime;

    private String type;

    private Long number;

    private Long callDuration;

    private String comment;

    private String callRecord;

    private Date whenChanged;

    private String contractorName;

    private Long contractorId;

    private String employeeWhoCalledName;

    private Long employeeWhoCalledId;

    private String employeeWhoChangedName;

    private Long employeeWhoChangedId;

}
