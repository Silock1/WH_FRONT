package com.warehouse_accounting.components.goods.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;

import java.util.List;

@SpringComponent
@UIScope
public class GoodsFilter extends VerticalLayout {

    private final ProductGroupService productGroupService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ContractorService contractorService;

    public GoodsFilter(ProductGroupService productGroupService,
                       EmployeeService employeeService,
                       DepartmentService departmentService,
                       ContractorService contractorService) {
        this.productGroupService = productGroupService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.contractorService = contractorService;
        HorizontalLayout one = getOne();
        HorizontalLayout two = getTwo();
        HorizontalLayout three = getThree();
        HorizontalLayout four = getFour();
        add(one,two, three, four);
        setSizeFull();
        setVisible(false);
        cleanAll();
    }

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");
    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
    TextField nameOfProduct = new TextField("Наименование");
    TextField description = new TextField("Описание");
    TextField vendorCode = new TextField("Артикул");
    TextField code = new TextField("Код");

    TextField externalCode = new TextField("Внешний код");
    TextField barcode = new TextField("Штрихкод");
    TextField egais = new TextField("Код ЕГАИС");
    ComboBox<String> weightGoods = new ComboBox<>("Весовой товар", "Да", "Нет");
    ComboBox<String> type = new ComboBox<>("Тип", "Все", "Товары", "Услуги", "Комплекты");

    ComboBox <String> show = new ComboBox<>("Показывать", "Только обычные", "Только архивные", "Все");
    TextField productGroup = new TextField("Группа товаров (без подгрупп)");
    ComboBox<ProductGroupDto> productGroup2 = new ComboBox<>("Группа товаров");
    ComboBox<ContractorDto> contractor = new ComboBox<>("Поставщик");
    ComboBox<EmployeeDto> ownerEmployee = new ComboBox<>("Владелец-сотрудник");

    ComboBox<DepartmentDto> ownerDepartment = new ComboBox<>("Владелец-отдел");
    ComboBox<String> access = new ComboBox<>("Общий доступ", "Да", "Нет");
    DatePicker start = new DatePicker("Когда изменен:");
    DatePicker end = new DatePicker("до");
    ComboBox<EmployeeDto> changed = new ComboBox<>("Кто изменил");

    private void cleanAll(){
        clear.addClickListener(e -> {
            nameOfProduct.clear(); description.clear(); vendorCode.clear(); code.clear(); externalCode.clear();
            barcode.clear(); egais.clear(); weightGoods.clear(); type.clear();show.clear(); productGroup.clear();
            productGroup2.clear(); contractor.clear(); ownerEmployee.clear();ownerDepartment.clear(); access.clear();
            start.clear(); end.clear(); changed.clear();
        });
    }

    private HorizontalLayout getOne() {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(find,clear,settingFilter,bookmarks,nameOfProduct,description,vendorCode,code);
        return horizontalLayout;
    }

    private HorizontalLayout getTwo(){
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(externalCode, barcode, egais, weightGoods, type);
        return horizontalLayout;
    }

    private HorizontalLayout getThree(){
        var horizontalLayout = new HorizontalLayout();

        List<ProductGroupDto> productGroupDtos = productGroupService.getAll();
        productGroup2.setItems(productGroupDtos);
        productGroup2.setItemLabelGenerator(ProductGroupDto::getName);

        List<ContractorDto> contractorDtos = contractorService.getAll();
        contractor.setItems(contractorDtos);
        contractor.setItemLabelGenerator(ContractorDto::getName);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ownerEmployee.setItems(employeeDtos);
        ownerEmployee.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(show, productGroup, productGroup2, contractor, ownerEmployee);
        return horizontalLayout;
    }

    private HorizontalLayout getFour(){
        var horizontalLayout = new HorizontalLayout();

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ownerDepartment.setItems(departmentDtos);
        ownerDepartment.setItemLabelGenerator(DepartmentDto::getName);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        changed.setItems(employeeDtos);
        changed.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(ownerDepartment, access, start, end, changed);
        return horizontalLayout;
    }
}
