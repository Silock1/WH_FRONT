package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;

import java.util.List;

@PageTitle("Сотрудники")
@Route(value = "employee", layout = SettingsView.class)
public class EmployeeSettingView extends VerticalLayout {
    private final TextField textField = new TextField();
    private final EmployeeService employeeService;
    private final Grid<EmployeeDto> employeeDtoGrid = new Grid<>(EmployeeDto.class, false);
    private final HorizontalLayout filterEmpLayout;

    public EmployeeSettingView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.filterEmpLayout = filterEmployee();
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, filterEmpLayout);
        employeeDtoGridSet();
        setSizeFull();
    }


    private HorizontalLayout getGroupButtons() {
        var horizontalLayout = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> Notification.show("" +
                "Внесите в МойСклад сотрудников вашей организации и настройте доступ для каждого из них. Вы можете выдать сотруднику полные права администратора, ограничить доступ по разделам или вообще разрешить пользоваться только Кассой в точке продаж.\n" +
                "\n" +
                "Читать инструкцию: Сотрудники", 5000, Notification.Position.TOP_START));

        Label employee = new Label("Сотрудники");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addEmployee = new Button("Сотрудник", new Icon(VaadinIcon.PLUS));
        addEmployee.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addEmployee.addClickListener(event -> UI.getCurrent().navigate("employee/new_employee"));

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> filterEmpLayout.setVisible(!filterEmpLayout.isVisible()));

        TextField searchField = new TextField();
        searchField.setPlaceholder("ФИО");
        searchField.setWidth("200px");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        horizontalLayout.add(helpButton, employee, refreshButton, addEmployee, addFilterButton,
                searchField, editMenuBar, setting);
        return horizontalLayout;
    }

    private HorizontalLayout getSetting() {
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
        });

        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {
        });

        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(textField, editMenuBar);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        return horizontalLayout;
    }

    private HorizontalLayout getEditMenuBar() {
        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        settingButton.addClickListener(buttonClickEvent -> {

        });

        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(settingButton);
        return horizontalLayout;
    }

    private void employeeDtoGridSet() {
        Grid.Column<EmployeeDto> idColumn = employeeDtoGrid.addColumn(EmployeeDto::getId).setHeader("ID");
        Grid.Column<EmployeeDto> lastName = employeeDtoGrid.addColumn(EmployeeDto::getLastName).setHeader("Фамилия");
        Grid.Column<EmployeeDto> name = employeeDtoGrid.addColumn(EmployeeDto::getFirstName).setHeader("Имя");
        Grid.Column<EmployeeDto> middleName = employeeDtoGrid.addColumn(EmployeeDto::getMiddleName).setHeader("Отчество");
        Grid.Column<EmployeeDto> email = employeeDtoGrid.addColumn(EmployeeDto::getEmail).setHeader("Email");
        Grid.Column<EmployeeDto> phone = employeeDtoGrid.addColumn(EmployeeDto::getPhone).setHeader("Телефон");
        Grid.Column<EmployeeDto> inn = employeeDtoGrid.addColumn(EmployeeDto::getInn).setHeader("ИНН");
        Grid.Column<EmployeeDto> description = employeeDtoGrid.addColumn(EmployeeDto::getDescription).setHeader("Описание");
        Grid.Column<EmployeeDto> role = employeeDtoGrid.addColumn(EmployeeDto::getRoles).setHeader("Роль");
        employeeDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<EmployeeDto> columnToggleContextMenu = new ColumnToggleContextMenu(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Фамилия", lastName);
        columnToggleContextMenu.addColumnToggleItem("Имя", name);
        columnToggleContextMenu.addColumnToggleItem("Отчество", middleName);
        columnToggleContextMenu.addColumnToggleItem("Email", email);
        columnToggleContextMenu.addColumnToggleItem("Телефон", phone);
        columnToggleContextMenu.addColumnToggleItem("ИНН", inn);
        columnToggleContextMenu.addColumnToggleItem("Описание", description);
        columnToggleContextMenu.addColumnToggleItem("Роль", role);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        employeeDtoGrid.setItems(employeeDtos);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        employeeDtoGrid.setHeightByRows(true);
        headerLayout.add(employeeDtoGrid, menuButton);
        add(headerLayout);
    }

    private HorizontalLayout filterEmployee() {

        HorizontalLayout firstLine = new HorizontalLayout();

        Button find = new Button("Найти");
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button clear = new Button("Очистить");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
        Button settingButton = new Button(new Icon(VaadinIcon.COG));

        TextField phoneEmp = new TextField("Телефон");
        TextField emailEmp = new TextField("E-mail");
        TextField innEmp = new TextField("ИНН");
        TextField positionEmp = new TextField("Должность");

        firstLine.add(find, clear, bookmarks, settingButton);

        ComboBox<String> show = new ComboBox<>("Показывать", "Только обычные", "Только архивные", "Все");
        ComboBox<EmployeeDto> employeeDtoBox = new ComboBox<>("Владелец-сотрудник");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        employeeDtoBox.setItems(employeeDtos);
        employeeDtoBox.setItemLabelGenerator(EmployeeDto::getLastName);

        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
        ComboBox<String> generalAccess = new ComboBox<>("Общий доступ", "Да", "Нет");
        TextField loginEmp = new TextField("Логин");

        ComboBox<String> general = new ComboBox<>("Разрешен вход в систему", "Да", "Нет");
        DatePicker periodStart = new DatePicker("Когда изменен");
        DatePicker periodEnd = new DatePicker("до");

        ComboBox<EmployeeDto> employeeBox = new ComboBox<>("Кто изменил");
        List<EmployeeDto> employeeDtos2 = employeeService.getAll();
        employeeBox.setItems(employeeDtos2);
        employeeBox.setItemLabelGenerator(EmployeeDto::getLastName);

        FormLayout formLayout = new FormLayout();
        formLayout.add(phoneEmp, emailEmp, innEmp, positionEmp, show, employeeDtoBox, departmentDtoComboBox,
                generalAccess, loginEmp, general, periodStart, periodEnd, employeeBox);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);
        filterForm.setVisible(false);
        return filterForm;
    }

}
