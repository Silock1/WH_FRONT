package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
 * ИНФОРМАЦИЯ ДЛЯ ФРОНТ-РАЗРАБОТЧИКА
 * Для снижения количества запросов к базе при передаче на фронт объектов типа Invoice
 * класс InvoiceDto реконструирован: там, где это было возможно, вместо полей-объектов
 * используются поля-примитивы или строки.
 * Ваша задача - при разработке фронтовой части Invoice собрать информацию для полей
 * "Создан" и "Договор", сконкатенировав поля InvoiceDto следующим образом:
 *
 * "Создан":
 * invoiceAuthorLastName + пробел + первая буква invoiceAuthorFirstName + точка.
 * "Договор":
 * "№" + contractNumber + пробел + "от" + пробел + contractDate
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String number;
    private LocalDateTime invoiceDateTime;
    private String type;
    private boolean isPosted;

    private Long invoiceAuthorId;
    private String invoiceAuthorLastName;
    private String invoiceAuthorFirstName;

    private Long companyId;
    private String companyName;

    private Long projectId;
    private String projectName;

    private Long warehouseId;
    private String warehouseName;

    private List<InvoiceProductDto> productDtos;
    private String comment;

    private Long contractorId;
    private String contractorName;

    private Long contractId;
    private String contractNumber;
    private LocalDate contractDate;

    private List<InvoiceEditDto> edits;

}
