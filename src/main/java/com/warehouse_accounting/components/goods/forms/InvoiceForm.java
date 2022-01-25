package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InvoiceForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private Tab main = new Tab("Главная");
    private Tab related_documents = new Tab("Связанные документы");
    private VerticalLayout documentPage = new VerticalLayout();

    public InvoiceForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        documentPage.add(mainPage());

        add(initTopButtons(),initInvoiceNumber(),initForms(),initTabs());
    }
    // Метод создает формы в центре страницы
    private VerticalLayout initForms() {
        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout formGroups1 = new HorizontalLayout(); // Первая строка с формами

        List<String> listOrganization = new ArrayList<>();
        listOrganization.add("Рога и копыта");
        listOrganization.add("ИП Аленушка");
        ComboBox<String> formOrganization = new ComboBox<>();
        formOrganization.setItems(listOrganization);

        List<String> listWareHouse = new ArrayList<>();
        listWareHouse.add("Основной склад");
        ComboBox<String> formWareHouse= new ComboBox<>();
        formWareHouse.setItems(listWareHouse);

        formGroups1.add(spaceGenerator(1));
        formGroups1.add(new Text("Организация"),spaceGenerator(2),formOrganization,spaceGenerator(9),
                new Text("Склад"),spaceGenerator(1),formWareHouse);
        formGroups1.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout formGroups2 = new HorizontalLayout(); // Вторая строка с формами

        List<String> listContrAgent = new ArrayList<>();
        listContrAgent.add("ООО Покупатель");
        listContrAgent.add("ООО Поставщик");
        listContrAgent.add("Розничный покупатель");
        ComboBox<String> formContrAgent = new ComboBox<>();
        formContrAgent.setItems(listContrAgent);

        ComboBox<String> formContract = new ComboBox<>();

        formGroups2.add(spaceGenerator(1));
        formGroups2.add(new Text("Контрагент"),spaceGenerator(3),formContrAgent,spaceGenerator(9),
                new Text("Договор"),formContract);
        formGroups2.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout formGroups3 = new HorizontalLayout(); // Третья строка с формами

        DatePicker datePickerPay = new DatePicker();

        ComboBox<String> formProgect= new ComboBox<>();

        formGroups3.add(spaceGenerator(1));
        formGroups3.add(new Text("Дата оплаты"),spaceGenerator(2),datePickerPay,spaceGenerator(9),
                new Text("Проект"),spaceGenerator(1),formProgect);
        formGroups3.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout formGroups4 = new HorizontalLayout(); // Четвертая строка с формами

        ComboBox<String> formIncomingNumber = new ComboBox<>();

        DatePicker dateIncomingNumber = new DatePicker();
        dateIncomingNumber.setWidth("23rem");

        formGroups4.add(spaceGenerator(1));
        formGroups4.add(new Text("Входящий номер"),formIncomingNumber,spaceGenerator(1),
                new Text("от"),dateIncomingNumber);
        formGroups4.setAlignItems(FlexComponent.Alignment.CENTER);

        verticalLayout.add(formGroups1,formGroups2,formGroups3,formGroups4); // Добавить формы

        return verticalLayout;
    }
    // Метод создает верхние кнопки
    private VerticalLayout initTopButtons(){
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> {
            //Создание и сохранение сущности
            parentLayer.add(returnLayer);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_SMALL);

        Button close = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_SMALL);

        MenuBar change = new MenuBar();
        change.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveChange = change.addItem("Изменить");
        SubMenu subMenuChange = moveChange.getSubMenu();
        subMenuChange.addItem("Удалить");
        subMenuChange.addItem("Копировать");

        MenuBar create = new MenuBar();
        create.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveCreate = create.addItem("Создать документ");
        SubMenu subMenuCreate = moveCreate.getSubMenu();
        subMenuCreate.addItem("Приемка");
        subMenuCreate.addItem("Исходящий платеж");
        subMenuCreate.addItem("Расходный ордер");

        MenuBar print = new MenuBar();
        print.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem itemPrint = print.addItem(new Icon(VaadinIcon.PRINT));
        itemPrint.add(new Text("Печать"));
        SubMenu subMenuPrint = itemPrint.getSubMenu();
        subMenuPrint.addItem("Счет поставщика");
        subMenuPrint.addItem("Ценник (70х49,5 мм)");
        subMenuPrint.addItem("Термоэтикетка (58х40 мм)");
        subMenuPrint.addItem("Комплект...");
        subMenuPrint.addItem("Настроить...");

        MenuBar send = new MenuBar();
        send.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem itemSend = send.addItem(new Icon(VaadinIcon.SHARE));
        itemSend.add(new Text("Отправить"));
        SubMenu subMenuSend = itemSend.getSubMenu();
        subMenuSend.addItem("Счет поставщика");
        subMenuSend.addItem("Комплект...");

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setAlignItems(FlexComponent.Alignment.CENTER);
        groupButton.add(save,close,change,create,print,send);

        verticalLayout.add(groupButton); // Добавить кнопки

        return verticalLayout;
    }
    // Метод создает строку с формами для счетов поставщика
    private HorizontalLayout initInvoiceNumber(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Text invoiceText = new Text("Счет поставщика №");

        FormLayout formInvoiceNumber = new FormLayout();
        TextField fieldInvoiceNumber = new TextField();
        formInvoiceNumber.add(fieldInvoiceNumber);
        formInvoiceNumber.setWidth("4rem");

        Text byText = new Text("от");

        DatePicker datePicker = new DatePicker();
        datePicker.setLocale(Locale.ENGLISH);

        MenuBar status = new MenuBar();
        status.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveStatus = status.addItem("Статус");
        SubMenu subMenuStatus = moveStatus.getSubMenu();
        subMenuStatus.addItem("Настроить...");

        Checkbox checkboxProdused = new Checkbox();
        checkboxProdused.setLabel("Произведено");

        horizontalLayout.add(spaceGenerator(3),invoiceText,formInvoiceNumber,spaceGenerator(1),byText,datePicker);
        horizontalLayout.add(status,spaceGenerator(2),checkboxProdused);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return horizontalLayout;
    }

    // Метод создает таб: главная и связанные страницы
    private VerticalLayout initTabs(){
        VerticalLayout verticalLayout = new VerticalLayout();

        Tabs tabs = new Tabs(main, related_documents);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        verticalLayout.add(tabs,documentPage);

        Accordion taskAccordion = new Accordion();
        Text taskName = new Text("Нет задач");
        VerticalLayout taskVerticalLayout = new VerticalLayout(taskName);
        taskVerticalLayout.setSpacing(false);
        taskVerticalLayout.setPadding(false);
        taskAccordion.add("Задачи", taskVerticalLayout);

        Button buttonTask = new Button("Задача", e -> {
            parentLayer.add(returnLayer);
        });
        buttonTask.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonTask.addThemeVariants(ButtonVariant.LUMO_SMALL);

        HorizontalLayout taskHorizontalLayout1 = new HorizontalLayout();
        taskHorizontalLayout1.add(taskAccordion,buttonTask);

        Accordion fileAccordion = new Accordion();
        VerticalLayout fileVerticalLayout = new VerticalLayout();
        fileVerticalLayout.setSpacing(false);
        fileVerticalLayout.setPadding(false);
        fileAccordion.add("Файлы", fileVerticalLayout);

        Button buttonFile = new Button("Файл", e -> {
            parentLayer.add(returnLayer);
        });
        buttonFile.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        buttonFile.addThemeVariants(ButtonVariant.LUMO_SMALL);

        HorizontalLayout fileHorizontalLayout = new HorizontalLayout();
        fileHorizontalLayout.add(fileAccordion,buttonFile);

        Text textName = new Text("Наименование");
        Text textSize = new Text("Размер, МБ");
        Text textDate = new Text("Дата добавления");
        Text textEmployee = new Text("Сотрудник");

        HorizontalLayout horizontalLayoutName = new HorizontalLayout();
        horizontalLayoutName.add(textName,spaceGenerator(15),textSize,spaceGenerator(3),
                textDate,spaceGenerator(17),textEmployee);

        String line = new String();
        for (int i=0; i<350; i++){
            line+="-";
        }
        Label textLine = new Label(line);

        HorizontalLayout horizontalLayoutLine = new HorizontalLayout();
        horizontalLayoutLine.add(textLine);

        verticalLayout.add(taskHorizontalLayout1,fileHorizontalLayout,horizontalLayoutName,horizontalLayoutLine);

        return verticalLayout;
    }

    // Метод устанавливает контент в выбранный таб
    private void setContent(Tab selectedTab) {
        if(selectedTab.equals(main)){
            documentPage.removeAll();
            documentPage.add(mainPage());
        }else if(selectedTab.equals(related_documents)){
            documentPage.removeAll();
        }
    }

    // Метод создает главную страницу в таб
    private VerticalLayout mainPage(){
        VerticalLayout verticalLayout = new VerticalLayout();

        Checkbox checkbox = new Checkbox();

        MenuBar name = new MenuBar();
        name.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveName = name.addItem("Наименование");
        SubMenu subMenuName = moveName.getSubMenu();
        subMenuName.addItem("Сортировать по наименованию");
        subMenuName.addItem("Сортировать по коду");
        Checkbox checkboxGroup = new Checkbox();
        checkboxGroup.setLabel("C учетом групп");
        subMenuName.addItem(checkboxGroup);

        MenuBar price = new MenuBar();
        price.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem movePrice = price.addItem("Цена");
        SubMenu subMenuPrice = movePrice.getSubMenu();
        subMenuPrice.addItem("Расценить");
        subMenuPrice.addItem("Сохранить цены");

        Button buttonDiscount = new Button("Скидка", e -> {
            parentLayer.add(returnLayer);
        });
        buttonDiscount.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_SMALL);

        FormLayout formAdd = new FormLayout();
        TextField fieldAdd = new TextField();
        fieldAdd.setPlaceholder("Добавить позицию - введите наименование, код, штрихкод или артикул");
        formAdd.setWidth("75rem");
        formAdd.add(fieldAdd);

        Button buttonAdd = new Button("Добавить из справочника", e -> {
            parentLayer.add(returnLayer);
        });
        buttonAdd.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_SMALL);

        Button buttonCheck = new Button("Проверить комплектацию", e -> {
            parentLayer.add(returnLayer);
        });
        buttonCheck.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_SMALL);

        MenuBar importMenuBar = new MenuBar();
        importMenuBar.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveImport = importMenuBar.addItem("Импорт");
        SubMenu subMenuImport = moveImport.getSubMenu();
        subMenuImport.addItem("Импортировать (старый)");
        subMenuImport.addItem("Импортировать");

        TextArea textArea = new TextArea();
        textArea.setWidth("44rem");
        textArea.setPlaceholder("Комметарий");
        add(textArea);

        //Layers
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();  // Первая строка

        horizontalLayout1.add(checkbox);
        horizontalLayout1.add(name);

        horizontalLayout1.add(spaceGenerator(25),new Text("Количество"),spaceGenerator(10),new Text("Доступно"));
        horizontalLayout1.add(price,spaceGenerator(5),new Text("НДС"),buttonDiscount,
                spaceGenerator(10),new Text("Сумма"));

        HorizontalLayout line1 = new HorizontalLayout();
        for(int i=0; i<343; i++){
           line1.add("-");
        }
        horizontalLayout1.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(); // Вторая строка


        horizontalLayout2.add(formAdd,buttonAdd,buttonCheck,spaceGenerator(5),importMenuBar);

        HorizontalLayout line2 = new HorizontalLayout();
        for(int i=0; i<343; i++){
            line2.add("-");
        }

        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        horizontalLayout3.add(textArea);

        VerticalLayout textVerticalLayout = new VerticalLayout();

        Label subTotal1 = new Label();
        subTotal1.add("Промежуточный итог:");
        Label dataSubTotal1 = new Label();
        dataSubTotal1.add("   0,00");

        Checkbox checkboxNDS = new Checkbox();
        checkboxNDS.setLabel("НДС:");
        Label dataSubTotal2 = new Label();
        dataSubTotal2.add("   0,00");

        Checkbox checkboxOnNDS = new Checkbox();
        checkboxOnNDS.setLabel("Цена включает НДС:");

        Label subTotal4 = new Label();
        subTotal4.add("Итого:");
        Label dataSubTotal4 = new Label();
        dataSubTotal4.add("   0,00");

        HorizontalLayout hLabel1 = new HorizontalLayout();
        HorizontalLayout hLabel2 = new HorizontalLayout();
        HorizontalLayout hLabel3 = new HorizontalLayout();
        HorizontalLayout hLabel4 = new HorizontalLayout();

        hLabel1.add(spaceGenerator(10),subTotal1,spaceGenerator(5),dataSubTotal1);
        hLabel2.add(spaceGenerator(10),checkboxNDS,spaceGenerator(10),dataSubTotal2);
        hLabel3.add(spaceGenerator(10),checkboxOnNDS);
        hLabel4.add(spaceGenerator(10),subTotal4,spaceGenerator(12),dataSubTotal4);

        textVerticalLayout.add(hLabel1,hLabel2,hLabel3,hLabel4);

        horizontalLayout3.add(textVerticalLayout);

        verticalLayout.add(horizontalLayout1,line1,horizontalLayout2,line2,horizontalLayout3);

        return verticalLayout;
    }

    private HorizontalLayout spaceGenerator(int count){
        HorizontalLayout space = new HorizontalLayout();
        for(int i=0; i<count; i++){
            space.add(new HorizontalLayout());
        }
        return space;
    }
}
