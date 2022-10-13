package com.warehouse_accounting.components.retail.forms.bonus_transaction;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

public class FilterForm extends VerticalLayout {

    public FilterForm() {
        setVisible(false);
        add(lineOne(),lineTwo());

    }

    private HorizontalLayout lineOne() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(

                new Button("Найти"),
                new Button("Очистить"),
                new Button(new Icon(VaadinIcon.BOOKMARK)),
                new Button(new Icon(VaadinIcon.COG)),
                new DatePicker("Период"),
                new DatePicker("до"),
                new ComboBox<>("Тип операции"),
                new ComboBox<>("Бонусная программа"),
                new IntegerField("Бонусные балл от"),
                new IntegerField("до"),
                new ComboBox<>("Статус")

        );

        return layout;
    }

    private HorizontalLayout lineTwo() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(

                new ComboBox<>("Контрагент"),
                new ComboBox<>("Группа контрагентов"),
                new ComboBox<>("Владелец-сотрудник"),
                new ComboBox<>("Владелец-отдел"),
                new ComboBox<>("Общий доступ")

        );

        return layout;
    }
}
