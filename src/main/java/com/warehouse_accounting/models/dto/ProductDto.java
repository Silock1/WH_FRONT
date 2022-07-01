package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private String country;

    private String articul;

    private BigDecimal code;

    private BigDecimal outCode;

    private UnitsOfMeasureDto unitsOfMeasureDto;

    private BigDecimal weight;

    private BigDecimal volume;

    private BigDecimal purchasePrice;

    private Float nds;

    private String description;

    private Boolean archive = false;

    private String productGroup;

    private ContractorDto contractor = new ContractorDto();

    private List<ImageDto> images;

    /*
        Инициализация вызвана NPE которые возникают когда Ваадин получает объект в котором не инициализированы все поля.
        NPE возникает поскольку Ваадин запрашивает getName(или что-то ещё) у несуществующего объекта в поле
        содержащем ДТО объекты. Для этого я инициализирую поля ДТО созданными конструкторами по умолчанию.
        Если добавить обработчик ситуации по месту,то изменяя объект ДТО нам придется изменять и обработчик,
        а если обработчиков будет несколько?
    */
    private UnitDto unit = new UnitDto(); // что это? склад? Если склад, то нужен список ИД

//    private ProductGroupDto productGroup = new ProductGroupDto();

    private TaxSystemDto taxSystem = new TaxSystemDto(); // Штрихкоды товара?

    private AttributeOfCalculationObjectDto attributeOfCalculationObject = new AttributeOfCalculationObjectDto(); // особенности учёта?
}
