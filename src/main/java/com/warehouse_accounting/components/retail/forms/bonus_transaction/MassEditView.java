package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.ContractorDto;
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
    private final Button continueButton;
    private Span spanSelectedItems;
    private int sizeSpan;
    private ComboBox<EmployeeDto> employeeBox; //Todo поменять на селект
    private ComboBox<DepartmentDto> departmentBox; //Todo поменять на селект
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    public MassEditView(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;

        setVisible(false);
        closeButton = silverButton.buttonBlank("Закрыть");
        continueButton = silverButton.buttonBlank("Далее");
        add(
                closeButton,
                titleLine(),
                labelLine(),
                dataSelectLayout(),
                continueButton

                );
    }

    private HorizontalLayout titleLine() {
        HorizontalLayout layout = new HorizontalLayout();
        Button button = silverButton.buttonHelp();
        Span title = new Span("Массовое редактирование: Операции с баллами");


        layout.add(button, title);
        return layout;
    }

    private HorizontalLayout labelLine() {
        sizeSpan = 5;//Заглушка
        HorizontalLayout layout = new HorizontalLayout();
        spanSelectedItems = new Span(String.format("Выбрано %d документов", sizeSpan));

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
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        Span spanEmployee =  new Span("Владелец-сотрудник");
        Span spanDepartment =  new Span("Владелец-отдел");
        Span spanAccess = new Span("Общий доступ");
        employeeBox = new ComboBox<>();
        departmentBox = new ComboBox<>();
        employeeBox.setItems(employeeService.getAll());
        departmentBox.setItems(departmentService.getAll());
        employeeBox.setItemLabelGenerator(EmployeeDto::getFirstName);
        departmentBox.setItemLabelGenerator(DepartmentDto::getName);

        employeeBox.setWidth("250px");
        departmentBox.setWidth("250px");
        spanEmployee.setWidth("200px");
        spanDepartment.setWidth("200px");
        spanAccess.setWidth("200px");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItems("Да", "Нет");



        ownerLine.add(
                new Checkbox(),
                spanEmployee,
                employeeBox
        );

        departmentLine.add(
                new Checkbox(),
                spanDepartment,
                departmentBox
        );

        generalAccessLine.add(
                new Checkbox(),
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


}
