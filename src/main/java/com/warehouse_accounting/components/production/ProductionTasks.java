package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

        Text productionTasks = new Text("Производственные задания");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            System.out.println("перезагрузка");
        });
        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button exercise = new Button("Задание", image);
        exercise.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> {
            if (productionTasksFilterLayout.isVisible()) {
                productionTasksFilterLayout.setVisible(false);
            } else {
                productionTasksFilterLayout.setVisible(true);
            }
        });

        horizontalLayout.add(helpButton, productionTasks, refreshButton, exercise, filter);
        return horizontalLayout;
    }


}
