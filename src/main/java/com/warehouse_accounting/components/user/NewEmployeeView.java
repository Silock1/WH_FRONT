package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.IpAddressDto;
import com.warehouse_accounting.models.dto.IpNetworkDto;
import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.PositionService;
import com.warehouse_accounting.services.interfaces.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Новый сотрудник")
@Route(value = "employee/new_employee", layout = AppView.class)
@CssImport("./css/newEmployee.css")
public class NewEmployeeView extends VerticalLayout {

    private final transient DepartmentService departmentService;
    private final transient EmployeeService employeeService;
    private final transient RoleService roleService;
    private final transient PositionService positionService;
    private final Binder<EmployeeDto> employeeDtoBinder = new Binder<>(EmployeeDto.class);
    private final transient EmployeeDto tempEmployee;
    private final List<Integer[]> ipList = new ArrayList<>();
    private final List<Integer[]> networkParams = new ArrayList<>(2);

    public NewEmployeeView(DepartmentService departmentService, EmployeeService employeeService,
                           RoleService roleService, PositionService positionService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.positionService = positionService;
        VerticalLayout newEmployeeLayout = getNewEmployeeLayout();
        this.tempEmployee = new EmployeeDto();
        add(newEmployeeLayout);
    }


    private VerticalLayout getNewEmployeeLayout() {
        employeeDtoBinder.readBean(tempEmployee);
        HorizontalLayout dataLayout = new HorizontalLayout();
        dataLayout.add(getLeftDataLayout(), getRightDataLayout());

        return new VerticalLayout(getUpperRow(), dataLayout);
    }

    private HorizontalLayout getUpperRow() {
        HorizontalLayout newEmployeeUpperRow = new HorizontalLayout();

        Button save = new Button("Сохранить", event -> {
            saveNewEmployee();
            UI.getCurrent().navigate("employee");
        });
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button close = new Button("Закрыть", event -> UI.getCurrent().navigate("employee"));
        close.setId("rs200");

        Button archive = new Button("Поместить в архив");
        archive.getElement().setAttribute("disabled", true);
        archive.setId("rs20");

        RouterLink deleteLink = new RouterLink("Удалить сотрудника", EmployeeSettingView.class);
        deleteLink.setEnabled(false);
        deleteLink.setId("al-c");

        newEmployeeUpperRow.add(save, close, archive, deleteLink);
        return newEmployeeUpperRow;
    }

    private VerticalLayout getLeftDataLayout() {

        VerticalLayout leftColumnLayout = new VerticalLayout();
        leftColumnLayout.setClassName("div-form-style");

        Label lastNameLabel = new Label("Фамилия");
        lastNameLabel.setClassName("al-r");
        TextField lastName = new TextField();
        employeeDtoBinder.bind(lastName, EmployeeDto::getLastName, EmployeeDto::setLastName);
        leftColumnLayout.add(new HorizontalLayout(lastNameLabel, lastName));

        Label firstNameLabel = new Label("Имя");
        firstNameLabel.setClassName("al-r");
        TextField firstName = new TextField();
        employeeDtoBinder.bind(firstName, EmployeeDto::getFirstName, EmployeeDto::setFirstName);
        leftColumnLayout.add(new HorizontalLayout(firstNameLabel, firstName));

        Label middleNameLabel = new Label("Отчество");
        middleNameLabel.setClassName("al-r");
        TextField middleName = new TextField();
        employeeDtoBinder.bind(middleName, EmployeeDto::getMiddleName, EmployeeDto::setMiddleName);
        leftColumnLayout.add(new HorizontalLayout(middleNameLabel, middleName));

        Label phoneLabel = new Label("Телефон");
        phoneLabel.setClassName("al-r");
        TextField phone = new TextField();
        employeeDtoBinder.bind(phone, EmployeeDto::getPhone, EmployeeDto::setPhone);
        leftColumnLayout.add(new HorizontalLayout(phoneLabel, phone));

        Label postLabel = new Label("Должность");
        postLabel.setClassName("al-r");
        Select<String> post = new Select<>();
        post.setClassName("width300");
        List<PositionDto> postList = positionService.getAll();
        List<String> postNames = postList.stream().map(PositionDto::getName)
                .collect(Collectors.toList());
        post.setItems(postNames);
        post.addValueChangeListener(event -> {
            String tempName = event.getValue();
            for (PositionDto position : postList) {
                if (position.getName().equals(tempName)) {
                    tempEmployee.setPosition(position);
                    break;
                }
            }
        });
        leftColumnLayout.add(new HorizontalLayout(postLabel, post));

        Label innLabel = new Label("ИНН");
        innLabel.setClassName("al-r");
        TextField inn = new TextField();
        employeeDtoBinder.bind(inn, EmployeeDto::getInn, EmployeeDto::setInn);
        leftColumnLayout.add(new HorizontalLayout(innLabel, inn));

        Label descriptionLabel = new Label("Описание");
        descriptionLabel.setClassName("al-r");
        TextField description = new TextField();
        employeeDtoBinder.bind(description, EmployeeDto::getDescription, EmployeeDto::setDescription);
        leftColumnLayout.add(new HorizontalLayout(descriptionLabel, description));

        Span addImageSpan = new Span("Изображение");
        addImageSpan.setClassName("orange-span");
        Button addImageButton = new Button("Изображение", new Icon(VaadinIcon.PLUS));
        leftColumnLayout.add(addImageSpan, addImageButton);

        return leftColumnLayout;
    }

    private VerticalLayout getRightDataLayout() {
        VerticalLayout rightColumnLayout = new VerticalLayout();
        rightColumnLayout.setClassName("div-form-style");

        Span logInCrmSpan = new Span("Вход в CRM");
        logInCrmSpan.setClassName("orange-span");
        Checkbox allowLogIn = new Checkbox("Разрешить вход в систему");
        rightColumnLayout.add(logInCrmSpan, allowLogIn);

        Label loginLabel = new Label("Логин");
        loginLabel.setClassName("al-r");
        TextField loginTextField = new TextField();
        employeeDtoBinder.bind(loginTextField, EmployeeDto::getEmail, EmployeeDto::setEmail);
        rightColumnLayout.add(new HorizontalLayout(loginLabel, loginTextField));

        Label passwordLabel = new Label("Пароль");
        passwordLabel.setClassName("al-r");
        PasswordField passwordField = new PasswordField();
        passwordField.addClassName("width300");
        employeeDtoBinder.bind(passwordField, EmployeeDto::getPassword, EmployeeDto::setPassword);
        rightColumnLayout.add(new HorizontalLayout(passwordLabel, passwordField));

        Label departmentsLabel = new Label("Отдел");
        departmentsLabel.setClassName("al-r");
        Select<String> departmentSelect = new Select<>();
        departmentSelect.addClassName("width300");
        List<DepartmentDto> departmentDtoList = departmentService.getAll();
        List<String> departmentNames = departmentDtoList.stream()
                .map(DepartmentDto::getName).collect(Collectors.toList());
        departmentSelect.setItems(departmentNames);
        departmentSelect.addValueChangeListener(event -> {
            String tempName = event.getValue();
            for (DepartmentDto department : departmentDtoList) {
                if (department.getName().equals(tempName)) {
                    tempEmployee.setDepartment(department);
                    break;
                }
            }
        });

        rightColumnLayout.add(new HorizontalLayout(departmentsLabel, departmentSelect));

        Span rolesSpan = new Span("Системные роли");
        rolesSpan.setClassName("orange-span");
        rightColumnLayout.add(rolesSpan);

        RadioButtonGroup<List<Span>> rolesRadio = new RadioButtonGroup<>();

        Span adminExpanded = new Span("Неограниченный доступ ко всем документам, справочникам\n" +
                "и настройкам системы");
        adminExpanded.setClassName("radio-help-text");
        Span adminSpan = new Span("Администратор");
        List<Span> adminList = List.of(adminExpanded, adminSpan);

        Span userExpanded = new Span("Ограниченный доступ к документам и справочникам");
        userExpanded.setClassName("radio-help-text");
        Span userSpan = new Span("Пользователь");
        List<Span> userList = List.of(userExpanded, userSpan);

        List<List<Span>> radioList = List.of(adminList, userList);
        rolesRadio.setItems(radioList);
        rolesRadio.setRenderer(new ComponentRenderer<>(list -> {
            Span head = new Span(list.get(1));
            Span body = new Span(list.get(0));
            Div rendered = new Div(head, new Div(body));
            rendered.setClassName("radio-style");
            return rendered;
        }));

        rolesRadio.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        rolesRadio.setValue(userList);

        rolesRadio.addValueChangeListener(event -> {
            if (event.getValue().equals(adminList)) {
                tempEmployee.setRoles(Set.of(roleService.getById(1L)));
            } else {
                tempEmployee.setRoles(Set.of(roleService.getById(2L)));
            }
        });

        rightColumnLayout.add(rolesRadio);

        Span network = new Span("Сеть");
        network.setClassName("orange-span");
        rightColumnLayout.add(network);

        Span networkAddressSpan = new Span("Доступ только с адресов");
        networkAddressSpan.getElement().setAttribute("margin-top", "0px");
        rightColumnLayout.add(networkAddressSpan);

        Div emptyAddressesDiv = new Div();
        Button addIpAddress = new Button("IP адрес", new Icon(VaadinIcon.PLUS));
        addIpAddress.addClassName("add-ip-button");
        addIpAddress.addClickListener(event -> {
            Div ipTextBlock = new Div();
            ipTextBlock.addClassName("ip-block-margin");

            Integer[] tempIpArray = {0, 0, 0, 0};

            List<IntegerField> fieldList = List.of(new IntegerField(), new IntegerField(),
                    new IntegerField(), new IntegerField());
            fieldList.forEach(elem -> {
                elem.addClassName("short-byte-field");
                elem.setMax(255);
                elem.setMin(0);
                elem.setValue(0);
                elem.setAutocorrect(true);
            });

            int listIndex = (ipList.isEmpty() ? 0 : ipList.size());
            ipList.add(tempIpArray);

            fieldList.get(0).addValueChangeListener(changeEvent -> {
                tempIpArray[0] = changeEvent.getValue();
                ipList.set(listIndex, tempIpArray);
            });
            fieldList.get(1).addValueChangeListener(changeEvent -> {
                tempIpArray[1] = changeEvent.getValue();
                ipList.set(listIndex, tempIpArray);
            });
            fieldList.get(2).addValueChangeListener(changeEvent -> {
                tempIpArray[2] = changeEvent.getValue();
                ipList.set(listIndex, tempIpArray);
            });
            fieldList.get(3).addValueChangeListener(changeEvent -> {
                tempIpArray[3] = changeEvent.getValue();
                ipList.set(listIndex, tempIpArray);
            });

            Button hideIpBlock = new Button(new Icon(VaadinIcon.CLOSE));
            hideIpBlock.addClickListener(click -> {
                emptyAddressesDiv.remove(ipTextBlock);
                ipList.remove(listIndex);
            });

            ipTextBlock.add(new HorizontalLayout(fieldList.get(0),
                    fieldList.get(1), fieldList.get(2), fieldList.get(3), hideIpBlock));

            emptyAddressesDiv.add(ipTextBlock);
        });
        rightColumnLayout.add(emptyAddressesDiv, addIpAddress);

        Span networkSpan = new Span("Доступ только из сети");
        networkSpan.getElement().setAttribute("margin-top", "0px");
        rightColumnLayout.add(networkSpan);

        Div networkMap = new Div();
        networkMap.setVisible(false);

        Integer[] ipMask = {0, 0, 0, 0};
        Integer[] ipNetwork = {0, 0, 0, 0};

        Button addIpNetworkMapping = new Button("IP-сеть", new Icon(VaadinIcon.PLUS));
        addIpNetworkMapping.addClickListener(event -> {
            addIpNetworkMapping.setVisible(false);
            networkMap.setVisible(true);
            networkParams.add(ipNetwork);
            networkParams.add(ipMask);
        });
        rightColumnLayout.add(addIpNetworkMapping);

        Span networkLabel = new Span("Сеть");
        networkLabel.addClassName("stick-to-fields");


        List<IntegerField> fieldList = List.of(new IntegerField(), new IntegerField(), new IntegerField(),
                new IntegerField(), new IntegerField(), new IntegerField(), new IntegerField(), new IntegerField());
        fieldList.forEach(elem -> {
            elem.setClassName("short-byte-field");
            elem.setMin(0);
            elem.setMax(255);
            elem.setValue(0);
        });

        fieldList.get(0).addValueChangeListener(changeEvent -> {
            ipNetwork[0] = changeEvent.getValue();
            networkParams.set(0, ipNetwork);
        });
        fieldList.get(1).addValueChangeListener(changeEvent -> {
            ipNetwork[1] = changeEvent.getValue();
            networkParams.set(0, ipNetwork);
        });
        fieldList.get(2).addValueChangeListener(changeEvent -> {
            ipNetwork[2] = changeEvent.getValue();
            networkParams.set(0, ipNetwork);
        });
        fieldList.get(3).addValueChangeListener(changeEvent -> {
            ipNetwork[3] = changeEvent.getValue();
            networkParams.set(0, ipNetwork);
        });

        fieldList.get(4).addValueChangeListener(changeEvent -> {
            ipMask[0] = changeEvent.getValue();
            networkParams.set(1, ipMask);
        });
        fieldList.get(5).addValueChangeListener(changeEvent -> {
            ipMask[1] = changeEvent.getValue();
            networkParams.set(1, ipMask);
        });
        fieldList.get(6).addValueChangeListener(changeEvent -> {
            ipMask[2] = changeEvent.getValue();
            networkParams.set(1, ipMask);
        });
        fieldList.get(7).addValueChangeListener(changeEvent -> {
            ipMask[3] = changeEvent.getValue();
            networkParams.set(1, ipMask);
        });

        Span maskLabel = new Span("Маска");
        maskLabel.addClassName("stick-to-fields");
        Button closeNetworkMapping = new Button(new Icon(VaadinIcon.CLOSE));
        closeNetworkMapping.addClickListener(event -> {
            networkMap.setVisible(false);
            addIpNetworkMapping.setVisible(true);
            networkParams.clear();
        });

        HorizontalLayout networkIp = new HorizontalLayout(fieldList.get(0), fieldList.get(1),
                fieldList.get(2), fieldList.get(3));
        networkIp.addClassName("top-margin0");
        HorizontalLayout networkMask = new HorizontalLayout(fieldList.get(4), fieldList.get(5),
                fieldList.get(6), fieldList.get(7), closeNetworkMapping);
        networkMask.addClassName("top-margin0");

        VerticalLayout leftIpColumn = new VerticalLayout(networkLabel, networkIp);
        VerticalLayout rightIpColumn = new VerticalLayout(maskLabel, networkMask);

        networkMap.add(new HorizontalLayout(leftIpColumn, rightIpColumn));
        rightColumnLayout.add(networkMap);

        Span notifies = new Span("Уведомления");
        notifies.addClassName("orange-span");
        rightColumnLayout.add(notifies);

        List<String> notifiesLabels = List.of("Заказы покупателей",
                "Счета покупателей",
                "Остатки",
                "Розничная торговля",
                "Задачи",
                "Обмен данными",
                "Сценарии",
                "Интернет-магазины");

        Grid<String> table = new Grid<>();
        table.addComponentColumn(Span::new).setHeader("").setWidth("250px");
        table.addComponentColumn(elem -> new Checkbox()).setHeader("Почта");
        table.addComponentColumn(elem -> new Checkbox()).setHeader("Телефон");
        table.setHeightByRows(true);
        table.setItems(notifiesLabels);
        table.setId("table-grid");

        rightColumnLayout.add(table);
        return rightColumnLayout;
    }

    private void saveNewEmployee() {
        try {
            employeeDtoBinder.writeBean(tempEmployee);

            if (tempEmployee.getRoles() == null) {
                tempEmployee.setRoles(Set.of(roleService.getById(2L)));
            }

            tempEmployee.setPassword("{noop}" + tempEmployee.getPassword());

            if (!ipList.isEmpty()) {
                Set<IpAddressDto> ipAddressSet = ipList.stream()
                        .map(elem -> elem[0] + "." + elem[1] + "." + elem[2] + "." + elem[3])
                        .map(elem -> {
                            IpAddressDto temp = new IpAddressDto();
                            temp.setAddress(elem);
                            return temp;
                        })
                        .collect(Collectors.toSet());
                tempEmployee.setIpAddress(ipAddressSet);
            }

            if (!networkParams.isEmpty()) {
                IpNetworkDto ipNetworkDto = new IpNetworkDto();
                Integer[] network = networkParams.get(0);
                Integer[] mask = networkParams.get(1);
                ipNetworkDto.setNetwork(network[0] + "." + network[1] + "."
                        + network[2] + "." + network[3]);
                ipNetworkDto.setMask(mask[0] + "." + mask[1] + "."
                        + mask[2] + "." + mask[3]);
                tempEmployee.setIpNetwork(ipNetworkDto);
            }

            employeeService.create(tempEmployee);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }
}
