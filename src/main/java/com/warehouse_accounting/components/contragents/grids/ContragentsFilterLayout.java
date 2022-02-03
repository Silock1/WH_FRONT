package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

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
    TextField codeField = new TextField("Код");
    TextField innField = new TextField("ИНН");
    TextField telephoneField = new TextField("Телефон");
    TextField emailField = new TextField("E-mail");
    TextField addressField = new TextField("Адрес");
    ComboBox<String> showField = new ComboBox<>("Показывать");


/*
Третья строка
 */
    TextField discountCardField = new TextField("Дисконтная карта");
    ComboBox<String> priceField = new ComboBox<>("Цены");
    ComboBox<String> typeEmployee = new ComboBox<>("Тип контрагента");
    ComboBox<String> groupEmployee = new ComboBox<>("Группа контрагента");
    TextField kppEmployee = new TextField("КПП контрагента");
    ComboBox<String> statusCombo = new ComboBox<>("Статус");

    /*
    Четвертая строка
     */
    ComboBox<String> ownerEmployeeCombo = new ComboBox<>("Владелец-сотрудник");
    ComboBox<String> departmentOwner = new ComboBox<>("Владелец-отдел");
    ComboBox<String> generalAccessCombo = new ComboBox<>("Общий доступ");
    DatePicker whenChangedStart = new DatePicker("Когда изменен: от");
    DatePicker getWhenChangedEnd = new DatePicker("до");
    ComboBox<String> whoChanged = new ComboBox<>("Кто изменил");




    public ContragentsFilterLayout() {

        HorizontalLayout horizontalLayout_one = new HorizontalLayout(find, clear, settingButton, bookmarks, create,
                andCreate, dateEvent, dateEventEnd,textEventLast, nameText, fullName);
        HorizontalLayout horizontalLayout_two = new HorizontalLayout(codeField, innField, telephoneField,
                emailField, addressField, showField);
        HorizontalLayout horizontalLayout_tree = new HorizontalLayout(discountCardField, priceField, typeEmployee,
                groupEmployee, kppEmployee, statusCombo);
        HorizontalLayout horizontalLayout_four = new HorizontalLayout(ownerEmployeeCombo, departmentOwner,
                generalAccessCombo, whenChangedStart, getWhenChangedEnd, whoChanged);
        add(horizontalLayout_one, horizontalLayout_two, horizontalLayout_tree, horizontalLayout_four);


    }

}
