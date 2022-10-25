package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import lombok.Getter;
import lombok.Setter;

@Route("massedit")
@Getter
@Setter
@CssImport(value = "./css/application.css")
public class MassEditView extends VerticalLayout {
    private SilverButton silverButton = new SilverButton();
    private final Button closeButton;
    private Button editButton;
    private Span spanSelectedItems = new Span();
    private Select<EmployeeDto> employeeSelect = new Select<>();
    private Select<DepartmentDto> departmentSelect = new Select<>();
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    private Checkbox employeeBox = new Checkbox();
    private Checkbox departmentBox = new Checkbox();
    private Checkbox generalAccessBox = new Checkbox();
    private boolean employeeBoxFlag;
    private boolean generalAccessBoxFlag;
    private boolean departmentBoxFLag;


    public MassEditView(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;

        setVisible(false);


        closeButton = silverButton.buttonBlank("Закрыть");
        editButton = silverButton.greenButton("Редактировать");
        disableEditButton();
        add(
                closeButton,
                titleLine(),
                labelLine(),
                dataSelectLayout(),
                editButton
        );


    }


    private HorizontalLayout titleLine() {
        HorizontalLayout layout = new HorizontalLayout();
        Button button = silverButton.buttonHelp();
        Span title = new Span("Массовое редактирование: Операции с баллами");
        title.setClassName("miniTitle");

        layout.add(button, title);
        return layout;
    }

    private HorizontalLayout labelLine() {

        HorizontalLayout layout = new HorizontalLayout();


        spanSelectedItems.getElement().getStyle().set("border", "solid 1px #e2f4ff");
        spanSelectedItems.getElement().getStyle().set("background-color", "#ecf8ff");


        layout.add(
                new Span("Настройка параметров"),
                spanSelectedItems

        );

        return layout;
    }

    private VerticalLayout dataSelectLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout ownerLine = new HorizontalLayout();
        HorizontalLayout departmentLine = new HorizontalLayout();
        HorizontalLayout generalAccessLine = new HorizontalLayout();
        Span spanEmployee = new Span("Владелец-сотрудник");
        Span spanDepartment = new Span("Владелец-отдел");
        Span spanAccess = new Span("Общий доступ");
        employeeSelect.setItems(employeeService.getAll());
        departmentSelect.setItems(departmentService.getAll());
        employeeSelect.setValue(employeeService.getPrincipalManually());
        departmentSelect.setValue(departmentService.getById(2L));

        employeeSelect.setItemLabelGenerator(EmployeeDto::getFirstName);
        departmentSelect.setItemLabelGenerator(DepartmentDto::getName);

        employeeSelect.setWidth("250px");
        departmentSelect.setWidth("250px");
        spanEmployee.setWidth("200px");
        spanDepartment.setWidth("200px");
        spanAccess.setWidth("200px");

        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItems("Да", "Нет");
        radioGroup.setValue("Нет");

        generalAccessBox.addValueChangeListener(event -> {
            generalAccessBoxFlag = event.getValue();
            disableEditButton();
        });

        employeeBox.addValueChangeListener(event -> {
            employeeBoxFlag = event.getValue();
            disableEditButton();

        });

        departmentBox.addValueChangeListener(event -> {
            departmentBoxFLag = event.getValue();
            disableEditButton();

        });

        ownerLine.add(
                employeeBox,
                spanEmployee,
                employeeSelect
        );

        departmentLine.add(
                departmentBox,
                spanDepartment,
                departmentSelect
        );

        generalAccessLine.add(
                generalAccessBox,
                spanAccess,
                radioGroup
        );


        verticalLayout.add(
                ownerLine,
                departmentLine,
                generalAccessLine

        );

        return verticalLayout;
    }

    private void disableEditButton() {
        if (!employeeBoxFlag && !departmentBoxFLag && !generalAccessBoxFlag) {
            editButton.setEnabled(false);
            editButton.getElement().getStyle().set("opacity", "0.5");
        } else {
            editButton.setEnabled(true);
            editButton.getElement().getStyle().set("opacity", "1.0");
        }
    }

    public void clearCheckBoxes() {
        employeeBox.setValue(false);
        departmentBox.setValue(false);
        generalAccessBox.setValue(false);
    }

}
