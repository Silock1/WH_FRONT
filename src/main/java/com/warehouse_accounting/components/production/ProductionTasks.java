package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
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
import com.warehouse_accounting.components.production.forms.ProductionTasksForm;
import com.warehouse_accounting.components.production.grids.ProductionTasksFilterLayout;
import com.warehouse_accounting.components.production.grids.ProductionTasksGridLayout;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.Getter;
import lombok.Setter;

@SpringComponent
@UIScope
public class ProductionTasks extends VerticalLayout {

    private final transient ProductionTasksService productionTasksService;

    private final transient WarehouseService warehouseService;

    private final ProductionTasksFilterLayout productionTasksFilterLayout;

    @Getter
    private final ProductionTasksGridLayout productionTasksGridLayout;

    @Getter
    @Setter
    private Div mainContent;

    public ProductionTasks(ProductionTasksFilterLayout productionTasksFilterLayout,
                           ProductionTasksGridLayout productionTasksGridLayout,
                           ProductionTasksService productionTasksService,
                           WarehouseService warehouseService) {
        this.productionTasksFilterLayout = productionTasksFilterLayout;
        this.productionTasksGridLayout = productionTasksGridLayout;
        this.productionTasksService = productionTasksService;
        this.warehouseService = warehouseService;

        mainContent = new Div();
        mainContent.setSizeFull();
        mainContent.add(getGroupButton(), this.productionTasksFilterLayout, this.productionTasksGridLayout);
        add(mainContent);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Производственное задание позволяет выстроить процесс изготовления" +
                        "продукции по техкартам и рассчитать расходы. Тут же можно отмечать выполнение производственных этапов" +
                        "и контролировать результаты."+
                "\n" +
                "Читать инструкцию: Расширенный учет производственных операций" + "Видео: Расширенный способ", 5000, Notification.Position.MIDDLE));

        Text productionTasks = new Text("Производственные задания");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> productionTasksGridLayout.updateGrid());
        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button exercise = new Button("Задание", image);
        exercise.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        exercise.addClickListener(click -> add(new ProductionTasksForm(this,
                ProductionTasksDto.builder()
                        .taskId(1L)
                        .productionWarehouseId(1L)
                        .materialWarehouseId(1L)
                        .editEmployeeId(1L)
                        .ownerDepartmentId(1L)
                        .ownerEmployeeId(1L)
                        .isAccessed(true).build(),
                productionTasksService,
                warehouseService)));

        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> productionTasksFilterLayout.setVisible(!productionTasksFilterLayout.isVisible()));

        TextField textField = new TextField();
        textField.setPlaceholder("Номер и комментарий");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        MenuBar menuBar = new MenuBar(); // Создаем меню бар
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        menuBar.addItem(count); // Добавим поле с кол-вом

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
        editSubMenu.addItem("Провести").onEnabledStateChanged(false);
        editSubMenu.addItem("Снять проведение").onEnabledStateChanged(false);

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"), printIcon, caretDownPrint);
        printVision.setSpacing(false);

        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список производственных заданий");
        printSubMenu.addItem("Производственное задание").onEnabledStateChanged(false);
        printSubMenu.addItem("Комплект").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить");

        Button setting = new Button(new Icon(VaadinIcon.COG)); // Настройки
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);


        horizontalLayout.add(helpButton, productionTasks, refreshButton, exercise, filter, textField, menuBar,
                setting);

        return horizontalLayout;
    }


}
