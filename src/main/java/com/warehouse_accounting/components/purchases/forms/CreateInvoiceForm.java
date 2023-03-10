package com.warehouse_accounting.components.purchases.forms;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.purchases.grids.SupplierInvoiceGridLayout;
import com.warehouse_accounting.components.sales.forms.order.types.DocumentCloseHandler;
import com.warehouse_accounting.models.dto.SupplierInvoiceDto;
import com.warehouse_accounting.services.impl.SupplierInvoiceServiceImpl;
import com.warehouse_accounting.services.interfaces.SupplierInvoiceService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

@CssImport(value = "./css/invoiceForm.css")
public class CreateInvoiceForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private Tab main = new Tab("Главная");
    private Tab related_documents = new Tab("Связанные документы");
    private VerticalLayout documentPage = new VerticalLayout();
    private Notification notification;

    private TextField fieldInvoiceNumber; //Input формы Номер счета поставщика
    private DatePicker datePickerInvoiceNumber; //Input datePicker Счет поставщика от
    private Checkbox checkboxProdused; //Input c чек-бокса Счет поставщика
    private Select<String> formOrganization; //Input формы Организация
    private Select<String> formWareHouse; //Input формы Склад
    private Select<String> formContrAgent; //Input формы Контрагент
    private Select<String> formContract;  //Input формы Договор
    private DatePicker datePickerPay; //Input datePicker Дата оплаты
    private Select<String> formProject; //Input Проект
    private TextField formIncomingNumber; //Input Входящий номер
    private DatePicker dateIncomingNumber; //Input даты входящего номера
    private Checkbox checkboxName; //Input c чек-бокса Наименование
    private TextField fieldAdd; //Input формы Добавить позицию
    private TextArea textArea; //Input формы Комментарий
    private Checkbox checkboxNDS; //Input c чек-бокса НДС
    private Checkbox checkboxOnNDS; //Input c чек-бокса Цена включает НДС
    private SupplierInvoiceGridLayout supplierInvoiceGridLayout = new SupplierInvoiceGridLayout();
    private  Button close;

    public CreateInvoiceForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        documentPage.add(mainPage());

        add(initTopButtons(), initInvoiceNumber(), initForms(), initTabs());
    }

    // Метод создает формы в центре страницы
    private VerticalLayout initForms() {

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout formLayout1 = new FormLayout(); // Первая строка с формами

        formOrganization = new Select<>();
        formOrganization.setItems("Рога и копыта", "ИП Аленушка", "АО Baba Yaga");
        formOrganization.setWidth("270px");

        formWareHouse = new Select<>();
        formWareHouse.setItems("Основной склад");
        formWareHouse.setWidth("270px");

        formLayout1.addFormItem(formOrganization, "Организация");
        formLayout1.addFormItem(formWareHouse, "Склад");
        formLayout1.addClassName("formLayout1");

        FormLayout formLayout2 = new FormLayout(); // Вторая строка с формами

        formContrAgent = new Select<>();
        formContrAgent.setItems("ООО \"Покупатель\"", "ООО \"Поставщик\"", "Розничный покупатель");
        formContrAgent.setWidth("270px");

        formContract = new Select<>();
        formContract.setItems("Нет данных");
        formContract.setWidth("270px");

        formLayout2.addFormItem(formContrAgent, "Котрагент");
        formLayout2.addFormItem(formContract, "Договор");
        formLayout2.addClassName("formLayout2");

        FormLayout formLayout3 = new FormLayout(); // Третья строка с формами

        datePickerPay = new DatePicker();
        formProject = new Select<>();
        formProject.setItems("Буратино", "Осьминожка", "Паутина");
        formProject.setWidth("270px");

        formLayout3.addFormItem(datePickerPay, "План. дата оплаты");
        formLayout3.addFormItem(formProject, "Проект");
        formLayout3.addClassName("formLayout3");

        HorizontalLayout horizontalLayout4 = new HorizontalLayout();  // Четвертая строка с формами

        formIncomingNumber = new TextField();
        formIncomingNumber.setWidth("148,5px");

        dateIncomingNumber = new DatePicker();

        horizontalLayout4.add(spaceGenerator(1));
        horizontalLayout4.add(new Text("Входящий номер"), formIncomingNumber, spaceGenerator(1),
                new Text("от"), dateIncomingNumber);
        horizontalLayout4.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout4.addClassName("horizontalLayout4");

        verticalLayout.add(formLayout1, formLayout2, formLayout3, horizontalLayout4); // Добавить формы

        return verticalLayout;
    }

    // Метод создает верхние кнопки
    private VerticalLayout initTopButtons() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> { //Создание и сохранение сущности
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:4446")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SupplierInvoiceService supplierInvoiceService = new SupplierInvoiceServiceImpl("/api/supplier_invoices", retrofit);
                SupplierInvoiceDto supplierInvoiceDto = new SupplierInvoiceDto();

                supplierInvoiceDto.setInvoiceNumber(fieldInvoiceNumber.getValue());
                if (datePickerInvoiceNumber.getValue() != null) {
                    supplierInvoiceDto.setDateInvoiceNumber(datePickerInvoiceNumber.getValue().toString());
                }
                supplierInvoiceDto.setCheckboxProd(checkboxProdused.getValue());
                supplierInvoiceDto.setOrganization(formOrganization.getValue());
                supplierInvoiceDto.setWarehouse(formWareHouse.getValue());
                supplierInvoiceDto.setContrAgent(formContrAgent.getValue());
                supplierInvoiceDto.setContract(formContract.getValue());
                if (datePickerPay.getValue() != null) {
                    supplierInvoiceDto.setDatePay(datePickerPay.getValue().toString());
                }
                supplierInvoiceDto.setProject(formProject.getValue());
                supplierInvoiceDto.setIncomingNumber(formIncomingNumber.getValue());
                if (dateIncomingNumber.getValue() != null) {
                    supplierInvoiceDto.setDateIncomingNumber(dateIncomingNumber.getValue().toString());
                }
                supplierInvoiceDto.setCheckboxName(checkboxName.getValue());
                supplierInvoiceDto.setCheckboxNDS(checkboxNDS.getValue());
                supplierInvoiceDto.setCheckboxOnNDS(checkboxOnNDS.getValue());
                supplierInvoiceDto.setAddPosition(fieldAdd.getValue());
                supplierInvoiceDto.setAddComment(textArea.getValue());

                supplierInvoiceService.create(supplierInvoiceDto);

                notification = Notification.show("Счет поставщика создан");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            } catch (Exception ex) {
                notification = Notification.show("Ошибка создания счета");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            }
            parentLayer.removeAll();
            parentLayer.add(returnLayer, supplierInvoiceGridLayout); // здесь статика была
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);

        close = new Button("Закрыть", e -> {});
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);

        MenuBar change = new MenuBar();
        change.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveChange = change.addItem("Изменить");
        SubMenu subMenuChange = moveChange.getSubMenu();
        subMenuChange.addItem("Удалить");
        subMenuChange.addItem("Копировать");

        MenuBar create = new MenuBar();
        create.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveCreate = create.addItem("Создать документ");
        SubMenu subMenuCreate = moveCreate.getSubMenu();
        subMenuCreate.addItem("Приемка");
        subMenuCreate.addItem("Исходящий платеж");
        subMenuCreate.addItem("Расходный ордер");

        MenuBar print = new MenuBar();
        print.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem itemPrint = print.addItem(new Icon(VaadinIcon.PRINT));
        itemPrint.add(new Text("Печать"));
        SubMenu subMenuPrint = itemPrint.getSubMenu();
        subMenuPrint.addItem("Счет поставщика");
        subMenuPrint.addItem("Ценник (70х49,5 мм)");
        subMenuPrint.addItem("Термоэтикетка (58х40 мм)");
        subMenuPrint.addItem("Комплект...");
        subMenuPrint.addItem("Настроить...");

        MenuBar send = new MenuBar();
        send.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem itemSend = send.addItem(new Icon(VaadinIcon.SHARE));
        itemSend.add(new Text("Отправить"));
        SubMenu subMenuSend = itemSend.getSubMenu();
        subMenuSend.addItem("Счет поставщика");
        subMenuSend.addItem("Комплект...");

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setAlignItems(FlexComponent.Alignment.CENTER);
        groupButton.add(save, close, change, create, print, send);

        verticalLayout.add(groupButton); // Добавить кнопки

        return verticalLayout;
    }

    // Метод создает строку с формами для счетов поставщика
    private HorizontalLayout initInvoiceNumber() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Text invoiceText = new Text("Счет поставщика №");

        FormLayout formInvoiceNumber = new FormLayout();
        fieldInvoiceNumber = new TextField();
        formInvoiceNumber.add(fieldInvoiceNumber);
        formInvoiceNumber.setWidth("4rem");

        Text byText = new Text("от");

        datePickerInvoiceNumber = new DatePicker();
        datePickerInvoiceNumber.setValue(LocalDate.now());

        MenuBar status = new MenuBar();
        status.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveStatus = status.addItem("Статус");
        SubMenu subMenuStatus = moveStatus.getSubMenu();
        subMenuStatus.addItem("Настроить...");

        checkboxProdused = new Checkbox();
        checkboxProdused.setLabel("Проведено");

        horizontalLayout.add(spaceGenerator(3), invoiceText, formInvoiceNumber, spaceGenerator(1), byText, datePickerInvoiceNumber);
        horizontalLayout.add(status, spaceGenerator(2), checkboxProdused);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return horizontalLayout;
    }

    // Метод создает таб: главная и связанные страницы
    private VerticalLayout initTabs() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Tabs tabs = new Tabs(main, related_documents);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        verticalLayout.add(tabs, documentPage);

        Accordion taskAccordion = new Accordion();
        Text taskName = new Text("Нет задач");
        VerticalLayout taskVerticalLayout = new VerticalLayout(taskName);
        taskVerticalLayout.setSpacing(false);
        taskVerticalLayout.setPadding(false);
        taskAccordion.add("Задачи", taskVerticalLayout);

        Button buttonTask = new Button("Задача", e -> {

        });
        buttonTask.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonTask.addThemeVariants(ButtonVariant.LUMO_SMALL);

        HorizontalLayout taskHorizontalLayout1 = new HorizontalLayout();
        taskHorizontalLayout1.add(taskAccordion, buttonTask);

        Accordion fileAccordion = new Accordion();
        VerticalLayout fileVerticalLayout = new VerticalLayout();
        fileVerticalLayout.setSpacing(false);
        fileVerticalLayout.setPadding(false);
        fileAccordion.add("Файлы", fileVerticalLayout);

        Button buttonFile = new Button("Файл", e -> {

        });
        buttonFile.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonFile.addThemeVariants(ButtonVariant.LUMO_SMALL);

        HorizontalLayout fileHorizontalLayout = new HorizontalLayout();
        fileHorizontalLayout.add(fileAccordion, buttonFile);

        Text textName = new Text("Наименование");
        Text textSize = new Text("Размер, МБ");
        Text textDate = new Text("Дата добавления");
        Text textEmployee = new Text("Сотрудник");

        HorizontalLayout horizontalLayoutName = new HorizontalLayout();
        horizontalLayoutName.add(textName, spaceGenerator(15), textSize, spaceGenerator(3),
                textDate, spaceGenerator(17), textEmployee);

        String line = new String();
        for (int i = 0; i < 350; i++) {
            line += "-";
        }
        Label textLine = new Label(line);

        HorizontalLayout horizontalLayoutLine = new HorizontalLayout();
        horizontalLayoutLine.add(textLine);

        verticalLayout.add(taskHorizontalLayout1, fileHorizontalLayout, horizontalLayoutName, horizontalLayoutLine);

        return verticalLayout;
    }

    // Метод устанавливает контент в выбранный таб
    private void setContent(Tab selectedTab) {
        if (selectedTab.equals(main)) {
            documentPage.removeAll();
            documentPage.add(mainPage());
        } else if (selectedTab.equals(related_documents)) {
            documentPage.removeAll();
        }
    }

    // Метод создает главную страницу в таб
    private VerticalLayout mainPage() {
        VerticalLayout verticalLayout = new VerticalLayout();

        checkboxName = new Checkbox();

        MenuBar name = new MenuBar();
        name.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveName = name.addItem("Наименование");
        SubMenu subMenuName = moveName.getSubMenu();
        subMenuName.addItem("Сортировать по наименованию");
        subMenuName.addItem("Сортировать по коду");
        Checkbox checkboxGroup = new Checkbox();
        checkboxGroup.setLabel("C учетом групп");
        subMenuName.addItem(checkboxGroup);

        MenuBar price = new MenuBar();
        price.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem movePrice = price.addItem("Цена");
        SubMenu subMenuPrice = movePrice.getSubMenu();
        subMenuPrice.addItem("Расценить");
        subMenuPrice.addItem("Сохранить цены");

        Button buttonDiscount = new Button("Скидка", e -> {

        });
        buttonDiscount.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);

        FormLayout formAdd = new FormLayout();
        fieldAdd = new TextField();
        fieldAdd.setPlaceholder("Добавить позицию - введите наименование, код, штрихкод или артикул");
        formAdd.setWidth("75rem");
        formAdd.add(fieldAdd);

        Button buttonAdd = new Button("Добавить из справочника", e -> {

        });
        buttonAdd.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);

        Button buttonCheck = new Button("Проверить комплектацию", e -> {

        });
        buttonCheck.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);

        MenuBar importMenuBar = new MenuBar();
        importMenuBar.addThemeVariants(MenuBarVariant.LUMO_CONTRAST, MenuBarVariant.LUMO_SMALL);
        MenuItem moveImport = importMenuBar.addItem("Импорт");
        SubMenu subMenuImport = moveImport.getSubMenu();
        subMenuImport.addItem("Импортировать (старый)");
        subMenuImport.addItem("Импортировать");

        textArea = new TextArea();
        textArea.setWidth("44rem");
        textArea.setPlaceholder("Комментарий");
        add(textArea);

        //Layers
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();  // Первая строка

        horizontalLayout1.add(checkboxName);
        horizontalLayout1.add(name);

        horizontalLayout1.add(spaceGenerator(25), new Text("Количество"), spaceGenerator(10), new Text("Доступно"));
        horizontalLayout1.add(price, spaceGenerator(5), new Text("НДС"), buttonDiscount,
                spaceGenerator(10), new Text("Сумма"));

        HorizontalLayout line1 = new HorizontalLayout();
        for (int i = 0; i < 343; i++) {
            line1.add("-");
        }
        horizontalLayout1.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(); // Вторая строка


        horizontalLayout2.add(formAdd, buttonAdd, buttonCheck, spaceGenerator(5), importMenuBar);

        HorizontalLayout line2 = new HorizontalLayout();
        for (int i = 0; i < 343; i++) {
            line2.add("-");
        }

        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        horizontalLayout3.add(textArea);

        VerticalLayout textVerticalLayout = new VerticalLayout();

        Label subTotal1 = new Label();
        subTotal1.add("Промежуточный итог:");
        Label dataSubTotal1 = new Label();
        dataSubTotal1.add("   0,00");

        checkboxNDS = new Checkbox();
        checkboxNDS.setLabel("НДС:");
        Label dataSubTotal2 = new Label();
        dataSubTotal2.add("   0,00");

        checkboxOnNDS = new Checkbox();
        checkboxOnNDS.setLabel("Цена включает НДС:");

        Label subTotal4 = new Label();
        subTotal4.add("Итого:");
        Label dataSubTotal4 = new Label();
        dataSubTotal4.add("   0,00");

        HorizontalLayout hLabel1 = new HorizontalLayout();
        HorizontalLayout hLabel2 = new HorizontalLayout();
        HorizontalLayout hLabel3 = new HorizontalLayout();
        HorizontalLayout hLabel4 = new HorizontalLayout();

        hLabel1.add(spaceGenerator(10), subTotal1, spaceGenerator(5), dataSubTotal1);
        hLabel2.add(spaceGenerator(10), checkboxNDS, spaceGenerator(10), dataSubTotal2);
        hLabel3.add(spaceGenerator(10), checkboxOnNDS);
        hLabel4.add(spaceGenerator(10), subTotal4, spaceGenerator(12), dataSubTotal4);

        textVerticalLayout.add(hLabel1, hLabel2, hLabel3, hLabel4);

        horizontalLayout3.add(textVerticalLayout);

        verticalLayout.add(horizontalLayout1, line1, horizontalLayout2, line2, horizontalLayout3);

        return verticalLayout;
    }

    private HorizontalLayout spaceGenerator(int count) {
        HorizontalLayout space = new HorizontalLayout();
        for (int i = 0; i < count; i++) {
            space.add(new HorizontalLayout());
        }
        return space;
    }

    public void setOnCloseHandler(DocumentCloseHandler closeHandler) {
        close.addClickListener(event -> closeHandler.close());
    }
}
