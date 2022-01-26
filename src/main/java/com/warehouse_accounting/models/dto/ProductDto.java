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

    private BigDecimal weight;

    private BigDecimal volume;

    private BigDecimal purchasePrice;

    private String description;

    private Boolean archive = false;


    private List<ImageDto> images;

    /*
        Коррекция вызвана NPE которые возникают когда Ваадин получает объект в котором не инициализированы все поля.
        Поля содержащие ДТО объекты. Для этого я инициализирую поля ДТО созданными конструкторами по умолчанию.
        Довольно странно что здесь ДТО я меняю противоположным образом тому как я менял ДТО в Back там я убирал
        инициализацию полей объектами ДТО по умолчанию здесь добавляю... Если добавить обработчик ситуации по месту,
        то изменяя объект ДТО нам придется изменять и обработчик, а если обработчиков будет несколько?
    */
    private UnitDto unit = new UnitDto();

    private ContractorDto contractor = new ContractorDto();

    private TaxSystemDto taxSystem = new TaxSystemDto();

    private ProductGroupDto productGroup = new ProductGroupDto();

    private AttributeOfCalculationObjectDto attributeOfCalculationObject = new AttributeOfCalculationObjectDto();
}
