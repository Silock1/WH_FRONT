package com.warehouse_accounting.components.tasks;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.warehouse_accounting.components.tasks.filter.TasksFilter;
import com.warehouse_accounting.components.tasks.forms.TasksEditForm;
import com.warehouse_accounting.components.tasks.forms.TasksForm;
import com.warehouse_accounting.components.tasks.grids.TasksGrid;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.TasksService;


public class Tasks extends VerticalLayout {
    private TasksGrid tasksGrid;
    private TasksFilter filterLayout;
    private final Div parentLayer;
    private final TextField textFieldGridSelected = new TextField();
    private EmployeeService employeeService;

    private ContractorService contractorService;

    public Tasks(Div parentLayer, TasksService tasksService, TasksFilter filterLayout
            , EmployeeService employeeService, ContractorService contractorService) {
        this.parentLayer = parentLayer;
        this.filterLayout = filterLayout;
        this.employeeService = employeeService;
        this.contractorService = contractorService;
        tasksGrid = new TasksGrid(tasksService, employeeService);
        Div pageContent = new Div();
        pageContent.add(tasksGrid);
        filterLayout.setTasks(this);//оно
        pageContent.setSizeFull();
        add(getGroupButtons(), filterLayout, pageContent);
    }

    public void hideTasksGrid(boolean filter) {
        tasksGrid.setVisible(filter);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Задачи");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Задача", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addOrderButton.addClickListener(buttonClickEvent -> {
            TasksForm tasksForm = new TasksForm(parentLayer, this
                    , employeeService, contractorService);
            parentLayer.removeAll();
            parentLayer.add(tasksForm);

        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e -> filterLayout.setVisible(!filterLayout.isVisible()));

        TextField searchField = new TextField();
        searchField.setPlaceholder("Задача или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        HorizontalLayout createMenuBar = getCreateMenuBar();

        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
                addFilterButton, searchField, createMenuBar);
        setSizeFull();
        return groupControl;
    }


    private HorizontalLayout getCreateMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar createMenuBar = new MenuBar();
        createMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Создать"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        HorizontalLayout groupCreate = new HorizontalLayout();
        groupCreate.add(createMenuBar);
        groupCreate.setSpacing(false);
        groupCreate.setAlignItems(Alignment.CENTER);
        return groupCreate;
    }


}
