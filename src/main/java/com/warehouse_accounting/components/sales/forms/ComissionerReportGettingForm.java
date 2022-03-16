package com.warehouse_accounting.components.sales.forms;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
import com.warehouse_accounting.services.interfaces.ProductService;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class ComissionerReportGettingForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private ComissionerReportsService comissionerReportsService;
    private ProductDto productDto;
    private Binder<ProductDto> productDtoBinder;

    public ComissionerReportGettingForm(Div parentLayer, Component returnLayer, ComissionerReportsService comissionerReportsService) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.comissionerReportsService = comissionerReportsService;
        this.productDto = new ProductDto();
        this.productDtoBinder = new Binder<>(ProductDto.class);
        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", event -> {
//            try {
//                productDtoBinder.writeBean(productDto);
//                productService.create(productDto);
//            } catch (ValidationException ex) {
//                log.error("Ошибка валидации при создании нового товара", ex);
//            }
//            parentLayer.removeAll();
//            parentLayer.add(returnLayer);
        });

        Button close = new Button("Закрыть", event -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save, close);

        FormLayout form = getFormLayout();
        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(form);
        verticalLayout.add(groupButton, groupForm);
        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();

        //ToDo Код для примера оформления полей
        //Operations are grouped by fields : new field + something else + bind + form.add
//        TextField nameField = new TextField("*Наименование товара", "наименование товара");
//        nameField.setRequired(true);
//        productDtoBinder.forField(nameField).asRequired().bind(ProductDto::getName, ProductDto::setName);
//        form.add(nameField);
//
//        TextField descriptionField = new TextField("Описание","описание товара");
//        productDtoBinder.forField(descriptionField).bind(ProductDto::getDescription, ProductDto::setDescription);
//        form.add(descriptionField);
//
//        BigDecimalField codeField = new BigDecimalField("Код", "введите код товара");
//        productDtoBinder.forField(codeField).bind(ProductDto::getCode, ProductDto::setCode);
//        form.add(codeField);
//
//        BigDecimalField purchasePriceField = new BigDecimalField("Цена закупки", "цена закупки товара");
//        productDtoBinder.forField(purchasePriceField).bind(ProductDto::getPurchasePrice, ProductDto::setPurchasePrice);
//        form.add(purchasePriceField);


        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        return form;
    }
}