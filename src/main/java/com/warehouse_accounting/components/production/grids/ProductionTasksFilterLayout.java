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

    Select<String> show = new Select<>();
    Select<String> contragent = new Select<>();
    Select<String> groupContragent = new Select<>();
    Select<String> organization = new Select<>();
    Select<String> status = new Select<>();
    Select<String> sent = new Select<>();
    Select<String> printed = new Select<>();
    Select<String> owner_employee = new Select<>();
    Select<String> owner_department = new Select<>();
    Select<String> access = new Select<>();

    DatePicker when_start_date = new DatePicker("Когда изменён: от ");
    DatePicker when_end_date = new DatePicker("до");

    Select<String> who = new Select<>();

    public ProductionTasksFilterLayout() {
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        find.setWidth("100px");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        clear.setWidth("100px");

        show.setLabel("Показывать");
        show.setItems("Только обычные", "Только архивные", "Все");
        show.setWidth("300px");

        contragent.setLabel("• Контрагент");
        contragent.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");
        contragent.setWidth("300px");

        groupContragent.setLabel("• Контрагент");
        groupContragent.add("нет данных");
        groupContragent.setWidth("300px");

        organization.setLabel("• Организация");
        organization.setWidth("300px");

        status.setLabel("• Статус");
        status.add("нет данных");
        status.setWidth("300px");

        sent.setLabel("Отправлено");
        sent.setItems("Да", "Нет");
        sent.setWidth("300px");

        printed.setLabel("Напечатано");
        printed.setItems("Да", "Нет");
        printed.setWidth("300px");

        owner_employee.setLabel("• Владелец-сотрудник");
        owner_employee.setWidth("300px");

        owner_department.setLabel("• Владелец-отдел");
        owner_department.setItems("Основной");
        owner_department.setWidth("300px");

        access.setLabel("Общий доступ");
        access.setItems("Да", "Нет");
        access.setWidth("300px");

        who.setLabel("• Владелец-отдел");
        who.setWidth("300px");

        HorizontalLayout layout_one = new HorizontalLayout(find, clear, settingFilter, bookmarks, period_start_date, period_end_date, show, contragent);
        HorizontalLayout layout_two = new HorizontalLayout(groupContragent, organization, status, sent);
        HorizontalLayout layout_three = new HorizontalLayout(printed, owner_employee, owner_department, access);
        HorizontalLayout layout_four = new HorizontalLayout(when_start_date, when_end_date, who);

        add(layout_one, layout_two, layout_three, layout_four);

        setVisible(false);

    }
}
