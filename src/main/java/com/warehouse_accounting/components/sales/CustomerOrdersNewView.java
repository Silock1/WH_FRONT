package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;

/*
    Заказы покупателей
    Не настроен внешний вид и таблица с товарами т.к. страница добавления товаров не реализована
 */

@Route(value = "customerorder/new", layout = AppView.class)
@PageTitle("Заказы покупателей")
public class CustomerOrdersNewView extends VerticalLayout {

    public CustomerOrdersNewView() {
        createButtons();
        createDateLine();
        createColumns();
    }

    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить");
        Button closeButton = new Button("Закрыть");
        Button editButton = new Button("Изменить");
        Button createDocButton = new Button("Создать документ");
        Button printButton = new Button("Печать");
        Button shareButton = new Button("Отправить");

        buttons.add(saveButton, closeButton, editButton, createDocButton, printButton, shareButton);
        add(buttons);
    }

    private void createDateLine() {
        HorizontalLayout line = new HorizontalLayout();
        Label numberText = new Label();
        numberText.setText("Заказ покупателя №");
        TextField numberField = new TextField();
        numberField.setPlaceholder("Номер");
        Label fromText = new Label();
        fromText.setText(" от ");
        DatePicker createOrderDate = new DatePicker();
        Button isPaid = new Button("Не оплачено");
        isPaid.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        isPaid.setEnabled(false);
        Button requestPaid = new Button("Запросить оплату");
        Checkbox accessCheckbox = new Checkbox();
        accessCheckbox.setLabel("Проведено");
        Checkbox reservCheckbox = new Checkbox();
        reservCheckbox.setLabel("Резерв");

        line.add(numberText, numberField, fromText, createOrderDate, isPaid, requestPaid, accessCheckbox, reservCheckbox);
        add(line);
    }

    private void createColumns() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout firstColumn = new VerticalLayout();
        VerticalLayout secondColumn = new VerticalLayout();
        VerticalLayout thirdColumn = new VerticalLayout();

        HorizontalLayout dateLayout = new HorizontalLayout();
        Label dateLabel = new Label("План. дата отгрузки");
        DatePicker shippingDate = new DatePicker();
        dateLayout.add(dateLabel, shippingDate);

        firstColumn.add(createSubColumn("Организация", "company"),
                        createSubColumn("Контрагент", "contractor"),
                        dateLayout,
                        createSubColumn("Канал продаж", "salesChannel"));
        secondColumn.add(createSubColumn("Склад", "warehouse"),
                         createSubColumn("Договор", "contract"),
                         createSubColumn("Проект", "project"));
        thirdColumn.add(createSubColumn("Адрес доставки", "address"),
                        createSubColumn("Комментарий", "comment"));
        horizontalLayout.add(firstColumn, secondColumn, thirdColumn);
        add(horizontalLayout);
    }

    private HorizontalLayout createSubColumn(String title, String className) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName(className);
        Label label = new Label(title);
        TextField textField = new TextField();
        horizontalLayout.add(label, textField);
        return horizontalLayout;
    }
}
