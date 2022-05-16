package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintingDocumentsDto {

    private Long id;

    private String printDoc;

    public String getPrintDoc(PrintingDocumentsDto printingDocumentsDto) {
        return printingDocumentsDto.printDoc == null ? "Document number: "
                + printingDocumentsDto.id
                : printingDocumentsDto.printDoc;
    }

    @Override
    public String toString() {
        return printDoc == null ? "Company number: " + id : printDoc;
    }
}
