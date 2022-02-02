package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.impl.WarehouseServiceImpl;

import java.util.List;

@PageTitle("Склады")
@Route(value = "warehouse", layout = SettingsView.class)
public class WarahauseSettingsView extends VerticalLayout {
    private final TextField textField = new TextField();
    private final Grid<WarehouseDto> warehouseDtoGrid = new Grid<>(WarehouseDto.class, false);
    private final WarehouseServiceImpl warehouseService;
    private final HorizontalLayout formLayout;

    public WarahauseSettingsView(WarehouseServiceImpl warehouseService) {
        this.warehouseService = warehouseService;
        this.formLayout = ctreateNewWarehouse();
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, formLayout);
        warehouseDtoGridSet();
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textWarehous = new Label();
        textWarehous.setText("Склады");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addtextWarehousButton = new Button("Склад", new Icon(VaadinIcon.PLUS));
        addtextWarehousButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addtextWarehousButton.addClickListener(buttonClickEvent -> {
//      Добавить вызов страницы с формой добавления складов

        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addFilterButton.addClickListener(buttonClickEvent -> {
//      Вызов формы фильтра
            if(formLayout.isVisible()){
                formLayout.setVisible(false);
            } else {
                formLayout.setVisible(true);
            }
        });
        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
//      поисковая строка
        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        groupControl.add(helpButton, textWarehous, refreshButton, addtextWarehousButton,
                addFilterButton, searchField, editMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Переместить", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }

    private HorizontalLayout getSetting(){
        Icon cogIcon = new Icon(VaadinIcon.COG);
        Button settingButton = new Button(cogIcon);
        settingButton.addClickListener(buttonClickEvent -> {
//      Добавить страницу с формой вызова складов

        });

        HorizontalLayout setting = new HorizontalLayout();
        setting.add(settingButton);
        return setting;
    }

    private void warehouseDtoGridSet(){
        Grid.Column<WarehouseDto> idColumn = warehouseDtoGrid.addColumn(WarehouseDto::getId).setHeader("Id");
        Grid.Column<WarehouseDto> nameColumn = warehouseDtoGrid.addColumn(WarehouseDto::getName).setHeader("Склад");
        Grid.Column<WarehouseDto> addressColumn = warehouseDtoGrid.addColumn(WarehouseDto::getAddress).setHeader("Адрес");
        Grid.Column<WarehouseDto> commentToAddressColumn = warehouseDtoGrid.addColumn(WarehouseDto::getCommentToAddress).setHeader("Комментарии к адресу");
        Grid.Column<WarehouseDto> sortNumberColumn = warehouseDtoGrid.addColumn(WarehouseDto::getSortNumber).setHeader("№");

        warehouseDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Склад", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Адрес", addressColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарии", commentToAddressColumn);
        columnToggleContextMenu.addColumnToggleItem("№", sortNumberColumn);

        List<WarehouseDto> warehouseDtos = warehouseService.getAll();
        warehouseDtoGrid.setItems(warehouseDtos);

        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);

        add(warehouseDtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<WarehouseDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

    private HorizontalLayout ctreateNewWarehouse(){
        HorizontalLayout firstLine = new HorizontalLayout();

        Button findWarehousButton = new Button("Найти");
        findWarehousButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button clearWarehousButton = new Button("Очистить");
        clearWarehousButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        firstLine.add(findWarehousButton, clearWarehousButton);

        TextField nameWarehouse = new TextField("Наименование");
        TextField sortNumberWarehouse = new TextField("Код");
        TextField adressWarehouse = new TextField("Адрес");

        TextField ownerEmployee = new TextField("Владелец-сотрудник");
        TextField ownerDepartment = new TextField("Владелец-отдел");
        TextField generalAccess = new TextField("Общий доступ");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                nameWarehouse, sortNumberWarehouse,
                adressWarehouse, ownerEmployee, ownerDepartment, generalAccess
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);

        filterForm.setVisible(false);
        return filterForm;
    }

}
