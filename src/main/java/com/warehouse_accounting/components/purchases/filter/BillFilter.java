package com.warehouse_accounting.components.purchases.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.purchases.AccountsPayable;
import com.warehouse_accounting.components.purchases.grids.AccountsPayableGridLayout;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import lombok.Setter;

@SpringComponent
@UIScope
public class BillFilter extends VerticalLayout {
    private AccountsPayableGridLayout gridLayout;
    private EmployeeService employeeService;
    @Setter
    private AccountsPayable accountsPayable;

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
}
