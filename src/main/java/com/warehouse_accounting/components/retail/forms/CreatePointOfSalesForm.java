package com.warehouse_accounting.components.retail.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.retail.grids.PointOfSalesGridLayout;
import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.services.impl.PointOfSalesServiceImpl;
import com.warehouse_accounting.services.interfaces.PointOfSalesService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePointOfSalesForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private Notification notification;
    private Checkbox checkboxEnabled;
    private TextField namePointSales;
    private Select<String> formOrganization;
    private Select<String> formCashiers;
    private TextArea textAreaDescription;
    private TextArea textAreaAddress;
    private TextArea textAreaComment;
    private TextField prefixSalesNumber;
    private Select<String> defaultTaxationSystem;
    private Select<String> ordersTaxationSystem;
    private Label helpButtonCashiers;
    private Checkbox checkboxCashiersSelection;
    private Span prices;
    private Select<String> formPrices;
    private Checkbox checkboxPermission;
    private RadioButtonGroup<String> radioGroup;
    private Span sales;
    private Checkbox checkboxPermissionDiscount;
    private TextField maxDiscount;
    private Span products;
    private Select<String> formWarehouse;
    private Checkbox checkboxAccountingForBalances;
    private Select<String> formUnloadOnlyProductsFromGroups;
    private Span marking;
    private Checkbox checkboxForCloudPoints;
    private RadioButtonGroup<String> radioGroup2;
    private Span buyers;
    private Select<String> formAddNewCustomersToGroups;
    private Select<String> formUnloadOnlyBuyersFromGroups;
    private Span cashReceipts;
    private RadioButtonGroup<String> radioGroup3;
    private RadioButtonGroup<String> radioGroup4;
    private RadioButtonGroup<String> radioGroup5;
    private Checkbox checkboxMandatoryToFormCashReceipts;
    private Select<String> formPattern;
    private Span paymentMethods;
    private Span paymentByCard;
    private Select<String> formAcquiringBank;
    private TextField commissionOfTheAcquiringBank;
    private Span paymentByQRcode;
    private Checkbox checkboxEnablePaymentByQR;
    private Select<String> formAcquiringBankQR;
    private TextField CommissionOfTheAcquiringBankQR;
    private Span change;
    private Checkbox checkboxCreateAnIncomingPayment;
    private Checkbox checkboxCreateAPCO;
    private Span refunds;
    private Span issuingOrders;
    private Select<String> formChangeTheStatus;
    private Select<String> formUploadOnlyOrdersWithStatuses;
    private Select<String> formCreateOrdersWithTheStatus;
    private Span prepayment;

    boolean isMain = true;

    public CreatePointOfSalesForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        add(initTopButtons(), initForms());
    }

    private VerticalLayout initTopButtons() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> { //Создание и сохранение сущности
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:4446")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                PointOfSalesService pointOfSalesService = new PointOfSalesServiceImpl("/api/retail", retrofit);
                PointOfSalesDto pointOfSalesDto = new PointOfSalesDto();

                pointOfSalesDto.setCheckboxEnabled(checkboxEnabled.getValue());
                pointOfSalesDto.setName(namePointSales.getValue());
                pointOfSalesDto.setCashiers(formCashiers.getValue());
                pointOfSalesService.create(pointOfSalesDto);

                notification = Notification.show("Точка продажи создана");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            } catch (Exception ex) {
                notification = Notification.show("Ошибка создания точки продажи");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            }
            parentLayer.removeAll();
            parentLayer.add(returnLayer, PointOfSalesGridLayout.initPointOfSalesGrid());
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);

        Button close = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer, PointOfSalesGridLayout.initPointOfSalesGrid());
        });
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);


        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setAlignItems(Alignment.CENTER);
        groupButton.add(save, close);

        verticalLayout.add(groupButton); // Добавить кнопки

        return verticalLayout;
    }

    private VerticalLayout initForms() {

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout formLayout1 = new FormLayout(); // Первая строка с формами

        checkboxEnabled = new Checkbox();

        textAreaDescription = new TextArea();
        textAreaDescription.setWidthFull();
        textAreaAddress = new TextArea();
        textAreaAddress.setWidthFull();
        textAreaComment = new TextArea();
        textAreaComment.setWidthFull();

        namePointSales = new TextField();

        formOrganization = new Select<>();
        formOrganization.setItems("Организация1");

        prefixSalesNumber = new TextField();

        defaultTaxationSystem = new Select<>();
        defaultTaxationSystem.setItems("ОСН", "УСН. Доход", "УСН. Доход-Расход", "ЕСХН", "ЕНВД", "Патент");
        defaultTaxationSystem.setPlaceholder("Совпадает с ККТ");

        ordersTaxationSystem = new Select<>();
        ordersTaxationSystem.setItems("ОСН", "УСН. Доход", "УСН. Доход-Расход", "ЕСХН", "ЕНВД", "Патент");
        ordersTaxationSystem.setPlaceholder("По умолчанию");

        formLayout1.addFormItem(checkboxEnabled, "Включена");
        formLayout1.addFormItem(textAreaDescription, "Описание");
        formLayout1.addFormItem(namePointSales, "Наименование");
        formLayout1.addFormItem(textAreaAddress, "Адрес");
        formLayout1.addFormItem(formOrganization, "Организация");
        formLayout1.addFormItem(textAreaComment, "Комментарий к адресу");
        formLayout1.addFormItem(prefixSalesNumber, "Префикс номера продаж");
        formLayout1.addFormItem(defaultTaxationSystem, "Система налогообложения по умолчанию");
        formLayout1.addFormItem(ordersTaxationSystem, "Система налогообложения для заказов");

        formLayout1.getStyle().set("margin-left", "var(--lumo-space-xs)");
        formLayout1.getElement().getStyle().set("padding", "1px");

        formLayout1.addClassName("formLayout1");

        FormLayout formLayout2 = new FormLayout(); // Вертикальная строка с формами

        formLayout2.setMaxWidth("37%");
        formLayout2.getStyle().set("margin-left", "var(--lumo-space-xs)");
        formLayout2.getElement().getStyle().set("padding", "1px");


        helpButtonCashiers = new Label("Кассиры");
        helpButtonCashiers.getStyle().set("color", "red");
        helpButtonCashiers.getStyle().set("font-weight", "bold");

        checkboxCashiersSelection = new Checkbox();

        formCashiers = new Select<>();
        formCashiers.setItems("Галя");

        prices = new Span("Цены");
        prices.getStyle().set("color", "red");
        prices.getStyle().set("font-weight", "bold");

        formPrices = new Select<>();
        formPrices.setItems("Цена продажи");

        checkboxPermission = new Checkbox();

        radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItems("Не учитывать МРЦ", "Продавать по МРЦ, указанной на пачке ",
                "Запрещать продажу, если цена продажи не совпадает с МРЦ");

        sales = new Span("Продажи");
        sales.getStyle().set("color", "red");
        sales.getStyle().set("font-weight", "bold");

        checkboxPermissionDiscount = new Checkbox();

        maxDiscount = new TextField();

        products = new Span("Товары");
        products.getStyle().set("color", "red");
        products.getStyle().set("font-weight", "bold");

        formWarehouse = new Select<>();
        formWarehouse.setItems("Основной склад");

        checkboxAccountingForBalances = new Checkbox();
        Checkbox checkboxAccountingForReserves = new Checkbox();
        Checkbox checkboxAllowToCreateProducts = new Checkbox();

        formUnloadOnlyProductsFromGroups = new Select<>();
        formUnloadOnlyProductsFromGroups.setItems("Группа товаров");

        marking = new Span("Маркировка");
        marking.getStyle().set("color", "red");
        marking.getStyle().set("font-weight", "bold");

        checkboxForCloudPoints = new Checkbox();

        radioGroup2 = new RadioButtonGroup<>();
        radioGroup2.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup2.setItems("Только с правильными кодами маркировки",
                "С правильными кодами и те, которые не удалось проверить ",
                "Все - независимо от результатов проверки кодов маркировки");

        buyers = new Span("Покупатели");
        buyers.getStyle().set("color", "red");
        buyers.getStyle().set("font-weight", "bold");

        formAddNewCustomersToGroups = new Select<>();
        formAddNewCustomersToGroups.setItems("1");

        formUnloadOnlyBuyersFromGroups = new Select<>();
        formUnloadOnlyBuyersFromGroups.setItems("1");

        cashReceipts = new Span("Кассовые чеки");
        cashReceipts.getStyle().set("color", "red");
        cashReceipts.getStyle().set("font-weight", "bold");

        radioGroup3 = new RadioButtonGroup<>();
        radioGroup3.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup3.setItems("Стандартное",
                "Стандартное с обработкой облачных операций",
                "Облачное");

        radioGroup4 = new RadioButtonGroup<>();
        radioGroup4.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup4.setItems("Любая",
                "Касса из того же отдела",
                "Только выбранные кассы");

        radioGroup5 = new RadioButtonGroup<>();
        radioGroup5.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup5.setItems("Не отправлять",
                "E-mail",
                "Телефон");

        checkboxMandatoryToFormCashReceipts = new Checkbox();

        formPattern = new Select<>();
        formPattern.setItems("нет данных");

        paymentMethods = new Span("Способы оплаты");
        paymentMethods.getStyle().set("color", "red");
        paymentMethods.getStyle().set("font-weight", "bold");

        paymentByCard = new Span("Оплата картой");
        paymentByCard.getStyle().set("color", "red");
        paymentByCard.getStyle().set("font-weight", "bold");

        formAcquiringBank = new Select<>();
        formAcquiringBank.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");

        commissionOfTheAcquiringBank = new TextField();

        paymentByQRcode = new Span("Оплата по QR-коду");
        paymentByQRcode.getStyle().set("color", "red");
        paymentByQRcode.getStyle().set("font-weight", "bold");

        checkboxEnablePaymentByQR = new Checkbox();

        formAcquiringBankQR = new Select<>();
        formAcquiringBankQR.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");

        CommissionOfTheAcquiringBankQR = new TextField();

        change = new Span("Смена");
        change.getStyle().set("color", "red");
        change.getStyle().set("font-weight", "bold");

        checkboxCreateAnIncomingPayment = new Checkbox();
        checkboxCreateAPCO = new Checkbox();

        refunds = new Span("Возвраты");
        refunds.getStyle().set("color", "red");
        refunds.getStyle().set("font-weight", "bold");

        Checkbox checkboxAllowRefundsInClosedShifts = new Checkbox();
        Checkbox checkboxAllowRefundsWithoutReason = new Checkbox();

        issuingOrders = new Span("Выдача заказов");
        issuingOrders.getStyle().set("color", "red");
        issuingOrders.getStyle().set("font-weight", "bold");

        Checkbox checkboxIssuingOrders = new Checkbox();

        formChangeTheStatus = new Select<>();
        formChangeTheStatus.setItems("");

        formUploadOnlyOrdersWithStatuses = new Select<>();
        formUploadOnlyOrdersWithStatuses.setItems("");

        formCreateOrdersWithTheStatus = new Select<>();
        formCreateOrdersWithTheStatus.setItems("");

        prepayment = new Span("Предоплата");
        prepayment.getStyle().set("color", "red");
        prepayment.getStyle().set("font-weight", "bold");

        Checkbox checkboxReserveGoods = new Checkbox();

        formLayout2.add(helpButtonCashiers);
        formLayout2.addFormItem(checkboxCashiersSelection, "Выбор кассира");
        formLayout2.addFormItem(formCashiers, "Кассиры");
        formLayout2.add(prices);
        formLayout2.addFormItem(formPrices, "Цены");
        formLayout2.addFormItem(checkboxPermission, "Разрешить продажу по свободной цене");
        formLayout2.addFormItem(radioGroup, "Учет максимальной розничной цены для табака");
        formLayout2.add(sales);
        formLayout2.addFormItem(checkboxPermissionDiscount, "Разрешить скидки");
        formLayout2.addFormItem(maxDiscount, "Максимальная скидка, %");
        formLayout2.add(products);
        formLayout2.addFormItem(formWarehouse, "Склад");
        formLayout2.addFormItem(checkboxAccountingForBalances, "Учет остатков");
        formLayout2.addFormItem(checkboxAccountingForReserves, "Учет резервов");
        formLayout2.addFormItem(checkboxAllowToCreateProducts, "Разрешить создавать товары");
        formLayout2.addFormItem(formUnloadOnlyProductsFromGroups, "Выгружать только товары из групп");
        formLayout2.add(marking);
        formLayout2.addFormItem(checkboxForCloudPoints, "Для облачных точек — до продажи отправлять коды маркировки на проверку на точку с ККТ");
        formLayout2.addFormItem(radioGroup2, "Продавать маркированные товары");
        formLayout2.add(buyers);
        formLayout2.addFormItem(formAddNewCustomersToGroups, "Добавлять новых покупателей в группы:");
        formLayout2.addFormItem(formUnloadOnlyBuyersFromGroups, "Выгружать только покупателей из групп:");
        formLayout2.add(cashReceipts);
        formLayout2.addFormItem(radioGroup3, "Формирование чеков");
        formLayout2.addFormItem(radioGroup4, "Касса для формирования облачных чеков");
        formLayout2.addFormItem(radioGroup5, "Основной контакт для отправки электронного чека");
        formLayout2.addFormItem(checkboxMandatoryToFormCashReceipts, "Обязательно формировать кассовые чеки");
        formLayout2.addFormItem(formPattern, "Шаблон");
        formLayout2.add(paymentMethods);
        formLayout2.add(paymentByCard);
        formLayout2.addFormItem(formAcquiringBank, "Банк-эквайер");
        formLayout2.addFormItem(commissionOfTheAcquiringBank, "Комиссия банка-эквайера, %");
        formLayout2.add(paymentByQRcode);
        formLayout2.addFormItem(checkboxEnablePaymentByQR, "Банк-эквайер");
        formLayout2.addFormItem(formAcquiringBank, "Включить оплату по QR");
        formLayout2.addFormItem(formAcquiringBankQR, "Банк-эквайер");
        formLayout2.addFormItem(CommissionOfTheAcquiringBankQR, "Комиссия банка-эквайера, %");
        formLayout2.add(change);
        formLayout2.addFormItem(checkboxCreateAnIncomingPayment, "Создавать входящий платеж при закрытии смены");
        formLayout2.addFormItem(checkboxCreateAPCO, "Создавать ПКО при закрытии смены");
        formLayout2.add(refunds);
        formLayout2.addFormItem(checkboxAllowRefundsInClosedShifts, "Разрешить возвраты в закрытых сменах");
        formLayout2.addFormItem(checkboxAllowRefundsWithoutReason, "Разрешить возвраты без основания");
        formLayout2.add(issuingOrders);
        formLayout2.addFormItem(checkboxIssuingOrders, "Выдача заказов");
        formLayout2.addFormItem(formChangeTheStatus, "Менять статус проданных заказов");
        formLayout2.addFormItem(formUploadOnlyOrdersWithStatuses, "Выгружать только заказы со статусами");
        formLayout2.addFormItem(formCreateOrdersWithTheStatus, "Создавать заказы со статусом");
        formLayout2.add(prepayment);
        formLayout2.addFormItem(checkboxReserveGoods, "Резервировать товары, за которые внесена предоплата");


        verticalLayout.add(formLayout1, formLayout2); // Добавить формы


        return verticalLayout;
    }

}
