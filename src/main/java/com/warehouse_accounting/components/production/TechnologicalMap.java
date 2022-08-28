package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.services.interfaces.AttributeOfCalculationObjectService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.TaxSystemService;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import com.warehouse_accounting.services.interfaces.UnitService;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;
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
    private final HorizontalLayout groupButtons;

    private final MenuBar editMenuBar;
    private List<Long> list;


    private final ProductService productService;
    private final CountryService countryService;
    private final UnitsOfMeasureService unitsOfMeasureService;
    private final ContractorService contractorService;
    private final TaxSystemService taxSystemService;
    private final ImageService imageService;
    private final ProductGroupService productGroupService;
    private final AttributeOfCalculationObjectService attributeService;
    private final UnitService unitService;

    public TechnologicalMap(TechnologicalMapService technologicalMapService, ProductService productService, CountryService countryService, UnitsOfMeasureService unitsOfMeasureService, ContractorService contractorService, TaxSystemService taxSystemService, ImageService imageService, ProductGroupService productGroupService, AttributeOfCalculationObjectService attributeService, UnitService unitService) {
        this.technologicalMapService = technologicalMapService;
        this.productService = productService;
        this.countryService = countryService;
        this.unitsOfMeasureService = unitsOfMeasureService;
        this.contractorService = contractorService;
        this.taxSystemService = taxSystemService;
        this.imageService = imageService;
        this.productGroupService = productGroupService;
        this.attributeService = attributeService;
        this.unitService = unitService;
        this.editMenuBar = initMenuBar();
        this.formLayout = createNewTechnologicalMap();
        groupButtons = getGroupButtons();
        add(groupButtons, formLayout);
        technologicalMapDtoGridSet();
        list = new ArrayList<>();

    }

    public void init() {

        HorizontalLayout groupButtons = getGroupButtons();
        add(groupButtons, createNewTechnologicalMap());
        technologicalMapDtoGrid = new Grid<>(TechnologicalMapDto.class, false);
        technologicalMapDtoGridSet();
    }

    public void initNew() {
        HorizontalLayout groupButtons = getButton();
        add(groupButtons);
    }

    private HorizontalLayout getButton() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Label textTechnologicalMap = new Label();
        textTechnologicalMap.setText("Техкарты");


        Button addTextTechnologicalMapButton = new Button("Техкарты", new Icon(VaadinIcon.MINUS));
        addTextTechnologicalMapButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addTextTechnologicalMapButton.addClickListener(buttonClickEvent -> {
            removeAll();
            init();



//            NewTechnologicalMapPanel newTechnologicalMapPanel = new NewTechnologicalMapPanel();
//            newTechnologicalMapPanel.setOnCloseHandler(() -> initPage());


            //   UI.getCurrent().navigate(String.valueOf(TechnologicalMapForm.class));
        });
        Button addGoodsButton = new Button("Новый товар", new Icon(VaadinIcon.MINUS));
        addGoodsButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addGoodsButton.addClickListener(addGoodsButtonClickEvent -> {
                    Dialog dialog = new Dialog();
                    GoodsForm goodsForm = new GoodsForm(productService, countryService, unitsOfMeasureService,
                            contractorService, taxSystemService, imageService, productGroupService, attributeService,
                            unitService);
            DocumentOperationsToolbar menu = new DocumentOperationsToolbar();
            menu.setCloseHandler(() -> { dialog.close();
                        });
            menu.setSaveHandler(() -> { goodsForm.save();
                dialog.close();
                        });
dialog.add(menu);
            dialog.add(goodsForm);
            dialog.setSizeFull();
            dialog.open();
                });

        groupControl.add(textTechnologicalMap, addTextTechnologicalMapButton, addGoodsButton);
        setSizeFull();
        return groupControl;
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
            removeAll();
            initNew();

//            NewTechnologicalMapPanel newTechnologicalMapPanel = new NewTechnologicalMapPanel();
//            newTechnologicalMapPanel.setOnCloseHandler(() -> initPage());


            //   UI.getCurrent().navigate(String.valueOf(TechnologicalMapForm.class));
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

        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");
        textField.addValueChangeListener(textFieldStringComponentValueChangeEvent -> disableEnableElement());

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }

    private void disableEnableElement() {
        if (Integer.parseInt(textField.getValue())> 0) {
            editMenuBar.getItems().get(0).getSubMenu().getItems().get(0).getElement().setAttribute("disabled", false);
        }
      else {
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
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> { log.info("deleting");
            for (Long l: list
                 ) {
                technologicalMapService.deleteById(l);
            }
            list.removeAll(list);
            log.info(list);
            removeAll();
            init();

        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        return editMenuBar;
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

        Grid.Column<TechnologicalMapDto> nameColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getName).setHeader("Наименование");
        Grid.Column<TechnologicalMapDto> expensesColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getProductionCost).setHeader("Затраты на производство");
        Grid.Column<TechnologicalMapDto> commentColumn = technologicalMapDtoGrid.addColumn(TechnologicalMapDto::getComment).setHeader("Комментарий");


        //technologicalMapDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //
        GridMultiSelectionModel<TechnologicalMapDto> selectionModel =
                (GridMultiSelectionModel<TechnologicalMapDto>) technologicalMapDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        //  technologicalMapDtoGrid.addSelectionListener(selectionEvent -> tempEvent(technologicalMapDtoGrid.getSelectedItems()));
        //technologicalMapDtoGrid.addMu
        selectionModel.addMultiSelectionListener(multiSelectionEvent -> {
            tempEvent(multiSelectionEvent.getAddedSelection());
            tempDeleteEvent(multiSelectionEvent.getRemovedSelection());
        });


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

    private void tempEvent(Set<TechnologicalMapDto> selectedItems) {
        for (TechnologicalMapDto t : new ArrayList<>(selectedItems)
        ) {
            list.add(t.getId());
            log.info("add " + t.getId());
            log.info("List" + list);
            textField.setValue(String.valueOf(list.size()));
        }

        //list.add()
    }

    private void tempDeleteEvent(Set<TechnologicalMapDto> selectedItems) {
        for (TechnologicalMapDto t : new ArrayList<>(selectedItems)
        ) {
            Element element = groupButtons.getElement().getChild(8);
            log.info("List do" + list);
            list.remove(t.getId());   //list.indexOf(t.getId())
            log.info("List posle" + list);
            log.info("delete " + t.getId());
            textField.setValue(String.valueOf(list.size()));
        }

        //list.add()
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
