package com.warehouse_accounting.components.payments.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.payments.PaymentsView;

import java.time.LocalDate;

@CssImport(value = "./css/receiptOrderAndIncomingPayForms.css")
public class CreateReceiptOrderForm extends VerticalLayout {
    private PaymentsView parent;
    private Tab paidDocuments = new Tab("Оплаченные документы");
    private Tab relatedDocuments = new Tab("Связанные документы");
    private VerticalLayout documentPage = new VerticalLayout();
    private TextField fieldReceiptOrderNumber;
    private DatePicker datePickerReceiptOrderNumber;
    private Checkbox checkboxProdused;
    private Select<String> formOrganization;
    private Select<String> formContrAgent;
    private Select<String> formContract;
    private Select<String> formProject;
    private Select<String> salesChannel;

    public CreateReceiptOrderForm(PaymentsView parent) {
        this.parent = parent;

        add(initTopButtons(), initInvoiceNumber(), initForms(), initTabs(), initTasksAndFiles());
    }

    private VerticalLayout initTopButtons() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> {
            //TODO реализовать сохранение готового ордера
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button close = new Button("Закрыть", e -> {
            //TODO добавить модальное окно с подтверждением
            parent.removeAll();
            parent.initView();
        });
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        MenuBar change = new MenuBar();
        change.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        MenuItem moveChange = change.addItem("Изменить");
        SubMenu subMenuChange = moveChange.getSubMenu();
        subMenuChange.addItem("Удалить");
        subMenuChange.addItem("Копировать");

        MenuBar create = new MenuBar();
        create.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        MenuItem moveCreate = create.addItem("Создать документ");
        SubMenu subMenuCreate = moveCreate.getSubMenu();
        subMenuCreate.addItem("Счет-фактура выданный");


        MenuBar print = new MenuBar();
        print.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        MenuItem itemPrint = print.addItem(new Icon(VaadinIcon.PRINT));
        itemPrint.add(new Text("Печать"));
        SubMenu subMenuPrint = itemPrint.getSubMenu();
        subMenuPrint.addItem("Приходный ордер");
        subMenuPrint.addItem("Настроить...");

        MenuBar send = new MenuBar();
        send.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        MenuItem itemSend = send.addItem(new Icon(VaadinIcon.SHARE));
        itemSend.add(new Text("Отправить"));
        SubMenu subMenuSend = itemSend.getSubMenu();
        subMenuSend.addItem("Приходный ордер");

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setAlignItems(FlexComponent.Alignment.CENTER);
        groupButton.add(save, close, change, create, print, send);

        verticalLayout.add(groupButton); // Добавить кнопки

        return verticalLayout;
    }

    // Метод создает строку с формами для счетов поставщика
    private HorizontalLayout initInvoiceNumber() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Text receiptOrderText = new Text("Приходный ордер №");

        FormLayout formReceiptOrderNumber = new FormLayout();
        fieldReceiptOrderNumber = new TextField();
        formReceiptOrderNumber.add(fieldReceiptOrderNumber);
        formReceiptOrderNumber.setWidth("4rem");

        Text byText = new Text("от");

        datePickerReceiptOrderNumber = new DatePicker();
        datePickerReceiptOrderNumber.setValue(LocalDate.now());

        MenuBar status = new MenuBar();
        status.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveStatus = status.addItem("Статус");
        SubMenu subMenuStatus = moveStatus.getSubMenu();
        subMenuStatus.addItem("Настроить...");

        checkboxProdused = new Checkbox();
        checkboxProdused.setLabel("Проведено");

        horizontalLayout.add(spaceGenerator(3),
                receiptOrderText,
                formReceiptOrderNumber,
                spaceGenerator(1),
                byText,
                datePickerReceiptOrderNumber);
        horizontalLayout.add(status, spaceGenerator(2), checkboxProdused);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return horizontalLayout;
    }

    private VerticalLayout initForms() {

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout formLayout1 = new FormLayout(); // Первая строка с формами

        //TODO сделать загрузку реальных данных из базы
        formOrganization = new Select<>();
        formOrganization.setItems("Рога и копыта", "ИП Аленушка", "АО Baba Yaga");
        formOrganization.setWidth("270px");

        //TODO сделать загрузку реальных данных из базы
        formContrAgent = new Select<>();
        formContrAgent.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");
        formContrAgent.setWidth("270px");

        formLayout1.addFormItem(formOrganization, "Организация");
        formLayout1.addFormItem(formContrAgent, "Контрагент");
        formLayout1.addClassName("formLayout1");

        FormLayout formLayout2 = new FormLayout(); // Вторая строка с формами

        formContract = new Select<>();
        formContract.setItems("Нет данных");
        formContract.setWidth("270px");

        NumberField sum = new NumberField();
        sum.setValue(0.00);

        formLayout2.addFormItem(formContract, "Договор");
        formLayout2.addFormItem(sum, "Сумма");
        formLayout2.addClassName("formLayout2");

        FormLayout formLayout3 = new FormLayout(); // Третья строка с формами

        formProject = new Select<>();
        formProject.setItems("Буратино", "Осьминожка", "Паутина");
        formProject.setWidth("270px");

        NumberField vat = new NumberField();
        vat.setValue(0.00);

        formLayout3.addFormItem(formProject, "Проект");
        formLayout3.addFormItem(vat, "Включая НДС");
        formLayout3.addClassName("formLayout3");


        FormLayout formLayout4 = new FormLayout(); // четвёртая строка с формами

        salesChannel = new Select<>();
        salesChannel.setWidth("270px");

        TextArea comment = new TextArea();
        comment.setWidth("270px");
        comment.addClassName("comment");

        formLayout4.addFormItem(salesChannel, "Канал продаж");
        formLayout4.addFormItem(comment, "Основание");
        formLayout4.addClassName("formLayout4");

        verticalLayout.add(formLayout1, formLayout2, formLayout3, formLayout4); // Добавить формы

        return verticalLayout;
    }


    // Метод создает таб: главная и связанные страницы
    private VerticalLayout initTabs() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Tabs tabs = new Tabs(paidDocuments, relatedDocuments);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        tabs.setSelectedTab(paidDocuments);
        setContent(paidDocuments);
        verticalLayout.add(tabs, documentPage);

        return verticalLayout;
    }

    private void setContent(Tab selectedTab) {
        if (selectedTab.equals(paidDocuments)) {
            documentPage.removeAll();
            documentPage.add(initPaidDocumentsTab());
        } else if (selectedTab.equals(relatedDocuments)) {
            documentPage.removeAll();
            documentPage.add(initRelatedDocumentsTab());
        }
    }

    private VerticalLayout initTasksAndFiles(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Accordion taskAccordion = new Accordion();
        Text taskName = new Text("Нет задач");
        VerticalLayout taskVerticalLayout = new VerticalLayout(taskName);
        taskVerticalLayout.setSpacing(false);
        taskVerticalLayout.setPadding(false);
        taskAccordion.add("Задачи", taskVerticalLayout);

        Button buttonTask = new Button("Задача", e -> {

        });
        buttonTask.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonTask.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout taskHorizontalLayout1 = new HorizontalLayout();
        taskHorizontalLayout1.add(taskAccordion, buttonTask);

        Accordion fileAccordion = new Accordion();
        VerticalLayout fileVerticalLayout = new VerticalLayout(new Text("Файлы"));
        fileVerticalLayout.setSpacing(false);
        fileVerticalLayout.setPadding(false);
        fileAccordion.add("Файлы", fileVerticalLayout);

        Button buttonFile = new Button("Файл", e -> {

        });
        buttonFile.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonFile.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout fileHorizontalLayout = new HorizontalLayout();
        fileHorizontalLayout.add(fileAccordion, buttonFile);

        verticalLayout.add(taskHorizontalLayout1, fileHorizontalLayout);
        return verticalLayout;
    }

    private VerticalLayout initPaidDocumentsTab() {
        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout buttons = new HorizontalLayout();

        Button linkPayment = new Button("Привязать платёж", event -> {
            //TODO реализовать функционал
        });
        linkPayment.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button redistributePaymentAmount = new Button("Перераспределить сумму платежа", e ->{
            //TODO реализовать функционал
        });
        redistributePaymentAmount.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttons.add(linkPayment, redistributePaymentAmount);

        //TODO вывод связанных документов
        verticalLayout.add(buttons);

        return verticalLayout;
    }

    private VerticalLayout initRelatedDocumentsTab() {
        VerticalLayout verticalLayout = new VerticalLayout();
        //TODO реализовать связанные документы
        return verticalLayout;
    }

    private HorizontalLayout spaceGenerator(int count) {
        HorizontalLayout space = new HorizontalLayout();
        for (int i = 0; i < count; i++) {
            space.add(new HorizontalLayout());
        }
        return space;
    }

}
