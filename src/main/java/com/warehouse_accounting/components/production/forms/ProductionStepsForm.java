package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.Getter;
import lombok.Setter;

public class ProductionStepsForm extends VerticalLayout {

    private  Div parentLayer;
    private  Component returnLayer;
    private TextArea textArea;
    private final ProductionStageService productionStageService;
    private final EmployeeService employeeService;

    public ProductionStepsForm(
            Div parentLayer,
            Component returnLayer,
            ProductionStageService productionStageService,
            EmployeeService employeeService
    ) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.productionStageService = productionStageService;
        this.employeeService = employeeService;
        createButtons();
        text();
        createColumns();
    }


    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            parentLayer.removeAll();
            EmployeeDto employeeDto = employeeService.getById(1L);
            ProductionStageDto productionStageDto = ProductionStageDto.builder()
                    .name("Тест")
                    .description("Провека записи этапа")
                    .editorEmployeeId(employeeDto.getId())
                    .ownerEmployeeId(employeeDto.getId())
                    .ownerDepartmentId(employeeDto.getDepartment().getId())
                    .build();
            productionStageService.create(productionStageDto);
            parentLayer.add(returnLayer);
        });

        Button closeButton = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        HorizontalLayout editButton = getEditButton();

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show(
                "Элементы, перемещенные в архив, не отображаются в справочниках и отчетах. Архив позволяет скрывать неактуальные элементы, не удаляя их." +
                        "\n", 3000, Notification.Position.MIDDLE));


        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            System.out.println("перезагрузка");
        });


        Button archiveButton = new Button("Поместить в архив", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        buttons.add(saveButton, closeButton, editButton, helpButton, archiveButton );
        add(buttons);
    }

    private HorizontalLayout getEditButton(){
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private void text() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Text productionTasks = new Text("Этапы");

        horizontalLayout.add( productionTasks);
        add(horizontalLayout);
    }


    private void createColumns() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
//        Text productionTasks = new Text("Этапы");
        VerticalLayout firstColumn = new VerticalLayout();
        textArea = new TextArea();
        textArea.setWidth("44rem");
        textArea.setPlaceholder("Описание");

        firstColumn.add(createSubColumn("Наименование", "company"), textArea);

        horizontalLayout.add( firstColumn);
        add(horizontalLayout);
    }

    private HorizontalLayout createSubColumn(String title, String className) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName(className);
        Label label = new Label(title);
        TextField textField = new TextField();
        horizontalLayout.add(label, textField);
        return horizontalLayout;
    }
}
