package com.warehouse_accounting.components.user;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;


import com.warehouse_accounting.components.util.QuestionButton;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.NotificationsDto;
import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.PositionService;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


@PageTitle("Настройки пользователя")
@Route(value = "profile/settings", layout = AppView.class)
public class UserSettingsView extends VerticalLayout {


    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout mainLeftLayout = new VerticalLayout();
    VerticalLayout mainRightLayout = new VerticalLayout();
    VerticalLayout verticalLayout2 = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    HorizontalLayout mainHorisontalLayout = new HorizontalLayout();

    Button createButton = new Button("Сохранить");
    Button closeButton = new Button("Закрыть");
    Label change = new Label("Изменения:");
    Button changeButtonPass = new Button((new Icon(VaadinIcon.MINUS)));
    HorizontalLayout passLayout = new HorizontalLayout();
    Button buttonPass = new Button("Изменить пароль");


    EmployeeService employeeService;
    PositionService positionService;
    EmployeeDto employeeDto;
    PositionDto positionDto;
    TextField firstName = new TextField();
    TextField middleName = new TextField();
    TextField lastName = new TextField();
    TextField email = new TextField();
    TextField phone = new TextField();
    TextField inn = new TextField();
    TextField position = new TextField();


    public UserSettingsView(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
        dsUserSettingsView();
    }

    public void dsUserSettingsView() {

        setSizeFull();
        horizontalLayout.add(createButton, closeButton, change, changeButtonPass);
        verticalLayout.add(textFieldrow("Имя", firstName), textFieldrow("Отчество", middleName), textFieldrow("Фамилия", lastName),
                textFieldrow("E-mail", email), textFieldrow("Телефон", phone), textFieldrow("Должность", position),
                textFieldrow("ИНН", inn));
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        changeButtonPass.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        verticalLayout2.add(imagePortrait());
        passLayout.add(buttonPass);
        mainLeftLayout.add(horizontalLayout, verticalLayout);


        mainRightLayout.add(textDefault(), textDefault("Организация", buttonPen()),
                textDefault("Склад", buttonPen()),

                textDefault("Покупатель", buttonPlus()), textDefault("Поставщик", buttonPlus()),
                textDefault("Проект", buttonPlus()), settings(), usSettings("Язык"),
                usSettings("Печать документов"),
                lablesRow("Число дополнительных полей в строке"), usSettings("Стартовый экран"),
                forCheckbox("Обновлять отчеты автоматически"), areaRow("Подпись в отправляемых письмах"),
                forCheckbox("Использовать новый редактор контрагентов"),
                forCheckbox("Использовать новый редактор товаров"),
                forCheckbox("Использовать одну кнопку для выбора из справочника товаров"), notification(),
                createNotificationsGrid());
        mainHorisontalLayout.setWidth("70%");
        mainLeftLayout.add(namer(), passLayout, horizontalLayout, verticalLayout, verticalLayout2);
        mainRightLayout.add();
        mainHorisontalLayout.add(mainLeftLayout, mainRightLayout);
        add(horizontalLayout, mainHorisontalLayout);
        verticalLayout.getStyle().set("margin-left", "var(--lumo-space-xl)");
        verticalLayout.getElement().getStyle().set("padding", "40px");
        activatedCreateButton();
        fillFields();
    }


    private HorizontalLayout namer() {

        HorizontalLayout hLayout = new HorizontalLayout();
        Label loginLabel = new Label("Логин");
        hLayout.add(loginLabel);
        return hLayout;
    }

    private Button buttonPen() {

        Button penButton = new Button(new Icon(VaadinIcon.PENCIL));

        penButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return penButton;

    }

    private Button buttonPlus() {

        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));

        plusButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return plusButton;

    }

//    private Component addLoginInfo() {
//        TextField login = new TextField();
//        Label label = new Label("Логин");
//        login.setValue(employeeService.getPrincipal().getEmail());
//        login.setReadOnly(true);
//        HorizontalLayout addEmployeePasswordAddLayout = new HorizontalLayout(label, login);
//        return addEmployeePasswordAddLayout;
//    }
//    нужна реализация security


    private HorizontalLayout lablesRow(String rowLabel) {

        HorizontalLayout hLayout = new HorizontalLayout();
        Label label = new Label(rowLabel);
        TextField textField = new TextField();
        hLayout.add(label, textField);
        label.getStyle().set("width", "90px");
        textField.getStyle().set("width", "190px");
        return hLayout;
    }

    private HorizontalLayout textFieldrow(String lable, TextField field) {
        HorizontalLayout hLayout = new HorizontalLayout();
        Label label = new Label(lable);
        hLayout.add(label, field);
        label.getStyle().set("width", "90px");
        field.getStyle().set("width", "190px");
        return hLayout;
    }

    //удалить в конце
    private HorizontalLayout areaRow(String rowArea) {

        HorizontalLayout areaLayout = new HorizontalLayout();
        Label label = new Label(rowArea);
        TextArea area = new TextArea();
        areaLayout.add(label, area);
        label.getStyle().set("width", "90px");
        area.getStyle().set("width", "190px");
        return areaLayout;
    }


    private HorizontalLayout textDefault(String someBox, Button someButton) {

        HorizontalLayout WH = new HorizontalLayout();
        Label someLabel = new Label(someBox);
        ComboBox<String> ComboBox = new ComboBox<>();

        WH.add(someLabel, ComboBox, someButton);
        someLabel.getStyle().set("width", "90px");
        ComboBox.getStyle().set("width", "190px");
        return WH;
    }

    private void fillFields() {
//        employeeDto = employeeService.getById(employeeService.getPrincipal().getId()); раскоментировать когда будет security, а строчку ниже удалить
        employeeDto = employeeService.getById(1L);
        positionDto = employeeDto.getPosition();
        firstName.setValue(employeeDto.getFirstName());
        middleName.setValue(employeeDto.getMiddleName());
        lastName.setValue(employeeDto.getMiddleName());
        email.setValue(employeeDto.getEmail());
        phone.setValue(employeeDto.getPhone());
        inn.setValue(employeeDto.getInn());
        position.setValue(positionDto.getName());
    }

    public void activatedCreateButton() {
        createButton.addClickListener(event -> {

            employeeDto.setFirstName(firstName.getValue());
            employeeDto.setMiddleName(middleName.getValue());
            employeeDto.setLastName(lastName.getValue());
            employeeDto.setEmail(email.getValue());
            employeeDto.setPhone(phone.getValue());
            employeeDto.setInn(inn.getValue());

            positionDto.setName(position.getValue());
            employeeDto.setPosition(positionDto);

            if (positionService.getAll().stream()
                    .filter(positionDto -> positionDto.getName().toLowerCase().equals(position.getValue().toLowerCase()))
                    .findFirst().isPresent()) {
                positionDto = positionService.getAll().stream()
                        .filter(positionDto -> positionDto.getName().toLowerCase().equals(position.getValue().toLowerCase()))
                        .findFirst().get();
            } else {
                positionService.create(new PositionDto(null, position.getValue(), null));
                positionDto = positionService.getAll().stream()
                        .filter(positionDto -> positionDto.getName().toLowerCase().equals(position.getValue().toLowerCase()))
                        .findFirst().get();
                positionDto.setSortNumber(positionDto.getId().toString());
                positionService.update(positionDto);
            }
            employeeDto.setPosition(positionDto);

            System.out.println(employeeService.getById(1L));
            System.out.println(positionDto);
            employeeService.update(employeeDto);
        });
    }

    private VerticalLayout imagePortrait() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Do something with the file data
            // processFile(inputStream, fileName);
        });

        VerticalLayout Portrait = new VerticalLayout();
        Label image = new Label("Изображение");
        image.getStyle().set("color", "red");
        image.getStyle().set("font-weight", "bold");
        Button setImage = new Button("+ Изображение");
        Portrait.add(image, setImage);
        return Portrait;
    }


    private HorizontalLayout textDefault() {
        HorizontalLayout defLayout = new HorizontalLayout();
        Label def = new Label("Значения по умолчанию");
        def.getStyle().set("color", "red");
        def.getStyle().set("font-weight", "bold");
        defLayout.add(def);
        return defLayout;
    }

    private HorizontalLayout settings() {
        HorizontalLayout settLayout = new HorizontalLayout();
        Label setting = new Label("Настройки");
        setting.getStyle().set("color", "red");
        setting.getStyle().set("font-weight", "bold");
        settLayout.add(setting);
        return settLayout;
    }


    private HorizontalLayout usSettings(String someBox) {

        HorizontalLayout usLayout = new HorizontalLayout();
        Label usLabel = new Label(someBox);
        ComboBox<String> usBox = new ComboBox<>();
        usLayout.add(usLabel, usBox);
        usLabel.getStyle().set("width", "90px");
        usBox.getStyle().set("width", "190px");
        return usLayout;
    }

    private HorizontalLayout forCheckbox(String text) {
        HorizontalLayout checkboxLayout = new HorizontalLayout();
        Label checkboxLabel = new Label(text);
        Checkbox checkbox = new Checkbox();
        checkboxLayout.add(checkboxLabel, checkbox);
        checkboxLabel.getStyle().set("width", "90px");
        checkbox.getStyle().set("width", "190px");
        return checkboxLayout;

    }

    private HorizontalLayout notification() {
        HorizontalLayout notifLayout = new HorizontalLayout();
        Label notifications = new Label("Уведомления");
        notifications.getStyle().set("color", "red");
        notifications.getStyle().set("font-weight", "bold");
        notifications.getStyle().set("margin-top", "8px");
        notifLayout.add(buttonQuestion("Уведомления сообщают об изменениях в сервисе. " +
                "Вы сами отмечаете, какие уведомления и как вы хотите получать. " +
                "Переключатель синего цвета — уведомления включены, " +
                "переключатель прозрачный — уведомления отключены. " +
                "Чтобы получать уведомления во всплывающих подсказках " +
                "на смартфоне или по электронной почте, " +
                "поставьте флажки в соответствующих столбцах"), notifications);
        return notifLayout;

    }


    private Grid<NotificationsDto> createNotificationsGrid() {

        Grid<NotificationsDto> grid = new Grid<>();


        grid.addColumn(new ComponentRenderer<>(notificationsDto ->
                        buttonQuestion(notificationsDto.getDescription())))
                .setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(notificationsDto -> createLabel(notificationsDto.getLabel())))
                .setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(notificationsDto -> toggleButton(notificationsDto.isEnabled())))
                .setWidth("30px");

        grid.addColumn(new ComponentRenderer<>(notificationsDto -> createStyleCheckBox(notificationsDto.isEmailProvided())))
                .setHeader("Почта")
                .setWidth("30px");
        grid.addColumn(new ComponentRenderer<>(notificationsDto -> createStyleCheckBox(notificationsDto.isPhoneProvided())))
                .setHeader("Телефон")
                .setWidth("30px");
        grid.setItems(getAll());
        return grid;


    }

    private Button buttonQuestion(String text) {

        return QuestionButton.buttonQuestion(text);
    }

    private Label createLabel(String label) {

        Label label1 = new Label(label);

        label1.getStyle().set("margin-top", "8px");
        return label1;

    }

    private Checkbox createStyleCheckBox(boolean value) {

        Checkbox checkbox = new Checkbox();
        checkbox.setValue(value);
        return checkbox;

    }

    private ToggleButton toggleButton(boolean value) {
        ToggleButton toggle = new ToggleButton();
        toggle.setValue(value);
        toggle.setLabel("");
        return toggle;
    }

    private List<NotificationsDto> getAll() {
        return Arrays.asList(
                createUser(1L, true, "Уведомления о создании и просрочке заказа покупателя.",
                        true, false, "Заказы покупателей"),
                createUser(2L, true, "Уведомления о просрочке счета покупателя.",
                        true, false, "Счета покупателей"),
                createUser(3L, true, "Уведомления о снижении остатка товара ниже его неснижаемого остатка.",
                        true, false, "Остатки"),
                createUser(4L, true, "Уведомления об открытии и закрытии розничной смены.",
                        true, false, "Розничная торговля"),
                createUser(5L, true, "Уведомления об изменениях, связанных с задачами.",
                        true, false, "Задачи"),
                createUser(6L, true, "Уведомления об окончании импорта и экспорта.",
                        true, false, "Обмен данными"),
                createUser(7L, true, "Уведомления, настроенные для сценариев",
                        true, false, "Сценарии"),
                createUser(8L, true, "Уведомления об изменениях в настройках.",
                        true, false, "Интернет-магазины"));


    }

    private NotificationsDto createUser(Long id, boolean isEnabled, String description, boolean isEmailProvided
            , boolean isPhoneProvided, String label) {
        NotificationsDto notificationsDto = new NotificationsDto();

        notificationsDto.setId(id);
        notificationsDto.setEnabled(isEnabled);
        notificationsDto.setDescription(description);
        notificationsDto.setEmailProvided(isEmailProvided);
        notificationsDto.setPhoneProvided(isPhoneProvided);
        notificationsDto.setLabel(label);

        return notificationsDto;
    }

}



