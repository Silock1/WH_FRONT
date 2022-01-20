package com.warehouse_accounting.components.sales.grids;

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
public class CustomerGoodsToRealizeFilter extends VerticalLayout {

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));

    DatePicker period_start_date = new DatePicker("На дату ");

    Select<String> noReport = new Select<>();
    Select<String> goodsOrGroup = new Select<>();
    Select<String> contract = new Select<>();
    Select<String> organization = new Select<>();
    Select<String> contragent = new Select<>();



    public CustomerGoodsToRealizeFilter() {
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        find.setWidth("100px");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        clear.setWidth("100px");

        period_start_date.setWidth("300px");

        noReport.setLabel("Не отчитались");
        noReport.setItems("Любое", "Больше нуля", "Ноль");
        noReport.setWidth("300px");

        goodsOrGroup.setLabel("Товар или група");
        goodsOrGroup.setWidth("300px");

        contragent.setLabel("Контрагент");
        contragent.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");
        contragent.setWidth("300px");

        contract.setLabel("Договор");
        contract.setItems("Нет данных");
        contract.setWidth("300px");

        organization.setLabel("Организация");
        organization.setWidth("300px");





        HorizontalLayout layout_one = new HorizontalLayout(find, clear, settingFilter, bookmarks, period_start_date, noReport, goodsOrGroup);
        HorizontalLayout layout_two = new HorizontalLayout(contragent, contract, organization);

        add(layout_one, layout_two);

        setVisible(false);
    }

}
