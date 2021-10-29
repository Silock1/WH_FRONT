package com.warehouse_accounting.components.contractors.grids;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ContractorsFilterLayout extends HorizontalLayout implements KeyNotifier {

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));

    DatePicker startDate = new DatePicker("Период: от ");
    DatePicker endDate = new DatePicker("до");

    Select<String> employeeWhoChangedName = new Select<>();
    Select<String> contragent = new Select<>();
    Select<String> type = new Select<>();
    Select<String> employee = new Select<>();

    public ContractorsFilterLayout() {
        contragent.setLabel("Контрагент");
        contragent.setItems("ООО Покупатель", "ООО Поставщик", "Розничный покупатель");
        type.setLabel("Тип");
        type.setItems("Входящий звонок", "Исходящий звонок");
        employee.setLabel("Сотрудник");
        employeeWhoChangedName.setLabel("Кто изменил");

        add(find, clear, settingFilter, bookmarks, startDate, endDate,
                contragent, type, employee, employeeWhoChangedName);

        setVisible(false);
        setSizeFull();
    }
}
