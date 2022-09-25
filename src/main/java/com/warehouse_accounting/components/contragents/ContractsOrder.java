package com.warehouse_accounting.components.contragents;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.form.FormForContract;
import com.warehouse_accounting.components.contragents.grids.ContractsFilterLayout;
import com.warehouse_accounting.components.contragents.grids.ContractsGridLayout;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.services.interfaces.ContractService;

@SpringComponent
@UIScope
public class ContractsOrder extends VerticalLayout {

    private final ContractsGridLayout contractsGridLayout;

    private final ContractsFilterLayout filterLayout;

    private final FormForContract formForContract;

    private final HorizontalLayout buttons;

    private final transient ContractService contractService;

    public ContractsOrder(ContractsGridLayout contractsGridLayout, ContractsFilterLayout filterLayout,
                          FormForContract formForContract, ContractService contractService) {
        this.formForContract = formForContract;
        this.contractsGridLayout = contractsGridLayout;
        this.filterLayout = filterLayout;
        this.contractService = contractService;
        this.buttons = getGroupButton();
        this.formForContract.setParent(this);
        this.contractsGridLayout.setParent(this);

        add(buttons, filterLayout, contractsGridLayout);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("В договорах фиксируются ваши договоренности с покупателями " +
                "и поставщиками — по ним удобно искать документы в рамках " +
                "работы с определенными контрагентами и контролировать " +
                "взаиморасчеты." +
                "\n" +
                "Читать инструкцию: Договоры", 3000, Notification.Position.MIDDLE));

        Span contracts = new Span("Договоры");
        contracts.setClassName("pageTitle");

        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> contractsGridLayout.refreshDate());

        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button contract = new Button("Договор", image);
        contract.addClickListener(e -> {
            hideButtonEndGrid();
            formForContract.build();
            add(formForContract);
        });
        contract.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button filter = new Button("Фильтр"); // todo: ещё не сделано
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> filterLayout.setVisible(!filterLayout.isVisible()));

        TextField textField = new TextField();
        textField.setPlaceholder("Номер или комментарий");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        menuBar.addItem(count);

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);

        MenuItem edit = menuBar.addItem(editVision);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Поместить в архив").onEnabledStateChanged(false);
        editSubMenu.addItem("Извлечь из архива").onEnabledStateChanged(false);

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить...");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(printIcon,
                new Text("Печать"), caretDownPrint);
        printVision.setSpacing(false);

        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список договоров");
        printSubMenu.addItem("Договор").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить...");

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        horizontalLayout.add(helpButton, contracts, refreshButton, contract, filter, textField, menuBar, setting);

        return horizontalLayout;
    }

    public void showButtonEndGrid(Boolean refreshGrid) {
        buttons.setVisible(true);
        if (refreshGrid) {
            contractsGridLayout.refreshDate();
        }
        contractsGridLayout.setVisible(true);
    }

    public void hideButtonEndGrid() {
        buttons.setVisible(false);
        contractsGridLayout.setVisible(false);
        contractsGridLayout.setVisible(false);
    }

    public void editFormActivate(ContractDto contractDto) {
        formForContract.build(contractService.getById(contractDto.getId()));
        hideButtonEndGrid();
        add(formForContract);
    }
}
