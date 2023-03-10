package com.warehouse_accounting.components.user;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.util.QuestionButton;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ImageDto;
import com.warehouse_accounting.models.dto.LanguageDto;
import com.warehouse_accounting.models.dto.NotificationsDto;
import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.models.dto.PrintingDocumentsDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.models.dto.SelectorDto;
import com.warehouse_accounting.models.dto.SelectorForViewDto;
import com.warehouse_accounting.models.dto.SettingsDto;
import com.warehouse_accounting.models.dto.StartScreenDto;
import com.warehouse_accounting.models.dto.TariffDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.security.UserPrincipal;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.LanguageService;
import com.warehouse_accounting.services.interfaces.PositionService;
import com.warehouse_accounting.services.interfaces.PrintingDocumentsService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.RoleService;
import com.warehouse_accounting.services.interfaces.SettingsService;
import com.warehouse_accounting.services.interfaces.StartScreenService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@Log4j2
@PageTitle("?????????????????? ????????????????????????")
@Route(value = "profile/settings", layout = AppView.class)
public class UserSettingsView extends VerticalLayout {


    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout mainLeftLayout = new VerticalLayout();
    VerticalLayout mainRightLayout = new VerticalLayout();
    VerticalLayout verticalLayout2 = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    HorizontalLayout mainHorisontalLayout = new HorizontalLayout();

    Button createButton = new Button("??????????????????");
    Button closeButton = new Button("??????????????");
    Label change = new Label("??????????????????:");
    Button changeButtonPass = new Button((new Icon(VaadinIcon.MINUS)));
    HorizontalLayout passLayout = new HorizontalLayout();
    Button buttonPass = new Button("???????????????? ????????????");
    private Grid<SelectorForViewDto> gridNotifications = new Grid<>();
    private List<ToggleButton> toggleButtonList = new ArrayList<>();
    private List<Checkbox> checkboxListPost = new ArrayList<>();
    private List<Checkbox> checkboxListPhone = new ArrayList<>();

    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final ImageService imageService;
    private final LanguageService languageService;
    private final SettingsService settingsService;
    private final CompanyService companyService;
    private final WarehouseService warehouseService;
    private final ContractorService contractorService;
    private final ProjectService projectService;
    private final PrintingDocumentsService printingDocumentsService;
    private final StartScreenService startScreenService;
    private final RoleService roleService;
    private int numberOfAdditionalFields;
    private boolean refreshReportsAuto;
    private boolean signatureInLetters;

    private SettingsDto settingsDto;
    private EmployeeDto employeeDto;
    private PositionDto positionDto;
    private LanguageDto languageDto;
    private ImageDto imageDto;
    private CompanyDto companyDto;
    private WarehouseDto warehouseDto;
    private ContractorDto customerDto;
    private ContractorDto producerDto;
    private ProjectDto projectDto;
    private PrintingDocumentsDto printingDocumentsDto;
    private StartScreenDto startScreenDto;
    private NotificationsDto notificationsDto;
    private TariffDto tariffDto;

    private FileBuffer buffer;
    private final TextField firstName = new TextField();
    private final TextField middleName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final TextField phone = new TextField();
    private final TextField inn = new TextField();
    private final TextField position = new TextField();
    private final TextField numberOfAdFields = new TextField();
    private final Checkbox refreshReports = new Checkbox();
    private final Checkbox signatureInLet = new Checkbox();
    private final ComboBox companyBox = new ComboBox();
    private final ComboBox warehouseBox = new ComboBox();
    private final ComboBox customerBox = new ComboBox();
    private final ComboBox producerBox = new ComboBox();
    private final ComboBox projectBox = new ComboBox();
    private final ComboBox printingDocBox = new ComboBox();
    private final ComboBox startScreenBox = new ComboBox();

    private Notification notification;

    public UserSettingsView(SettingsService settingsService,
                            CompanyService companyService,
                            WarehouseService warehouseService,
                            ContractorService contractorService,
                            ProjectService projectService,
                            PrintingDocumentsService printingDocumentsService,
                            StartScreenService startScreenService,
                            PositionService positionService,
                            EmployeeService employeeService,
                            ImageService imageService,
                            LanguageService languageService, RoleService roleService) throws IOException {
        this.companyService = companyService;
        this.warehouseService = warehouseService;
        this.contractorService = contractorService;
        this.projectService = projectService;
        this.printingDocumentsService = printingDocumentsService;
        this.startScreenService = startScreenService;
        this.settingsService = settingsService;
        this.languageService = languageService;
        this.employeeService = employeeService;
        this.positionService = positionService;
        this.imageService = imageService;
        this.roleService = roleService;
        dsUserSettingsView();
    }

    public void dsUserSettingsView() throws IOException {
        initialization();
        setSizeFull();
        //???????????????????? ???????????????? ????????????????????
        employeeDto = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployeeDto();
        horizontalLayout.add(createButton, closeButton, change, changeButtonPass);
        verticalLayout.add(
                textFieldrow("??????", firstName, employeeDto.getFirstName()),
                textFieldrow("????????????????", middleName, employeeDto.getMiddleName()),
                textFieldrow("??????????????", lastName, employeeDto.getLastName()),
                textFieldrow("E-mail", email, employeeDto.getEmail()),
                textFieldrow("??????????????", phone, employeeDto.getPhone()),
                textFieldrow("??????????????????", position, employeeDto.getPosition().getName()),
                textFieldrow("??????", inn, employeeDto.getInn()));
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        changeButtonPass.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        verticalLayout2.add(imagePortrait());
        passLayout.add(buttonPass);
        mainLeftLayout.add(horizontalLayout, verticalLayout);

        fillFields();
        mainRightLayout.add(textDefault(),
                setComboBox("Company", companyBox, companyService.getAll()),
                setComboBox("Warehouse", warehouseBox, warehouseService.getAll()),
                setComboBox("Customer", customerBox, contractorService.getAll()),
                setComboBox("Producer", producerBox, contractorService.getAll()),
                setComboBox("Project", projectBox, projectService.getAll()),
                settingsLabel(),
                setDefaultLanguage(),
                setComboBox("Printing Documents", printingDocBox, printingDocumentsService.getAll()),
                labelsRow("?????????? ???????????????????????????? ?????????? ?? ????????????", numberOfAdFields),
                setComboBox("Start Screen", startScreenBox, startScreenService.getAll()),
                forCheckbox("?????????????????? ???????????? ??????????????????????????", refreshReports, refreshReportsAuto),
                forCheckbox("?????????????? ?? ???????????????????????? ??????????????", signatureInLet, signatureInLetters),
//                forCheckbox("???????????????????????? ?????????? ???????????????? ????????????????????????"),
//                forCheckbox("???????????????????????? ?????????? ???????????????? ??????????????"),
//                forCheckbox("???????????????????????? ???????? ???????????? ?????? ???????????? ???? ?????????????????????? ??????????????"),
                notificationLabel(),
                createNotificationsGrid());
        mainHorisontalLayout.setWidth("70%");
        mainLeftLayout.add(namer(), passLayout, horizontalLayout, verticalLayout, verticalLayout2);
        mainRightLayout.add();
        mainHorisontalLayout.add(mainLeftLayout, mainRightLayout);
        add(horizontalLayout, mainHorisontalLayout);
        verticalLayout.getStyle().set("margin-left", "var(--lumo-space-xl)");
        verticalLayout.getElement().getStyle().set("padding", "40px");
        activatedCreateButton();
        activatedCloseButton();
    }

    private void initialization() {
        if (settingsService.getAll().isEmpty()) {
            settingsDto = new SettingsDto();
            System.out.println("Empty");
        } else {
            employeeDto = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployeeDto();
            settingsDto = settingsService.getById(employeeDto.getId());
        }
        companyDto = settingsDto.getCompanyDto();
        warehouseDto = settingsDto.getWarehouseDto();
        customerDto = settingsDto.getCustomerDto();
        producerDto = settingsDto.getProducerDto();
        projectDto = settingsDto.getProjectDto();
        printingDocumentsDto = settingsDto.getPrintingDocumentsDto();
        numberOfAdditionalFields = settingsDto.getNumberOfAdditionalFieldsPerLine();
        startScreenDto = settingsDto.getStartScreenDto();
        refreshReportsAuto = settingsDto.isRefreshReportsAuto();
        signatureInLetters = settingsDto.isSignatureInLetters();
        notificationsDto = settingsDto.getNotificationsDto();

    }


    private HorizontalLayout setComboBox(String label, ComboBox comboBox, List list) {
        Label companyLabel = new Label(label);
        comboBox.setItems(list);
        comboBox.setItemLabelGenerator(Object::toString);
        companyLabel.getStyle().set("width", "90px");
        comboBox.getStyle().set("width", "190px");
        return new HorizontalLayout(companyLabel, comboBox, buttonPen());
    }

    private HorizontalLayout namer() {
        HorizontalLayout hLayout = new HorizontalLayout();
        Label loginLabel = new Label("??????????");
        hLayout.add(loginLabel);
        return hLayout;
    }

    private Button buttonPen() {
        Button penButton = new Button(new Icon(VaadinIcon.PENCIL));
        penButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return penButton;
    }

//    private Component addLoginInfo() {
//        TextField login = new TextField();
//        Label label = new Label("??????????");
//        login.setValue(employeeService.getPrincipal().getEmail());
//        login.setReadOnly(true);
//        HorizontalLayout addEmployeePasswordAddLayout = new HorizontalLayout(label, login);
//        return addEmployeePasswordAddLayout;
//    }
//    ?????????? ???????????????????? security


    private HorizontalLayout labelsRow(String rowLabel, TextField textField) {
        HorizontalLayout hLayout = new HorizontalLayout();
        Label label = new Label(rowLabel);
        textField.setValue(String.valueOf(numberOfAdditionalFields));
        hLayout.add(label, textField);
        label.getStyle().set("width", "90px");
        textField.getStyle().set("width", "190px");
        return hLayout;
    }

    private HorizontalLayout textFieldrow(String lable, TextField field, String value) {
        HorizontalLayout hLayout = new HorizontalLayout();
        Label label = new Label(lable);
        if (value == null) {
            value = "";
        }
        field.setValue(value);
        hLayout.add(label, field);
        label.getStyle().set("width", "90px");
        field.getStyle().set("width", "190px");
        return hLayout;
    }

    private void fillFields() {
//        employeeDto = employeeService.getById(employeeService.getPrincipal().getId()); ???????????????????????????????? ?????????? ?????????? security, ?? ?????????????? ???????? ??????????????
        try {
            positionDto = employeeDto.getPosition();
            if (positionDto == null) {
                positionDto = new PositionDto();
                positionDto.setName("");
            }
            if (positionDto.getName() == null) {
                positionDto.setName("");
            }
            position.setValue(positionDto.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        firstName.setValue(employeeDto.getFirstName() == null ? "" : employeeDto.getFirstName());
        middleName.setValue(employeeDto.getMiddleName() == null ? "" : employeeDto.getMiddleName());
        lastName.setValue(employeeDto.getMiddleName() == null ? "" : employeeDto.getMiddleName());
        email.setValue(employeeDto.getEmail() == null ? "" : employeeDto.getEmail());
        phone.setValue(employeeDto.getPhone() == null ? "" : employeeDto.getPhone());
        inn.setValue(employeeDto.getInn() == null ? "" : employeeDto.getInn());
    }

    void activatedCloseButton() {
        closeButton.addClickListener(event ->
                UI.getCurrent().getPage().setLocation("http://localhost:4447/"));
    }

    private void setNotificationSettings() {
        SelectorDto buyerOrders = new SelectorDto(0L, toggleButtonList.get(0).getValue(),
                checkboxListPost.get(0).getValue(), checkboxListPhone.get(0).getValue());
        SelectorDto buyerInvoices = new SelectorDto(1L, toggleButtonList.get(1).getValue(),
                checkboxListPost.get(1).getValue(), checkboxListPhone.get(1).getValue());
        SelectorDto remainder = new SelectorDto(2L, toggleButtonList.get(2).getValue(),
                checkboxListPost.get(2).getValue(), checkboxListPhone.get(2).getValue());
        SelectorDto retail = new SelectorDto(3L, toggleButtonList.get(3).getValue(),
                checkboxListPost.get(3).getValue(), checkboxListPhone.get(3).getValue());
        SelectorDto tasks = new SelectorDto(4L, toggleButtonList.get(4).getValue(),
                checkboxListPost.get(4).getValue(), checkboxListPhone.get(4).getValue());
        SelectorDto dataExchange = new SelectorDto(5L, toggleButtonList.get(5).getValue(),
                checkboxListPost.get(5).getValue(), checkboxListPhone.get(5).getValue());
        SelectorDto scripts = new SelectorDto(6L, toggleButtonList.get(6).getValue(),
                checkboxListPost.get(6).getValue(), checkboxListPhone.get(6).getValue());
        SelectorDto onlineStores = new SelectorDto(7L, toggleButtonList.get(7).getValue(),
                checkboxListPost.get(7).getValue(), checkboxListPhone.get(7).getValue());
        if (notificationsDto == null) {
            notificationsDto = new NotificationsDto();
        }
        notificationsDto.setBuyerOrders(buyerOrders);
        notificationsDto.setBuyersInvoices(buyerInvoices);
        notificationsDto.setRemainder(remainder);
        notificationsDto.setRetail(retail);
        notificationsDto.setTasks(tasks);
        notificationsDto.setDataExchange(dataExchange);
        notificationsDto.setScripts(scripts);
        notificationsDto.setOnlineStores(onlineStores);
    }

    private SettingsDto saveSetting() {
        settingsDto.setCompanyDto(companyDto);
        settingsDto.setWarehouseDto(warehouseDto);
        settingsDto.setCustomerDto(customerDto);
        settingsDto.setProducerDto(producerDto);
        settingsDto.setProjectDto(projectDto);
        settingsDto.setPrintingDocumentsDto(printingDocumentsDto);
        settingsDto.setNumberOfAdditionalFieldsPerLine(Integer.parseInt(numberOfAdFields.getValue()));
        settingsDto.setStartScreenDto(startScreenDto);
        settingsDto.setRefreshReportsAuto(refreshReports.getValue());
        settingsDto.setSignatureInLetters(signatureInLet.getValue());
        settingsDto.setNotificationsDto(notificationsDto);
        settingsDto.setEmployeeDto(employeeDto);
        return settingsDto;
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
//            if (employeeDto.getTariff().isEmpty()) {
//                employeeDto.setTariff(Set.of(TariffDto.getDefaultTarifDto()));
//            } // ????????????????, ???????? ???? ?????????????????????? ?????????? ?? ?????????? TariffServiceImpl
            if (positionService.getAll().stream()
                    .anyMatch(positionDto -> positionDto.getName().equalsIgnoreCase(position.getValue()))
            ) {
                positionDto = positionService.getAll().stream()
                        .filter(positionDto -> positionDto.getName().equalsIgnoreCase(position.getValue()))
                        .findFirst().get();
            } else {
                positionService.create(new PositionDto(null, position.getValue(), null));
                positionDto = positionService.getAll().stream()
                        .filter(positionDto -> positionDto.getName().equalsIgnoreCase(position.getValue()))
                        .findFirst().get();
                positionDto.setSortNumber(positionDto.getId().toString());
                positionService.update(positionDto);
            }

            if (!buffer.getFileName().equalsIgnoreCase("")) {
                String filePath = "src/main/resources/static/avatars/" + new Date().getTime() + buffer.getFileName();
                imageDto = new ImageDto(employeeDto.getId(), filePath, "Picture");
                imageService.create(imageDto);
                imageDto = imageService.getAll().stream().filter(imageDto ->
                        imageDto.getImageUrl().equals(filePath)).findFirst().get();
                employeeDto.setImage(imageDto);
                employeeDto.setPosition(positionDto);
                employeeService.update(employeeDto);
                try {
                    copyInputStreamToFile(buffer.getInputStream(), new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                employeeDto.setPosition(positionDto);
                employeeDto.setTariff(null); // ????????????????, ???????? ???? ?????????????????????? ?????????? ?? ??????????
                System.out.println(employeeDto);
                employeeService.update(employeeDto);
            }
            setComboBoxSettings();
            setNotificationSettings();
            saveSetting();
            settingsService.update(settingsDto);
            System.out.println(settingsDto);
            UI.getCurrent().getPage().setLocation("http://localhost:4447/");
        });
    }

    private void setComboBoxSettings() {
        companyDto = (CompanyDto) companyBox.getValue();
        if (companyBox.getValue() == null && companyDto == null) {
            companyDto = new CompanyDto();
        }
        warehouseDto = (WarehouseDto) warehouseBox.getValue();
        customerDto = (ContractorDto) customerBox.getValue();
        producerDto = (ContractorDto) producerBox.getValue();
        projectDto = (ProjectDto) projectBox.getValue();
        printingDocumentsDto = (PrintingDocumentsDto) printingDocBox.getValue();
        startScreenDto = (StartScreenDto) startScreenBox.getValue();
    }

    private VerticalLayout imagePortrait() {
        buffer = new FileBuffer();
        Upload upload = new Upload(buffer);
        upload.setDropAllowed(false);
        Button uploadButton = new Button("+ ??????????????????????");
        upload.setUploadButton(uploadButton);
        Div rightSection = new Div(upload);
        FormLayout formLayout = new FormLayout(rightSection);
        VerticalLayout Portrait = new VerticalLayout();
        Label image = new Label("??????????????????????");
        image.getStyle().set("color", "red");
        image.getStyle().set("font-weight", "bold");
        Portrait.add(image, formLayout);
        return Portrait;
    }


    private HorizontalLayout textDefault() {
        HorizontalLayout defLayout = new HorizontalLayout();
        Label def = new Label("???????????????? ???? ??????????????????");
        def.getStyle().set("color", "red");
        def.getStyle().set("font-weight", "bold");
        defLayout.add(def);
        return defLayout;
    }

    private HorizontalLayout settingsLabel() {
        HorizontalLayout settLayout = new HorizontalLayout();
        Label setting = new Label("??????????????????");
        setting.getStyle().set("color", "red");
        setting.getStyle().set("font-weight", "bold");
        settLayout.add(setting);
        return settLayout;
    }


    private HorizontalLayout setDefaultLanguage() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label currentLabel = new Label("????????");
        ComboBox<LanguageDto> currentBox = new ComboBox<>();
        currentBox.setItems(languageService.getAll());
        currentBox.setItemLabelGenerator(LanguageDto::getLanguage);
        horizontalLayout.add(currentLabel, currentBox);
        currentLabel.getStyle().set("width", "90px");
        currentBox.getStyle().set("width", "190px");
        return horizontalLayout;
    }

    private HorizontalLayout forCheckbox(String text, Checkbox checkbox, boolean value) {
        checkbox.setValue(value);
        Label checkboxLabel = new Label(text);
        checkboxLabel.getStyle().set("width", "90px");
        checkbox.getStyle().set("width", "190px");
        return new HorizontalLayout(checkboxLabel, checkbox);

    }

    private HorizontalLayout notificationLabel() {
        HorizontalLayout notifLayout = new HorizontalLayout();
        Label notifications = new Label("??????????????????????");
        notifications.getStyle().set("color", "red");
        notifications.getStyle().set("font-weight", "bold");
        notifications.getStyle().set("margin-top", "8px");
        notifLayout.add(buttonQuestion("?????????????????????? ???????????????? ???? ???????????????????? ?? ??????????????. " +
                "???? ???????? ??????????????????, ?????????? ?????????????????????? ?? ?????? ???? ???????????? ????????????????. " +
                "?????????????????????????? ???????????? ?????????? ??? ?????????????????????? ????????????????, " +
                "?????????????????????????? ???????????????????? ??? ?????????????????????? ??????????????????. " +
                "?????????? ???????????????? ?????????????????????? ???? ?????????????????????? ???????????????????? " +
                "???? ?????????????????? ?????? ???? ?????????????????????? ??????????, " +
                "?????????????????? ???????????? ?? ?????????????????????????????? ????????????????"), notifications);
        return notifLayout;

    }


    private Grid<SelectorForViewDto> createNotificationsGrid() {

        System.out.println(gridNotifications.getPageSize());

        gridNotifications.addColumn(new ComponentRenderer<>(selectorDto ->
                        buttonQuestion(selectorDto.getDescription())))
                .setAutoWidth(true);

        gridNotifications.addColumn(new ComponentRenderer<>(selectorDto -> createLabel(selectorDto.getLabel())))
                .setAutoWidth(true);

        gridNotifications.addColumn(new ComponentRenderer<>(selectorDto -> toggleButton(selectorDto.isActivate())))
                .setWidth("30px");

        gridNotifications.addColumn(new ComponentRenderer<>(selectorDto -> createStyleCheckBoxPost(selectorDto.isPost())))
                .setHeader("??????????")
                .setWidth("30px");
        gridNotifications.addColumn(new ComponentRenderer<>(selectorDto -> createStyleCheckBoxPhone(selectorDto.isPhone())))
                .setHeader("??????????????")
                .setWidth("30px");
        gridNotifications.setItems(getAll());
        return gridNotifications;
    }

    private Button buttonQuestion(String text) {
        return QuestionButton.buttonQuestion(text);
    }

    private Label createLabel(String label) {
        Label label1 = new Label(label);
        label1.getStyle().set("margin-top", "8px");
        return label1;
    }

    private Checkbox createStyleCheckBoxPhone(boolean value) {
        Checkbox checkbox = new Checkbox();
        checkbox.setValue(value);
        checkboxListPhone.add(checkbox);
        return checkbox;
    }

    private Checkbox createStyleCheckBoxPost(boolean value) {
        Checkbox checkbox = new Checkbox();
        checkbox.setValue(value);
        checkboxListPost.add(checkbox);
        return checkbox;
    }

    private ToggleButton toggleButton(boolean value) {
        ToggleButton toggle = new ToggleButton();
        toggle.setValue(value);
        toggle.setLabel("");
        toggleButtonList.add(toggle);
        return toggle;
    }

    private List<SelectorForViewDto> getAll() {
        if (notificationsDto == null) {
            return Arrays.asList(
                    createSelector(1L, true, "?????????????????????? ?? ???????????????? ?? ?????????????????? ???????????? ????????????????????.",
                            true, false, "???????????? ??????????????????????"),
                    createSelector(2L, true, "?????????????????????? ?? ?????????????????? ?????????? ????????????????????.",
                            true, false, "?????????? ??????????????????????"),
                    createSelector(3L, true, "?????????????????????? ?? ???????????????? ?????????????? ???????????? ???????? ?????? ???????????????????????? ??????????????.",
                            true, false, "??????????????"),
                    createSelector(4L, true, "?????????????????????? ???? ???????????????? ?? ???????????????? ?????????????????? ??????????.",
                            true, false, "?????????????????? ????????????????"),
                    createSelector(5L, true, "?????????????????????? ???? ????????????????????, ?????????????????? ?? ????????????????.",
                            true, false, "????????????"),
                    createSelector(6L, true, "?????????????????????? ???? ?????????????????? ?????????????? ?? ????????????????.",
                            true, false, "?????????? ??????????????"),
                    createSelector(7L, true, "??????????????????????, ?????????????????????? ?????? ??????????????????",
                            true, false, "????????????????"),
                    createSelector(8L, true, "?????????????????????? ???? ???????????????????? ?? ????????????????????.",
                            true, false, "????????????????-????????????????"));
        }
        return Arrays.asList(
                createSelector(notificationsDto.getBuyerOrders().getId(), notificationsDto.getBuyerOrders().isActivate(),
                        "?????????????????????? ?? ???????????????? ?? ?????????????????? ???????????? ????????????????????.",
                        notificationsDto.getBuyerOrders().isPost(), notificationsDto.getBuyerOrders().isPhone(), "???????????? ??????????????????????"),
                createSelector(notificationsDto.getBuyersInvoices().getId(), notificationsDto.getBuyersInvoices().isActivate(),
                        "?????????????????????? ?? ?????????????????? ?????????? ????????????????????.",
                        notificationsDto.getBuyersInvoices().isPost(), notificationsDto.getBuyersInvoices().isPhone(), "?????????? ??????????????????????"),
                createSelector(notificationsDto.getRemainder().getId(), notificationsDto.getRemainder().isActivate(),
                        "?????????????????????? ?? ???????????????? ?????????????? ???????????? ???????? ?????? ???????????????????????? ??????????????.",
                        notificationsDto.getRemainder().isPost(), notificationsDto.getRemainder().isPhone(), "??????????????"),
                createSelector(notificationsDto.getRetail().getId(), notificationsDto.getRetail().isActivate(),
                        "?????????????????????? ???? ???????????????? ?? ???????????????? ?????????????????? ??????????.",
                        notificationsDto.getRetail().isPost(), notificationsDto.getRetail().isPhone(), "?????????????????? ????????????????"),
                createSelector(notificationsDto.getTasks().getId(), notificationsDto.getTasks().isActivate(),
                        "?????????????????????? ???? ????????????????????, ?????????????????? ?? ????????????????.",
                        notificationsDto.getTasks().isPost(), notificationsDto.getTasks().isPhone(), "????????????"),
                createSelector(notificationsDto.getDataExchange().getId(), notificationsDto.getDataExchange().isActivate(),
                        "?????????????????????? ???? ?????????????????? ?????????????? ?? ????????????????.",
                        notificationsDto.getDataExchange().isPost(), notificationsDto.getDataExchange().isPhone(), "?????????? ??????????????"),
                createSelector(notificationsDto.getScripts().getId(), notificationsDto.getScripts().isActivate(),
                        "??????????????????????, ?????????????????????? ?????? ??????????????????",
                        notificationsDto.getScripts().isPost(), notificationsDto.getScripts().isPhone(), "????????????????"),
                createSelector(notificationsDto.getOnlineStores().getId(), notificationsDto.getOnlineStores().isActivate(),
                        "?????????????????????? ???? ???????????????????? ?? ????????????????????.",
                        notificationsDto.getOnlineStores().isPost(), notificationsDto.getOnlineStores().isPhone(), "????????????????-????????????????"));

    }

    private SelectorForViewDto createSelector(Long id,
                                              boolean isEnabled,
                                              String description,
                                              boolean isEmailProvided,
                                              boolean isPhoneProvided,
                                              String label) {
        return new SelectorForViewDto(id, isEnabled, description, isEmailProvided, isPhoneProvided, label);
    }

}



