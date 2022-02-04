package com.warehouse_accounting.components.sales.filter;

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
public class SalesShipmentsFilter extends VerticalLayout {
    Button find = new Button("Найти");
    Button clear = new Button("Очистить");
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
    Button settingButton = new Button(new Icon(VaadinIcon.COG));

    DatePicker periodStart = new DatePicker("Период");
    DatePicker periodEnd = new DatePicker("до");
    ComboBox<String> consignee = new ComboBox<>("Грузополучатель");
    ComboBox<String> payment = new ComboBox<>("Оплата");
    ComboBox<String> itemOrGroup = new ComboBox<>("Товар или группа");

    /*
    Вторая строка
     */
    ComboBox<String> typeReturn = new ComboBox<>("Тип возврата");
    ComboBox<String> warehouseCombo = new ComboBox<>("Склад");
    ComboBox<String> projectCombo = new ComboBox<>("Проект");
    ComboBox<String> contractorsCombo = new ComboBox<>("Контаргент");
    ComboBox<String> groupContractors = new ComboBox<>("Группа контрагента");
    /*
    Третья строка
     */
    TextField counterPartyAccount = new TextField("Счёт контрагента");
    ComboBox<String> treatyContractors = new ComboBox<>("Договор");
    ComboBox<String> counterPartyOwner = new ComboBox<>("Владелец контрагента");
    ComboBox<String> organization = new ComboBox<>("Организация");
    TextField organizationAccount = new TextField("Счет организации");

    /*
    Четвертая строка
     */
    ComboBox<String> statusCombo = new ComboBox<>("Статус");
    ComboBox<String> carriedOutCombo = new ComboBox<>("Проведено");
    ComboBox<String> printedCombo = new ComboBox<>("Напечатано");
    ComboBox<String> sentCombo = new ComboBox<>("Отправлено");
    ComboBox<String> salesChannel = new ComboBox<>("Канал продаж");

    /*
    Пятая строка
     */
    TextField deliveryAddress = new TextField("Адрес доставки");
    TextField shippingAddressComment = new TextField("Комментарий к адресу доставки");
    ComboBox<String> ownerEmployee = new ComboBox<>("Владелец-сотрудник");
    ComboBox<String> departmentOwner = new ComboBox<>("Владелец-отдел");
    ComboBox<String> generalAccess = new ComboBox<>("Общий доступ");

    /*
    Шестая строка
     */
    DatePicker whenChangedStart = new DatePicker("Когда изменен: от");
    DatePicker whenChangedEnd = new DatePicker("до");
    ComboBox<String> whoChanged = new ComboBox<>("Кто изменил");

    public SalesShipmentsFilter() {
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        consignee.setItems("Ромашка ООО", "Основной ООО");
        payment.setItems("Оплачено", "Частично оплачено", "Не оплачено");
        itemOrGroup.setItems("Товар");


        HorizontalLayout horizontalLayoutOne = new HorizontalLayout(find, clear, bookmarks, settingButton, periodStart,
                periodEnd, consignee, payment, itemOrGroup);
        HorizontalLayout horizontalLayoutTwo = new HorizontalLayout(typeReturn, warehouseCombo, projectCombo,
                contractorsCombo, groupContractors);
        HorizontalLayout horizontalLayoutThree = new HorizontalLayout(counterPartyAccount, treatyContractors,
                counterPartyOwner, organization, organizationAccount);
        HorizontalLayout horizontalLayoutFour = new HorizontalLayout(statusCombo, carriedOutCombo, printedCombo,sentCombo,
                salesChannel);
        HorizontalLayout horizontalLayoutFive = new HorizontalLayout(deliveryAddress, shippingAddressComment,ownerEmployee,
                departmentOwner, generalAccess);
        HorizontalLayout horizontalLayoutSix = new HorizontalLayout(whenChangedStart, whenChangedEnd, whoChanged);
        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour, horizontalLayoutFive,
                horizontalLayoutSix);

        setVisible(false);
    }

}
