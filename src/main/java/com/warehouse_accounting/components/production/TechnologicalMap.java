package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.forms.NewTechnologicalMapPanel;
import com.warehouse_accounting.components.production.notification.TechnologicalMapNotification;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@Log4j2
public class TechnologicalMap extends VerticalLayout {
    private final TextField textField = new TextField();
    private Grid<TechnologicalMapDto> technologicalMapDtoGrid = new Grid<>(TechnologicalMapDto.class, false);
    private final TechnologicalMapService technologicalMapService;
    private final HorizontalLayout formLayout;
    private final NewTechnologicalMapPanel newTechnologicalMapPanel;
    private final HorizontalLayout groupButtons;
    private final MenuBar editMenuBar;
    private final List<Long> list;
    private final TechnologicalMapNotification notifications;
    private HorizontalLayout headerLayout;

    public TechnologicalMap(TechnologicalMapService technologicalMapService, NewTechnologicalMapPanel newTechnologicalMapPanel, TechnologicalMapNotification notification) {
        this.technologicalMapService = technologicalMapService;
        this.newTechnologicalMapPanel = newTechnologicalMapPanel;
        this.notifications = notification;
        this.editMenuBar = initMenuBar();
        this.formLayout = createNewTechnologicalMap();
        groupButtons = getGroupButtons();
        add(groupButtons, formLayout);
        technologicalMapDtoGridSet();
        list = new ArrayList<>();
        this.newTechnologicalMapPanel.getLayout(this);
    }

    public void init() {
        removeAll();
        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, createNewTechnologicalMap());
        technologicalMapDtoGrid = new Grid<>(TechnologicalMapDto.class, false);
        technologicalMapDtoGridSet();
        newTechnologicalMapPanel.getLayout(this);
    }

    public void initNew() {
        removeAll();
        add(newTechnologicalMapPanel);
        setSizeFull();
    }

    public void initNew(TechnologicalMapDto technologicalMapDto) {
        removeAll();
        newTechnologicalMapPanel.getLayout(this, technologicalMapDto);
        add(newTechnologicalMapPanel);
        setSizeFull();
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> {
            helpButton.setEnabled(false);
            Notification notification = notifications.show();
            notification.addDetachListener(detachEvent -> helpButton.setEnabled(true));
        });

        Label textTechnologicalMap = new Label();
        textTechnologicalMap.setText("Техкарты");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), buttonClickEvent -> {
            remove(technologicalMapDtoGrid);
            remove(headerLayout);
            technologicalMapDtoGridSet();
        });
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addTextTechnologicalMapButton = new Button("Техкарта", new Icon(VaadinIcon.PLUS));
        addTextTechnologicalMapButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addTextTechnologicalMapButton.addClickListener(buttonClickEvent -> {
            initNew();
        });

        Button addTextTechnologicalMapGroupButton = new Button("Группа", new Icon(VaadinIcon.PLUS));
        addTextTechnologicalMapGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addTextTechnologicalMapGroupButton.addClickListener(buttonClickEvent -> {
            //TODO: функционал добавления групп
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
            //TODO: функционал поиска
        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        groupControl.add(helpButton, textTechnologicalMap, refreshButton, addTextTechnologicalMapButton,
                addTextTechnologicalMapGroupButton, addFilterButton, searchField, editMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");
        textField.addValueChangeListener(textFieldStringComponentValueChangeEvent -> disableEnableElement());
        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private void disableEnableElement() {
        if (Integer.parseInt(textField.getValue()) > 0) {
            editMenuBar.getItems().get(0).getSubMenu().getItems().get(0).getElement().setAttribute("disabled", false);
        } else {
            editMenuBar.getItems().get(0).getSubMenu().getItems().get(0).getElement().setAttribute("disabled", true);
        }
    }

    private MenuBar initMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            for (Long l : list) {
                technologicalMapService.deleteById(l);
            }
            list.removeAll(list);
            init();
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {
            //TODO: Массовое редактирование
        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {
            //TODO: Поместить в архив
        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {
            //TODO: Извлечь из архива
        }).getElement().setAttribute("disabled", true);
        return editMenuBar;
    }

    private HorizontalLayout getSetting() {
        Icon cogIcon = new Icon(VaadinIcon.COG);
        Button settingButton = new Button(cogIcon);
        settingButton.addClickListener(buttonClickEvent -> {
            //TODO: функционал настроек
        });
        HorizontalLayout setting = new HorizontalLayout();
        setting.add(settingButton);
        return setting;
    }

    private void technologicalMapDtoGridSet() {
        technologicalMapDtoGrid = new Grid<>(TechnologicalMapDto.class, false);

        Grid.Column<TechnologicalMapDto> idColumn = technologicalMapDtoGrid
                .addColumn(TechnologicalMapDto::getId).setHeader("Id");
        Grid.Column<TechnologicalMapDto> nameColumn = technologicalMapDtoGrid
                .addColumn(TechnologicalMapDto::getName).setHeader("Наименование");
        Grid.Column<TechnologicalMapDto> expensesColumn = technologicalMapDtoGrid
                .addColumn(TechnologicalMapDto::getProductionCost).setHeader("Затраты на производство");
        Grid.Column<TechnologicalMapDto> commentColumn = technologicalMapDtoGrid
                .addColumn(TechnologicalMapDto::getComment).setHeader("Комментарий");
        Grid.Column<TechnologicalMapDto> editColumn = technologicalMapDtoGrid.addComponentColumn(technologicalMapDtoEdit -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                initNew(technologicalMapDtoEdit);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        GridMultiSelectionModel<TechnologicalMapDto> selectionModel =
                (GridMultiSelectionModel<TechnologicalMapDto>) technologicalMapDtoGrid
                        .setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.addMultiSelectionListener(multiSelectionEvent -> {
            tempEvent(multiSelectionEvent.getAddedSelection());
            tempDeleteEvent(multiSelectionEvent.getRemovedSelection());
        });

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        TechnologicalMap.ColumnToggleContextMenu columnToggleContextMenu = new TechnologicalMap
                .ColumnToggleContextMenu(menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Наименование", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Затраты на производство", expensesColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);

        List<TechnologicalMapDto> technologicalMapDtoList = technologicalMapService.getAll();
        technologicalMapDtoGrid.setItems(technologicalMapDtoList);

        headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(technologicalMapDtoGrid, headerLayout);
    }

    private void tempEvent(Set<TechnologicalMapDto> selectedItems) {
        for (TechnologicalMapDto t : new ArrayList<>(selectedItems)
        ) {
            list.add(t.getId());
            textField.setValue(String.valueOf(list.size()));
        }
    }

    private void tempDeleteEvent(Set<TechnologicalMapDto> selectedItems) {
        for (TechnologicalMapDto t : new ArrayList<>(selectedItems)
        ) {
            list.remove(t.getId());
            textField.setValue(String.valueOf(list.size()));
        }
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
