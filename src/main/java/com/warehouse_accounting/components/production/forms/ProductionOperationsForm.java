package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;


import java.time.LocalDate;
import java.util.stream.Stream;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Log4j2
@UIScope
public class ProductionOperationsForm extends VerticalLayout {

    private ProductionOperations productionOperations;
    private final TechnologicalOperationDto technologicalOperation;
    private final Div returnDiv;
    private final WarehouseService warehouseService;
    private final TechnologicalMapService technologicalMapService;
    private final Binder <TechnologicalOperationDto> technologicalOperationDtoBinder = new Binder<>(TechnologicalOperationDto.class);


    public ProductionOperationsForm(ProductionOperations productionOperations, TechnologicalOperationDto technologicalOperation,
                                     WarehouseService warehouseService, TechnologicalMapService technologicalMapService) {
        this.technologicalOperation = technologicalOperation;
        this.productionOperations = productionOperations;
        this.warehouseService = warehouseService;
        this.technologicalMapService = technologicalMapService;
        this.returnDiv = productionOperations.getPageContent();
        productionOperations.removeAll();
        add(createTopGroupElements());
        createColumns();
    }


    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        topPartLayout.add(
                saveProductionOperationsButton(),
                closeProductionOperationButton()
        );
        return topPartLayout;
    }

    private Button saveProductionOperationsButton () {
        Button saveButton = new Button("Продолжить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(event -> {
           if (technologicalOperationDtoBinder.validate().isOk()) {
               return;
           }
        });
        return saveButton;
    }

    private Button closeProductionOperationButton() {
        Button closeButton = new Button("Отмена");
        closeButton.addClickListener(c -> {
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });
        return closeButton;

    }

    private void createColumns() {
        VerticalLayout column = new VerticalLayout();
        TextField numberOfOperation = new TextField("Номер");
        DatePicker createOperationDate = new DatePicker("Дата");
        createOperationDate.setValue(LocalDate.now());
        Select<String> technologicalMap = new Select<>();
        technologicalMap.setLabel("Технологическая карта");
//        technologicalMap.setItemLabelGenerator(TechnologicalMapDto::getName);
//        technologicalMap.setItems(technologicalMapService.getAll());
        TextField volumeOfProduction = new TextField("Объем производства");
        ComboBox<WarehouseDto> warehouseForMaterials = new ComboBox<>();
        warehouseForMaterials.setLabel("Склад для материалов");
        warehouseForMaterials.setItems(warehouseService.getAll());
        warehouseForMaterials.setItemLabelGenerator(WarehouseDto::getName);
        warehouseForMaterials.addValueChangeListener(event -> {
            WarehouseDto warehouseDto = event.getValue();
            technologicalOperation.setWarehouseForMaterialsId(warehouseDto.getId());
            technologicalOperation.setWarehouseForMaterialsName(warehouseDto.getName());
        });
        ComboBox<WarehouseDto> warehouseForProducts = new ComboBox<>();
        warehouseForProducts.setLabel("Склад для продукции");
        warehouseForProducts.setItems(warehouseService.getAll());
        warehouseForProducts.setItemLabelGenerator(WarehouseDto::getName);
        warehouseForProducts.addValueChangeListener(event -> {
            WarehouseDto warehouseDto = event.getValue();
            technologicalOperation.setWarehouseForProductId(warehouseDto.getId());
            technologicalOperation.setWarehouseForProductName(warehouseDto.getName());
        });

        column.add(numberOfOperation, createOperationDate,
                technologicalMap, volumeOfProduction, warehouseForMaterials, warehouseForProducts);
        add(column);
    }

    private void showErrorNotification(String showText) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Text text = new Text(showText);
        layout.add(icon, text,closeBtn);
        layout.setMaxWidth("350px");

        notification.add(layout);
        notification.open();

    }


}
