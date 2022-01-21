package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ProductionTasksFilterLayout extends VerticalLayout {
    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));

    DatePicker period_start_date = new DatePicker("Период: от ");
    DatePicker period_end_date = new DatePicker("до");

    // План дата производства
    DatePicker plan_date_start_production = new DatePicker("План. дата производства");
    DatePicker plan_date_end_production = new DatePicker("до");

    // Начало производства
    DatePicker start_production = new DatePicker("Начало производства");
    DatePicker end_production = new DatePicker("до");

    // Завершение производства
    DatePicker completion_start_production = new DatePicker("Завершение производства");
    DatePicker completion_end_production = new DatePicker("до");

    DatePicker when_start_date = new DatePicker("Когда изменён");
    DatePicker when_end_date = new DatePicker("до");


    Select<String> warehouse = new Select<>(); // Склад
    Select<String> product_warehouse = new Select<>();
    Select<String> carried_out = new Select<>(); // Проведено
    Select<String> sent = new Select<>();
    Select<String> organization = new Select<>();
    Select<String> status = new Select<>();
    Select<String> printed = new Select<>();
    Select<String> owner_employee = new Select<>();
    Select<String> owner_department = new Select<>();
    Select<String> access = new Select<>();
    Select<String> who_changed = new Select<>();

    public ProductionTasksFilterLayout() {
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        find.setWidth("100px");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        clear.setWidth("100px");

        warehouse.setLabel("• Склад для материалов");
        warehouse.setItems("Центральный", "Речная", "Новомосковск");
        warehouse.setWidth("200px");

        product_warehouse.setLabel("• Склад продукции");
        product_warehouse.setItems("Заморозка", "Открытая площадка", "Подземный склад");
        product_warehouse.setWidth("200px");

        organization.setLabel("• Организация");
        organization.setItems("Ромашка ООО", "СкладПартс ООО");
        organization.setWidth("300px");

        status.setLabel("• Статус");
        status.add("нет данных");
        status.setWidth("300px");

        carried_out.setLabel("• Проведено");
        carried_out.setItems("Да", "Нет");
        carried_out.setWidth("300px");

        printed.setLabel("Напечатано");
        printed.setItems("Да", "Нет");
        printed.setWidth("300px");

        sent.setLabel("• Отправлено");
        sent.setItems("Да", "Нет");
        sent.setWidth("300px");

        owner_employee.setLabel("• Владелец-сотрудник");
        owner_employee.setItems("Главный менеджер", "Старший продавец");
        owner_employee.setWidth("300px");

        owner_department.setLabel("• Владелец-отдел");
        owner_department.setItems("Основной", "Распеределение");
        owner_department.setWidth("300px");

        access.setLabel("Общий доступ");
        access.setItems("Да", "Нет");
        access.setWidth("300px");

        who_changed.setLabel("• Кто изменил");
        who_changed.setWidth("300px");

        HorizontalLayout layout_one = new HorizontalLayout(find, clear, settingFilter, bookmarks, period_start_date,
                period_end_date, plan_date_start_production, plan_date_end_production, start_production, end_production,
                completion_start_production, completion_end_production, warehouse);
        HorizontalLayout layout_two = new HorizontalLayout(product_warehouse, organization, status, carried_out,
                printed, sent);
        HorizontalLayout layout_three = new HorizontalLayout( owner_employee, owner_department, access, when_start_date,
                when_end_date, who_changed);

        add(layout_one, layout_two, layout_three);

        setVisible(false);

    }
}
