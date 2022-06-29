package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import lombok.extern.log4j.Log4j2;

@UIScope
@Log4j2
public class ProductionOperationsForm extends VerticalLayout {

    private ProductionOperations productionOperations;

    private final Div returnDiv;

    private Binder <ProductionOperationsDto> productionOperationsDtoBinder = new Binder<>(ProductionOperationsDto.class);
    private ProductionOperationsDto productionOperationsDto;

    public ProductionOperationsForm(Div returnDiv) {
        this.returnDiv = returnDiv;
    }


    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setAlignItems(Alignment.CENTER);

        return topLayout;
    }

    private Button saveProductionOperations () {
        Button saveButton = new Button();
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        return saveButton;
    }
}
