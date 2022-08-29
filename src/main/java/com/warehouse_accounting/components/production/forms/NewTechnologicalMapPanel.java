package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.production.TechnologicalMap;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import lombok.extern.log4j.Log4j2;
import org.vaadin.olli.FileDownloadWrapper;

import java.time.LocalDate;

@Log4j2
@SpringComponent
@UIScope
@Tag("div")

public class NewTechnologicalMapPanel {
    private final NewGoodsDialog newGoodsDialog;

    private HorizontalLayout horizontalToolPanelLayout;

    public NewTechnologicalMapPanel(NewGoodsDialog newGoodsDialog) {
        this.newGoodsDialog = newGoodsDialog;
    }


    public HorizontalLayout getLayout(TechnologicalMap technologicalMap) {
        horizontalToolPanelLayout = new HorizontalLayout();
        configToolPanel(technologicalMap);
        return horizontalToolPanelLayout;
    }


//    private void createDateLine() {
//        HorizontalLayout line = new HorizontalLayout();
//        Label numberText = new Label();
//        numberText.setText("Заказ покупателя №");
//        TextField numberField = new TextField();
//        numberField.setPlaceholder("Номер");
//        Label fromText = new Label();
//        fromText.setText(" от ");
//        DatePicker createOrderDate = new DatePicker();
//        Button isPaid = new Button("Не оплачено");
//        isPaid.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        isPaid.setEnabled(false);
//        Button requestPaid = new Button("Запросить оплату");
//        Checkbox accessCheckbox = new Checkbox();
//        accessCheckbox.setLabel("Проведено");
//        Checkbox reservCheckbox = new Checkbox();
//        reservCheckbox.setLabel("Резерв");
//
//        line.add(numberText, numberField, fromText, createOrderDate, isPaid, requestPaid, accessCheckbox, reservCheckbox);
//        add(line);
//    }

    private void configToolPanel(TechnologicalMap technologicalMap) {
        Button save = new Button("Сохранить", buttonClickEvent -> {
            checkRequired();
            //TODO SAVE
            log.info("save method");
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button close = new Button("Закрыть");
        close.getStyle().set("margin-inline-end", "auto");

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
            Notification.show("Элементы, перемещенные в архив, не отображаются\n" +
                    "\n" +
                    "в справочниках и отчетах. Архив позволяет скрывать\n" +
                    "\n" +
                    "неактуальные элементы, не удаляя их.", 5000, Notification.Position.TOP_START);
        });
        Button archiveButton = new Button("Поместить в архив");
        archiveButton.getElement().setAttribute("disabled", true);

        MenuBar menuBar = new MenuBar();
        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Удалить");

        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        delete.getElement().setAttribute("disabled", true);
        MenuItem recover = changeSubMenu.addItem("Копировать");
        recover.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        recover.getElement().setAttribute("disable", true);


        MenuItem createDocument = menuBar.addItem("Создать документ");
        createDocument.add(new Icon(VaadinIcon.CARET_DOWN));
        SubMenu createSubMenu = createDocument.getSubMenu();
        MenuItem productionOperation = createSubMenu.addItem("Технологическая операция");
        productionOperation.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem productionOrders = createSubMenu.addItem("Заказ на производство");
        productionOrders.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });


        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

//модальное окно
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        //
        SubMenu printSubMenu = print.getSubMenu();
        MenuItem recycleBinList = printSubMenu.addItem("Технологическая карта");
        recycleBinList.addClickListener(event -> {
            dialog.open(); //
            //TODO повод поработать этот функционал
        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

            //TODO повод поработать этот функционал
        });

        //TODO owner?
        //  Button owner = new Button("Owner",new Icon(VaadinIcon.CARET_DOWN));
        //   owner.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        //  owner.getElement().setText();

        Button addTextTechnologicalMapButton = new Button("возвратТехкарты", new Icon(VaadinIcon.MINUS));
        addTextTechnologicalMapButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addTextTechnologicalMapButton.addClickListener(buttonClickEvent -> {
            technologicalMap.removeAll();
            technologicalMap.init();
        });


        horizontalToolPanelLayout.add(save, close, helpButton, archiveButton, menuBar, addTextTechnologicalMapButton);

    }

    private void checkRequired() {

    }


    //модальное окно
    private static VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Создание печатной формы");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");


        Select<String> selectForm = new Select<>("Открыть в браузере",
                "Скачать в формате EXEL", "Скачать в формате PDF");


        VerticalLayout fieldLayout = new VerticalLayout(selectForm);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);


        Button cancelButton = new Button("Закрыть", e -> dialog.close());


        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");

        return dialogLayout;
    }
}
