package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.grids.ProductionTasksFilterLayout;



@SpringComponent
@UIScope
public class ProductionTasks extends VerticalLayout {

    private final ProductionTasksFilterLayout productionTasksFilterLayout;

    public ProductionTasks(ProductionTasksFilterLayout productionTasksFilterLayout) {
        this.productionTasksFilterLayout = productionTasksFilterLayout;
        add(getGroupButton(), productionTasksFilterLayout);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Производственное задание позволяет выстроить процесс изготовления" +
                        "продукции по техкартам и рассчитать расходы. Тут же можно отмечать выполнение производственных этапов" +
                        "и контролировать результаты."+
                "\n" +
                "Читать инструкцию: Расширенный учет производственных операций" + "Видео: Расширенный способ", 3000, Notification.Position.MIDDLE));


        return null;
    }


}
