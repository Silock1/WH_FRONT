package com.warehouse_accounting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationsDto {

    private Long id;

    private boolean isEnabled;

    private String description;

    private boolean isEmailProvided;

    private boolean isPhoneProvided;

    private String label;

}
