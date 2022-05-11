package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor



@NoArgsConstructor
public class SettingsDto {

    private Long id;

    private EmployeeDto employeeDto = new EmployeeDto();

    private CompanyDto companyDto = new CompanyDto();

    private WarehouseDto warehouseDto = new WarehouseDto();

    private ContractorDto customerDto = new ContractorDto();

    private ContractorDto producerDto = new ContractorDto();

    private ProjectDto projectDto = new ProjectDto();

    // Настройки
    private LanguageDto languageDto = new LanguageDto();

    private PrintingDocumentsDto printingDocumentsDto = new PrintingDocumentsDto();

    private int numberOfAdditionalFieldsPerLine;

    private StartScreenDto startScreenDto = new StartScreenDto();

    private boolean refreshReportsAuto;

    private boolean signatureInLetters;

    // Уведомления
    private NotificationsDto notificationsDto = new NotificationsDto();

    public SettingsDto(Long id,
                       Long employeeDtoId,
                       Long companyDtoId,
                       Long warehouseDtoId,
                       Long customerDtoId,
                       Long producerDtId,
                       Long projectDtoId,
                       Long languageDtoId,
                       Long printingDocumentsDtoId,
                       int numberOfAdditionalFieldsPerLine,
                       Long startScreenDtoId,
                       boolean refreshReportsAuto,
                       boolean signatureInLetters,
                       Long notificationsDtoId) {
        this.id = id;
        this.employeeDto.setId(employeeDtoId);
        this.companyDto.setId(companyDtoId);
        this.warehouseDto.setId(warehouseDtoId);
        this.customerDto.setId(customerDtoId);
        this.producerDto.setId(producerDtId);
        this.projectDto.setId(projectDtoId);
        this.languageDto.setId(languageDtoId);
        this.printingDocumentsDto.setId(printingDocumentsDtoId);
        this.numberOfAdditionalFieldsPerLine = numberOfAdditionalFieldsPerLine;
        this.startScreenDto.setId(startScreenDtoId);
        this.refreshReportsAuto = refreshReportsAuto;
        this.signatureInLetters = signatureInLetters;
        this.notificationsDto.setId(notificationsDtoId);
    }

    @Override
    public String toString() {
        return "SettingsDto{" +
                "id=" + id +
                ", employeeDto=" + employeeDto +
                ", companyDto=" + companyDto +
                ", warehouseDto=" + warehouseDto +
                ", customerDto=" + customerDto +
                ", producerDto=" + producerDto +
                ", projectDto=" + projectDto +
                ", languageDto=" + languageDto +
                ", printingDocumentsDto=" + printingDocumentsDto +
                ", numberOfAdditionalFieldsPerLine=" + numberOfAdditionalFieldsPerLine +
                ", startScreenDto=" + startScreenDto +
                ", refreshReportsAuto=" + refreshReportsAuto +
                ", signatureInLetters=" + signatureInLetters +
                ", notificationsDto=" + notificationsDto +
                '}';
    }
}
