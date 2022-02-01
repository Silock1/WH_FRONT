package com.warehouse_accounting.components.sales.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    public SalesShipmentsFilter() {
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        find.setWidth("100px");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        clear.setWidth("100px");
        periodStart.setWidth("300px");
        periodEnd.setWidth("300px");
        consignee.setItems("Ромашка ООО", "Основной ООО");
        consignee.setWidth("300px");
        payment.setItems("Оплачено", "Частично оплачено", "Не оплачено");
        payment.setWidth("300px");
        itemOrGroup.setItems("Товар");
        itemOrGroup.setWidth("300px");


        HorizontalLayout horizontalLayoutOne = new HorizontalLayout(find, clear, bookmarks, settingButton, periodStart,
                periodEnd, consignee, payment, itemOrGroup);
        HorizontalLayout horizontalLayoutTwo = new HorizontalLayout();

        add(horizontalLayoutOne);

        setVisible(false);
    }



}
