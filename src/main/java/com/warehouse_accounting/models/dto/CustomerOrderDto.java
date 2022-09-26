package com.warehouse_accounting.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehouse_accounting.components.util.DateConvertor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CustomerOrderDto {

/*    Long id;

    LocalDateTime date;
//    new
    private String number;
    private LocalDateTime invoiceDateTime;
    private boolean isPosted;
    private String type;
    private Long invoiceAuthorId;
    private String invoiceAuthorLastName;
    private String invoiceAuthorFirstName;

    WarehouseDto warehouseDto = new WarehouseDto();

    ContractDto contractDto = new ContractDto();

    CompanyDto companyDto = new CompanyDto();
    //new
    ProjectDto projectDto = new ProjectDto();

    ContractorDto contractorDto = new ContractorDto();

    List<ProductDto> productsDto = new ArrayList<>();
    //new
    private List<InvoiceProductDto> productDtos;

    List<TaskDto> tasksDto = new ArrayList<>();

    List<FileDto> filesDto = new ArrayList<>();

    SalesChannelDto channelDto = new SalesChannelDto();

    BigDecimal sum;

    String comment;

    Boolean isPaid;

    public CustomerOrderDto(Long id, LocalDateTime date, Long warehouseId,
                            Long contractId, Long companyId, Long contractorId,
                            BigDecimal sum, String comment, Boolean isPaid) {
        this.id = id;
        this.date = date;
        this.warehouseDto.setId(warehouseId);
        this.contractDto.setId(contractId);
        this.companyDto.setId(companyId);
        this.contractorDto.setId(contractorId);
        this.sum = sum;
        this.comment = comment;
        this.isPaid = isPaid;
    }*/

    private Long id;

    private String type;

    private String docNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateConvertor.dateTimePattern)
    private LocalDateTime date;

    private BigDecimal sum;

    private Long warehouseFromId;
    private String warehouseFromName;

    private Long companyId;
    private String companyName;

    private Long contrAgentId;
    private String contrAgentName;

    private Long projectId;
    private String projectName;

    private Long salesChannelId;
    private String salesChannelName;

    private Long contractId;
    private String contractNumber;

    private Boolean isSharedAccess;

//    private Long departmentId;
//    private String departmentName;

//    private Long employeeId;
//    private String employeeFirstname;

    private Boolean sent;

    private Boolean print;

    private String comments;

    private LocalDateTime updatedAt;

//    private Long updatedFromEmployeeId;
//    private String updatedFromEmployeeFirstname;

    private List<TaskDto> tasks = new ArrayList<>();

    private List<FileDto> files = new ArrayList<>();

    private List<ProductDto> products = new ArrayList<>();

    private List<InvoiceProductDto> productDtos = new ArrayList<>();

    private Boolean isPaid;

    private Long deliveryAddressId;
    private String deliveryAddressFull;

    private LocalDate plannedShipmentDate;

    private Boolean isPosted;
}
