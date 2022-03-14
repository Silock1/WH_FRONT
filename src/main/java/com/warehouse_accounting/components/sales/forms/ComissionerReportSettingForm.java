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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
import lombok.extern.log4j.Log4j2;



@Log4j2
public class ComissionerReportSettingForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private ComissionerReportsService comissionerReportsService;
//    private ProductDto productDto;
    private Binder<ProductDto> productDtoBinder;

    public ComissionerReportSettingForm(Div parentLayer, Component returnLayer, ComissionerReportsService comissionerReportsService) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.comissionerReportsService = comissionerReportsService;
//        this.productDto = new ProductDto();
        this.productDtoBinder = new Binder<>(ProductDto.class);
        VerticalLayout form = initForm();
        add(form);
    }


    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", event -> {
//            try {
//                productDtoBinder.writeBean(productDto);
//                comissionerReportsService.create(productDto);
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

        //Operations are grouped by fields : new field + something else + bind + form.add
        TextField nameField = new TextField("*Наименование товара", "наименование товара");
        nameField.setRequired(true);
        //productDtoBinder.forField(nameField).asRequired().bind(ProductDto::getName, ProductDto::setName);
        //form.add(nameField);
//
//        TextField descriptionField = new TextField("Описание","описание товара");
//        productDtoBinder.forField(descriptionField).bind(ProductDto::getDescription, ProductDto::setDescription);
//        form.add(descriptionField);
//
//        TextField groupField = new TextField("Группа","введите группу товару");
//        productDtoBinder.forField(groupField).bind(ProductDto::getGroup, ProductDto::setGroup);
//        form.add(groupField);
//
//        TextField countryField = new TextField("Страна","");
//        productDtoBinder.forField(countryField).bind(ProductDto::getCountry, ProductDto::setCountry);
//        form.add(countryField);
//
//        TextField articulField = new TextField("Артикул","введите артикул");
//        productDtoBinder.forField(articulField).bind(ProductDto::getArticul, ProductDto::setArticul);
//        form.add(articulField);
//
//        BigDecimalField codeField = new BigDecimalField("Код", "введите код товара");
//        productDtoBinder.forField(codeField).bind(ProductDto::getCode, ProductDto::setCode);
//        form.add(codeField);
//
//        BigDecimalField outCodeField = new BigDecimalField("Внешний Код", "введите внешний код товара");
//        productDtoBinder.forField(outCodeField).bind(ProductDto::getOutCode, ProductDto::setOutCode);
//        form.add(outCodeField);
//
//        BigDecimalField weightField = new BigDecimalField("Вес", "вес единицы товара");
//        productDtoBinder.forField(weightField).bind(ProductDto::getWeight, ProductDto::setWeight);
//        form.add(weightField);
//
//        BigDecimalField volumeField = new BigDecimalField("Объём", "объём единицы товара");
//        productDtoBinder.forField(volumeField).bind(ProductDto::getVolume, ProductDto::setVolume);
//        form.add(volumeField);
//
//        BigDecimalField purchasePriceField = new BigDecimalField("Цена закупки", "цена закупки товара");
//        productDtoBinder.forField(purchasePriceField).bind(ProductDto::getPurchasePrice, ProductDto::setPurchasePrice);
//        form.add(purchasePriceField);


        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        return form;
    }
}