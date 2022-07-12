package com.warehouse_accounting.components.user.legalDetail;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.legalDetail.forms.LegalDetailForm;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.services.interfaces.LegalDetailService;

import java.util.List;

@PageTitle("ЮрЛица")
@Route(value = "mycompany", layout = SettingsView.class)
public class LegalDetailSettingsView extends VerticalLayout {

    private final TextField textField = new TextField();
    private final Grid<LegalDetailDto> legalDetailDtoGrid = new Grid<>(LegalDetailDto.class, false);
    private final LegalDetailService legalDetailService;
    private final HorizontalLayout formLayout;

    public LegalDetailSettingsView(LegalDetailService legalDetailService) {
        this.legalDetailService = legalDetailService;
        this.formLayout = createNewLegalDetail();
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, formLayout);
        legalDetailDtoGridSet();
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> {
            Notification.show("" +
                            "Если у вас несколько организаций, вы можете внести\n" +
                            "их в МойСклад и вести учет отдельно по каждой из них.\n" +
                            "\n" +
                            "Читать инструкцию\n"
                    , 5000, Notification.Position.TOP_START);
        });

        Label textLegalDetail = new Label();
        textLegalDetail.setText("Юр. лица");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);


        Button addtextLegalDetailButton = new Button("Юр. лицо", new Icon(VaadinIcon.PLUS));
        addtextLegalDetailButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addtextLegalDetailButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(LegalDetailForm.class);
        });


        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> {
            if (formLayout.isVisible()) {
                formLayout.setVisible(false);
            } else {
                formLayout.setVisible(true);
            }
        });

        TextField searchField = new TextField();
        searchField.setPlaceholder("Наименование или код");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        groupControl.add(helpButton, textLegalDetail, refreshButton, addtextLegalDetailButton,
                addFilterButton, searchField, editMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }

    private HorizontalLayout getSetting() {
        Icon cogIcon = new Icon(VaadinIcon.COG);
        Button settingButton = new Button(cogIcon);
        settingButton.addClickListener(buttonClickEvent -> {
        });
        HorizontalLayout setting = new HorizontalLayout();
        setting.add(settingButton);
        return setting;
    }

    private void legalDetailDtoGridSet() {
        Grid.Column<LegalDetailDto> idColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getId).setHeader("Id");
        Grid.Column<LegalDetailDto> lastNameColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getLastName).setHeader("Фамилия");
        Grid.Column<LegalDetailDto> firstNameColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getFirstName).setHeader("Имя");
        Grid.Column<LegalDetailDto> middleNameColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getMiddleName).setHeader("Отчество");
        Grid.Column<LegalDetailDto> addressColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getAddress).setHeader("Комментарии к адресу");
        Grid.Column<LegalDetailDto> innColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getInn).setHeader("ИНН");
        Grid.Column<LegalDetailDto> okpoColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getOkpo).setHeader("ОКПО");
        Grid.Column<LegalDetailDto> ogrnipColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getOgrnip).setHeader("ОГРНИП");
        Grid.Column<LegalDetailDto> kppColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getKpp).setHeader("КПП");
        Grid.Column<LegalDetailDto> numberOfTheCertificateColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getNumberOfTheCertificate).setHeader("Номер сертификата");
        Grid.Column<LegalDetailDto> dateOfTheCertificateColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getDateOfTheCertificate).setHeader("Дата выдачи сертификата");
        Grid.Column<LegalDetailDto> typeOfContractorNameColumn = legalDetailDtoGrid.addColumn(LegalDetailDto::getTypeOfContractorName).setHeader("Тип контракта");

        legalDetailDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<LegalDetailDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Фамилия", lastNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Имя", firstNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Отчество", middleNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарии к адресу", addressColumn);
        columnToggleContextMenu.addColumnToggleItem("ИНН", innColumn);
        columnToggleContextMenu.addColumnToggleItem("ОКПО", okpoColumn);
        columnToggleContextMenu.addColumnToggleItem("ОГРНИП", ogrnipColumn);
        columnToggleContextMenu.addColumnToggleItem("КПП", kppColumn);
        columnToggleContextMenu.addColumnToggleItem("Номер сертификата", numberOfTheCertificateColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата выдачи сертификата", dateOfTheCertificateColumn);
        columnToggleContextMenu.addColumnToggleItem("Тип контракта", typeOfContractorNameColumn);

        List<LegalDetailDto> legalDetailDtoList = legalDetailService.getAll();
        legalDetailDtoGrid.setItems(legalDetailDtoList);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        legalDetailDtoGrid.setHeightByRows(true);
        headerLayout.add(legalDetailDtoGrid, menuButton);
        add(headerLayout);
    }

    private HorizontalLayout createNewLegalDetail() {
        HorizontalLayout firstLine = new HorizontalLayout();

        Button findLegalDetailButton = new Button("Найти");
        findLegalDetailButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button clearLegalDetailButton = new Button("Очистить");
        clearLegalDetailButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        firstLine.add(findLegalDetailButton, clearLegalDetailButton);

        TextField sortNumberLegalDetail = new TextField("Код");
        TextField innLegalDetail = new TextField("ИНН");
        TextField phoneLegalDetail = new TextField("Телефон");
        TextField emailLegalDetail = new TextField("E-mail");
        TextField addressLegalDetail = new TextField("Адрес");
        TextField showLegalDetail = new TextField("Показывать");
        TextField ownerEmployeeLegalDetail = new TextField("Владелец-сотрудник");
        TextField ownerDepartmentLegalDetail = new TextField("Владелец-отдел");
        TextField generalAccessLegalDetail = new TextField("Общий доступ");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                sortNumberLegalDetail, innLegalDetail,
                phoneLegalDetail, emailLegalDetail, addressLegalDetail,
                showLegalDetail, ownerEmployeeLegalDetail, ownerDepartmentLegalDetail, generalAccessLegalDetail
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);

        filterForm.setVisible(false);
        return filterForm;
    }

}
