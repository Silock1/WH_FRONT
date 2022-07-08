package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.warehouse_accounting.exceptions.WarehouseCreatingProductDtoException;
import com.warehouse_accounting.models.dto.*;
import com.warehouse_accounting.services.interfaces.*;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


//TODO Реализовать загрузку и связывание полей-объектов
// todo: изображение пока не заполняется
// todo: тип продукции
// todo: в дто нет штрихкодов

@Log4j2
public class GoodsForm extends FormLayout {
    private final ProductService productService;
    private final TaxSystemService taxSystemService;
    private final AttributeOfCalculationObjectService attributeService;
//    private final ImageService imageService;
    private final UnitService unitService;
//    private final ProductGroupService productGroupService;
//    private final CountryService countryService = countryService;
//    private final UnitsOfMeasureService unitsOfMeasureService = unitsOfMeasureService;
//    private final ContractorService contractorService = contractorService;
    private final ProductDto productDto = new ProductDto();
    private final Binder<ProductDto> productDtoBinder = new Binder<>(ProductDto.class);

    public GoodsForm(ProductService productService, CountryService countryService,
                     UnitsOfMeasureService unitsOfMeasureService, ContractorService contractorService,
                     TaxSystemService taxSystemService, ImageService imageService,
                     ProductGroupService productGroupService, AttributeOfCalculationObjectService attributeService,
                     UnitService unitService) {
        this.productService = productService;
        this.taxSystemService = taxSystemService;
        this.attributeService = attributeService;
//        this.imageService = imageService;
        this.unitService = unitService;
//        this.countryService = countryService;
//        this.unitsOfMeasureService= unitsOfMeasureService;
//        this.contractorService = contractorService;
//        this.productGroupService = productGroupService;

        productDto.setTaxSystem(TaxSystemDto.builder().name(TaxSystemDto.SYSTEM_BY_PlACE)
                .sortNumber(TaxSystemDto.ATTRIBUTE_PRODUCT).build());
        productDto.setAttributeOfCalculationObject(AttributeOfCalculationObjectDto.builder()
                .isService(false)
                .name(AttributeOfCalculationObjectDto.PACKING_PER_UNIT)
                .sortNumber(AttributeOfCalculationObjectDto.ACCOUNTING_NONE).build());

        // List<ImageDto>
        // todo: изображение пока не заполняется
        final Button imageField = new Button("Изображение", event -> Notification.show("Not implemented"));
        productDto.setImages(new ArrayList<ImageDto>());

        final TextField nameField = new TextField("Наименование");
        nameField.setRequired(true);
        productDtoBinder.forField(nameField).asRequired().bind(ProductDto::getName, ProductDto::setName);
        nameField.setValue("Мой новый товар");

        final BigDecimalField weightField = new BigDecimalField("Вес");
        productDtoBinder.forField(weightField).bind(ProductDto::getWeight, ProductDto::setWeight);
        weightField.setValue(BigDecimal.valueOf(444.0));

        final BigDecimalField volumeField = new BigDecimalField("Объём");
        productDtoBinder.forField(volumeField).bind(ProductDto::getVolume, ProductDto::setVolume);
        volumeField.setValue(BigDecimal.valueOf(555.0));

        final BigDecimalField purchasePriceField = new BigDecimalField("Цена закупки");
        purchasePriceField.setValue(BigDecimal.valueOf(666.0));
        productDtoBinder.forField(purchasePriceField)
                .bind(ProductDto::getPurchasePrice, ProductDto::setPurchasePrice);

        final TextField descriptionField = new TextField("Описание");
        productDtoBinder.forField(descriptionField)
                .bind(ProductDto::getDescription, ProductDto::setDescription);
        descriptionField.setValue("Оченно нужный товар неизвестного происхождения");

        final TextField articulField = new TextField("Артикул");
        articulField.setRequired(true);
        productDtoBinder.forField(nameField).asRequired()
                .bind(ProductDto::getArticul, ProductDto::setArticul);
        articulField.setValue("articul_of_new_product_333");

        final BigDecimalField codeField = new BigDecimalField("Код");
        productDtoBinder.forField(codeField)
                .bind(ProductDto::getCode, ProductDto::setCode);
        codeField.setValue(BigDecimal.valueOf(777));

        final BigDecimalField outCodeField = new BigDecimalField("Внешний код");
        productDtoBinder.forField(outCodeField)
                .bind(ProductDto::getOutCode, ProductDto::setOutCode);
        outCodeField.setValue(BigDecimal.ZERO);

        ComboBox<CountryDto> countryField = new ComboBox<>("Страна", countryService.getAll());
        countryField.setAllowCustomValue(false);
        countryField.setItemLabelGenerator(CountryDto::getShortName);
        countryField.addValueChangeListener(event -> productDto.setCountry(event.getValue().getShortName()));
        countryField.setValue(countryService.getById(1L));

        final List<Float> ndsList = List.of(0f, 10f, 18f, 20f);
        ComboBox<Float> ndsField = new ComboBox<>("НДС", ndsList);
        ndsField.addValueChangeListener(event -> {
            Float newValue = event.getValue();
            Float oldValue = event.getOldValue();
            productDto.setNds(!(newValue < 0f || newValue > 100f) ? newValue : oldValue);
        });
        ndsField.setValue(ndsList.get(ndsList.size() - 1));

        final ComboBox<UnitsOfMeasureDto> measureField =
                new ComboBox<>("Единица измерения", unitsOfMeasureService.getAll());
        measureField.setItemLabelGenerator(UnitsOfMeasureDto::getName);
        measureField.setAllowCustomValue(false);
        measureField.addValueChangeListener(event -> productDto.setUnitsOfMeasureDto(event.getValue()));
        measureField.setValue(unitsOfMeasureService.getById(1L));

        final ComboBox<ContractorDto> contractorField = new ComboBox<>("Поставщик", contractorService.getAll());
        contractorField.setItemLabelGenerator(ContractorDto::getName);
        contractorField.setAllowCustomValue(false);
        contractorField.addValueChangeListener(event -> productDto.setContractor(event.getValue()));
        contractorField.setValue(contractorService.getById(1L));

        final ComboBox<ProductGroupDto> productGroupField = new ComboBox<>("Группа", productGroupService.getAll());
        productGroupField.setItemLabelGenerator(ProductGroupDto::getName);
        productGroupField.setAllowCustomValue(false);
        productGroupField.addValueChangeListener(event -> productDto.setProductGroup(event.getValue()));
        productGroupField.setValue(productGroupService.getById(1L));

        final List<String> taxSystems = List.of(TaxSystemDto.SYSTEM_BY_PlACE, TaxSystemDto.SYSTEM_BY_GROUP,
                TaxSystemDto.SYSTEM_USN_INCOME, TaxSystemDto.SYSTEM_USN_INCOME_OUTCOME, TaxSystemDto.SYSTEM_ENVD,
                TaxSystemDto.SYSTEM_OSN, TaxSystemDto.SYSTEM_ESHN, TaxSystemDto.SYSTEM_PATENT);
        final ComboBox<String> taxSystemField = new ComboBox<>("Система налогооблажения", taxSystems);
        taxSystemField.setAllowCustomValue(false);
        taxSystemField.addValueChangeListener(event -> productDto.getTaxSystem().setName(event.getValue()));
        taxSystemField.setValue(taxSystems.get(0));

        final List<String> taxCalculationAttributes = List.of(TaxSystemDto.ATTRIBUTE_PRODUCT,
                TaxSystemDto.ATTRIBUTE_COMPOSITE, TaxSystemDto.ATTRIBUTE_EXCISE, TaxSystemDto.ATTRIBUTE_ANOTHER);
        final ComboBox<String> taxAttributesField =
                new ComboBox<>("Признак предмета расчёта", taxCalculationAttributes);
        taxAttributesField.setAllowCustomValue(false);
        taxAttributesField.addValueChangeListener(event -> productDto.getTaxSystem().setSortNumber(event.getValue()));
        taxAttributesField.setValue(taxCalculationAttributes.get(0));

        final Label taxCaptionLabel = new Label("\nКассовый чек");

        final List<String> packingTypes = List.of(AttributeOfCalculationObjectDto.PACKING_PER_UNIT,
                AttributeOfCalculationObjectDto.PACKING_PER_WEIGTH, AttributeOfCalculationObjectDto.PACKING_PER_LIQUID);
        final ComboBox<String> packingField = new ComboBox<>("Фасовка", packingTypes);
        packingField.setAllowCustomValue(false);
        packingField.addValueChangeListener(
                event -> productDto.getAttributeOfCalculationObject().setName(event.getValue()));
        packingField.setValue(AttributeOfCalculationObjectDto.PACKING_PER_UNIT);

        final List<String> calculationTypes = List.of(AttributeOfCalculationObjectDto.ACCOUNTING_NONE,
                AttributeOfCalculationObjectDto.ACCOUNTING_ALCHOGOL,
                AttributeOfCalculationObjectDto.ACCOUNTING_SERIAL_NUMBER,
                AttributeOfCalculationObjectDto.ACCOUNTING_PROTECTION_THINGS);
        final ComboBox<String> calculationTypeField = new ComboBox<>("Тип учёта", calculationTypes);
        calculationTypeField.setAllowCustomValue(false);
        calculationTypeField.addValueChangeListener(
                event -> productDto.getAttributeOfCalculationObject().setSortNumber(event.getValue()));
        calculationTypeField.setValue(calculationTypes.get(0));

        final Checkbox isCalculationServiceField = new Checkbox("Прослеживаемый");
        isCalculationServiceField.addValueChangeListener(
                event -> productDto.getAttributeOfCalculationObject().setIsService(event.getValue()));

        final ComboBox<UnitDto> unitField = new ComboBox<>("Склад", unitService.getAll());
        unitField.setAllowCustomValue(false);
        unitField.setItemLabelGenerator(UnitDto::getShortName);
        unitField.addValueChangeListener(event -> productDto.setUnit(event.getValue()));
        unitField.setValue(unitService.getById(1L));

        final BigDecimalField reservedField = new BigDecimalField("Зарезервировать количество", "0");
        reservedField.addValueChangeListener(event -> {
                    productDto.getUnit().setSortNumber(event.getValue().toString());
                });
        reservedField.setValue(BigDecimal.ZERO);

        setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(new VerticalLayout(nameField, imageField, descriptionField, productGroupField, countryField, contractorField,
                articulField, codeField, outCodeField, measureField, weightField, volumeField, ndsField, unitField,
                reservedField, packingField, calculationTypeField, isCalculationServiceField, taxCaptionLabel,
                taxSystemField, taxAttributesField),
            new VerticalLayout(purchasePriceField));
    }

    public void save() {
        try {
            productDtoBinder.writeBean(productDto);
            productService.create(checkExistingDto(productDto));
        } catch (Exception err) {
            throw new WarehouseCreatingProductDtoException("Ошибка при создании нового товара", err);
        }
    }

    public ProductDto close() {
        return checkExistingDto(productDto);
    }

    private ProductDto checkExistingDto(ProductDto productDto) {
        final TaxSystemDto taxSystem = productDto.getTaxSystem();
        taxSystemService.getAll().stream()
                .filter(dto -> dto.getName().equals(taxSystem.getName())
                        && dto.getSortNumber().equals(taxSystem.getSortNumber()))
                .findFirst()
                .ifPresent(productDto::setTaxSystem);

        final AttributeOfCalculationObjectDto calculationAttribute = productDto.getAttributeOfCalculationObject();
        attributeService.getAll().stream()
                .filter(dto -> dto.getIsService().equals(calculationAttribute.getIsService())
                        && dto.getName().equals(calculationAttribute.getName())
                        && dto.getSortNumber().equals(calculationAttribute.getSortNumber()))
                .findFirst()
                .ifPresent(productDto::setAttributeOfCalculationObject);

        final UnitDto unitDto = productDto.getUnit();
        unitService.getAll().stream()
                .filter(dto -> dto.getShortName().equals(unitDto.getShortName())
                        && dto.getSortNumber().equals(unitDto.getSortNumber()))
                .findFirst()
                .ifPresent(productDto::setUnit);
        return productDto;
    }
}
