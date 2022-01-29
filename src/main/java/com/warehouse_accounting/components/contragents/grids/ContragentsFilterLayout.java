package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.awt.*;

@SpringComponent
@UIScope
public class ContragentsFilterLayout extends VerticalLayout {
    Button find = new Button("Найти");
    Button clear = new Button("Очистить");
    Button settingButton = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));

    DatePicker create = new DatePicker("Создан. Период: от");
    DatePicker andCreate = new DatePicker("до");

    DatePicker dateEvent = new DatePicker("Дата события. от");
    DatePicker dateEventEnd = new DatePicker("до");

    TextField textEventLast = new TextField("Текст события (последнее)");
    TextField nameText = new TextField("Наименование");
    TextField fullName = new TextField("Полное наименование");

/*
Вторая строка
 */
    TextField averageCheck = new TextField("Средний чек: от");
    TextField averageCheckEnd = new TextField("до");

/*
Третья строка
 */
    TextField ownerEmployee = new TextField("Владелец сотрудник");


    public ContragentsFilterLayout() {

        HorizontalLayout horizontalLayout_one = new HorizontalLayout(find, clear, settingButton, bookmarks, create,
                andCreate, dateEvent, dateEventEnd,textEventLast, nameText, fullName);
        HorizontalLayout horizontalLayout_two = new HorizontalLayout(averageCheck, averageCheckEnd);
        HorizontalLayout horizontalLayout_tree = new HorizontalLayout(ownerEmployee);

        add(horizontalLayout_one, horizontalLayout_two, horizontalLayout_tree);


    }

}
