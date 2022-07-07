package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.forms.TechnologicalMapForm;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@SpringComponent
@UIScope
@Log4j2

public class TechnologicalMap extends VerticalLayout {
    private final TextField textField = new TextField();
    private final Grid<TechnologicalMapDto> technologicalMapDtoGrid = new Grid<>(TechnologicalMapDto.class, false);
    private final TechnologicalMapService technologicalMapService;
    private final HorizontalLayout formLayout;

    public TechnologicalMap(TechnologicalMapService technologicalMapService) {
        this.technologicalMapService = technologicalMapService;
        this.formLayout = createNewTechnologicalMap();
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, formLayout);
        technologicalMapDtoGridSet();
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> {
            Notification.show("" +
                            "Технологическая карта описывает состав изделия - \n" +
                            "с комплектующими, сырьем и материалами. Технологическая карта\n" +
                            "может использоваться как в базовом, так и расширенном способе\n" +
                            "производства\n" +
                            "\n" +
                            "Читать инструкцию\n"
                    , 5000, Notification.Position.TOP_START);
        });

        Label textTechnologicalMap = new Label();
        textTechnologicalMap.setText("Техкарты");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);


        Button addTextTechnologicalMapButton = new Button("Техкарты", new Icon(VaadinIcon.PLUS));
        addTextTechnologicalMapButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addTextTechnologicalMapButton.addClickListener(buttonClickEvent -> {
//            UI.getCurrent().navigate(TechnologicalMapForm.class);
        });

        Button addtextTechnologicalMapGroupButton = new Button("Группа", new Icon(VaadinIcon.PLUS));
        addtextTechnologicalMapGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addtextTechnologicalMapGroupButton.addClickListener(buttonClickEvent -> {
//            UI.getCurrent().navigate(TechnologicalMapGroupForm.class);
        });


        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> {
            if (formLayout.isVisible()) {
                formLayout.setVisible(false);
            } else {
                formLayout.setVisible(true);
            }
        });

        TextField searchField = new TextField();
        searchField.setPlaceholder("Наименование или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        groupControl.add(helpButton, textTechnologicalMap, refreshButton, addTextTechnologicalMapButton,
                addtextTechnologicalMapGroupButton, addFilterButton, searchField, editMenuBar, setting);
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

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
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

    private HorizontalLayout getSetting() {
        Icon cogIcon = new Icon(VaadinIcon.COG);
        Button settingButton = new Button(cogIcon);
        settingButton.addClickListener(buttonClickEvent -> {
        });
        HorizontalLayout setting = new HorizontalLayout();
        setting.add(settingButton);
        return setting;
    }

    private void technologicalMapDtoGridSet() {
        Grid.Column<TechnologicalMapDto> idColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getId).setHeader("Id");

        Grid.Column<TechnologicalMapDto> nameColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getName).setHeader("Фамилия");
        Grid.Column<TechnologicalMapDto> expensesColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getProductionCost).setHeader("Фамилия");
        Grid.Column<TechnologicalMapDto> commentColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getComment).setHeader("Имя");

        technologicalMapDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        TechnologicalMap.ColumnToggleContextMenu columnToggleContextMenu = new TechnologicalMap.ColumnToggleContextMenu(menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Наименование", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Затраты на производство", expensesColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);

        List<TechnologicalMapDto> technologicalMapDtoList = technologicalMapService.getAll();
        technologicalMapDtoGrid.setItems(technologicalMapDtoList);

        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);

        add(technologicalMapDtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<TechnologicalMapDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

    private HorizontalLayout createNewTechnologicalMap() {
        HorizontalLayout firstLine = new HorizontalLayout();

        Button findTechnoligicalMapButton = new Button("Найти");
        findTechnoligicalMapButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button clearTechnoligicalMapButton = new Button("Очистить");
        clearTechnoligicalMapButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        firstLine.add(findTechnoligicalMapButton, clearTechnoligicalMapButton);

        TextField nameTechnoligicalMap = new TextField("Наименование");
        TextField productionTechnoligicalMap = new TextField("Продукция");
        TextField materialTechnoligicalMap = new TextField("Материал");
        TextField showTechnoligicalMap = new TextField("Показывать");
        TextField ownerEmployeeTechnoligicalMap = new TextField("Владелец-сотрудник");
        TextField ownerDepartmentTechnoligicalMap = new TextField("Владелец-отдел");
        TextField generalAccessTechnoligicalMap = new TextField("Общий доступ");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                nameTechnoligicalMap, productionTechnoligicalMap,
                materialTechnoligicalMap, showTechnoligicalMap, ownerEmployeeTechnoligicalMap,
                ownerDepartmentTechnoligicalMap, generalAccessTechnoligicalMap
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);

        filterForm.setVisible(false);
        return filterForm;
    }

}
