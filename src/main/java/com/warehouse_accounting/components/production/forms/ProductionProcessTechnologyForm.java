package com.warehouse_accounting.components.production.forms;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.production.ProductionProcessTechnology;


@Route(value = "test")
public class ProductionProcessTechnologyForm extends VerticalLayout {
    private final ProductionProcessTechnology productionProcessTechnology;
    private final Div returnDiv;
    private final Div newMainContent = new Div();


    public ProductionProcessTechnologyForm(ProductionProcessTechnology productionProcessTechnology) {
        this.productionProcessTechnology = productionProcessTechnology;
        this.returnDiv = productionProcessTechnology.getMainContent();

        newMainContent.add(createTopGroupElements());
        productionProcessTechnology.setMainContent(newMainContent);
    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.add(
                saveProductionTechnologyButton(),
                closeProductionTechnologyButton()
        );
        return topPartLayout;
    }

    private Button saveProductionTechnologyButton() {
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(c -> {

        });
        return saveButton;
    }

    private Button closeProductionTechnologyButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> {

        });
        return closeButton;

    }


}

