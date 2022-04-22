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
import com.warehouse_accounting.components.production.forms.ProductionStepsForm;
import com.warehouse_accounting.components.production.grids.ProductionStepsGridLayout;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProductionSteps extends VerticalLayout {
    private final ProductionStageService productionStageService;
    private final EmployeeService employeeService;
    private ProductionStepsGridLayout productionStepsGridLayout;
    private final TextField textFieldGridSelected = new TextField();
    @Setter
    @Getter
    private Div parentLayer;

    public ProductionSteps(ProductionStageService productionStageService, EmployeeService employeeService) {
        this.productionStageService = productionStageService;
        this.employeeService = employeeService;
        productionStepsGridLayout = new ProductionStepsGridLayout(productionStageService);
        Div pageContent = new Div();
        pageContent.add(productionStepsGridLayout);
        pageContent.setSizeFull();
        add(getGroupButton(), pageContent);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show(
                "Этап — часть техпроцесса. В технологической карте по этапам четко прописываются " +
                        "требования к материалам и затраты на производство. Одни и те же этапы могут быть включены в разные техпроцессы." +
                        "\n" +
                        "Читать инструкцию: Расширенный учет производственных операций", 3000, Notification.Position.MIDDLE));

        Text productionTasks = new Text("Этапы");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            System.out.println("перезагрузка");
        });

        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button addStepsButton = new Button("Этап", image);
        addStepsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);


        addStepsButton.addClickListener(buttonClickEvent -> {
            ProductionStepsForm productionStepsForm = new ProductionStepsForm(parentLayer, this, productionStageService, employeeService);
            parentLayer.removeAll();
            parentLayer.add(productionStepsForm);
        });


        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        TextField textField = new TextField();
        textField.setPlaceholder("Наменование или описание");
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
        editSubMenu.addItem("Поместить в архив").onEnabledStateChanged(false);
        editSubMenu.addItem("Извлечь из архива").onEnabledStateChanged(false);

        horizontalLayout.add(helpButton, productionTasks, refreshButton, addStepsButton, filter, textField, menuBar);

        return horizontalLayout;

    }
}
