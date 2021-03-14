package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.components.goods.forms.ServiceForm;
import com.warehouse_accounting.components.goods.grids.GoodsGridLayout;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@SpringComponent
public class GoodsAndServiceView extends VerticalLayout {
    private final TreeGrid<ProductGroupDto> treeGrid;
    private final ProductGroupService productGroupService;
    private final TextField textFieldGridSelected;
    private final Div pageContent;
    private final Div mainLayer;
    private GoodsGridLayout goodsGridLayout;
    private Long rootGroupId = 1L; //TODO переопределить для текущего пользователя

    public GoodsAndServiceView(@Qualifier("mainLayer") Div parentLayer,
                               @Qualifier("goodsLayer") Div pageContent,
                               @Qualifier("goodsSelectedTextField") TextField textFieldGridSelected,
                               @Qualifier("goodsTreeGrid") TreeGrid<ProductGroupDto> treeGrid,
                               ProductGroupService productGroupService) {
        this.mainLayer = parentLayer;
        this.textFieldGridSelected = textFieldGridSelected;
        this.treeGrid = treeGrid;
        this.productGroupService = productGroupService;
        this.pageContent = pageContent;
        pageContent.setSizeFull();
        add(getGroupButtons(), pageContent);
    }

    @Autowired
    public void setGoodsGridLayout(GoodsGridLayout goodsGridLayout) {
        this.goodsGridLayout = goodsGridLayout;
        pageContent.add(goodsGridLayout);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Товары и услуги");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(buttonClickEvent -> {
            goodsGridLayout.initGrid(rootGroupId);
            goodsGridLayout.initThreeGrid(rootGroupId);
        });

        Button addProductButton = new Button("Товар", new Icon(VaadinIcon.PLUS));
        addProductButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addProductButton.addClickListener(event -> {
            GoodsForm goodsForm = new GoodsForm(mainLayer, this);
            mainLayer.removeAll();
            mainLayer.add(goodsForm);
        });

        Button addServiceButton = new Button("Услуга", new Icon(VaadinIcon.PLUS));
        addServiceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addServiceButton.addClickListener(event -> {
            ServiceForm serviceForm = new ServiceForm(mainLayer, this);
            mainLayer.removeAll();
            mainLayer.add(serviceForm);
        });

        Button addKitButton = new Button("Комплект", new Icon(VaadinIcon.PLUS));
        addKitButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button addGroupButton = new Button("Группа", new Icon(VaadinIcon.PLUS));
        addGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addGroupButton.addClickListener(buttonClickEvent -> {
            ProductGroupDto selectGroup;
            Optional<ProductGroupDto> selectedGroup = treeGrid.getSelectedItems().stream().findFirst();
            selectGroup = selectedGroup.orElseGet(() -> productGroupService.getById(rootGroupId));
            GroupForm groupForm = new GroupForm(mainLayer, this, productGroupService, selectGroup, false);
            mainLayer.removeAll();
            mainLayer.add(groupForm);
        });

        Button addFilterButton = new Button("Фильтр", new Icon(VaadinIcon.PLUS));
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setPlaceholder("наименование, код или артикул");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
            //grid filter
        });

        HorizontalLayout editMenuBar = getEditMenuBar();

        HorizontalLayout printMenuBar = getPrintMenuBar();

        HorizontalLayout importMenuBar = getImportMenuBar();

        HorizontalLayout exportMenuBar = getExportMenuBar();

        groupControl.add(helpButton, textProducts, refreshButton, addProductButton, addServiceButton,
                addKitButton, addGroupButton, addFilterButton, searchField, editMenuBar,
                printMenuBar, importMenuBar, exportMenuBar);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), agileDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            int selected = goodsGridLayout.getProductGrid().asMultiSelect().getSelectedItems().size();
            Notification notification = new Notification(String.format("Выделено для удаления %d", selected),
                    3000,
                    Notification.Position.MIDDLE);
            notification.open();
        });
        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Переместить", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Цены...", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textFieldGridSelected, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getPrintMenuBar() {
        MenuBar printMenuBar = new MenuBar();
        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("12px");
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        HorizontalLayout printItem = new HorizontalLayout(printIcon, agileDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("Ценник (70x49,5мм)", e -> {

        });
        print.getSubMenu().addItem("Термоэтикетка (58х40мм)", e -> {

        });
        print.getSubMenu().addItem("Настроить...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }

    private HorizontalLayout getImportMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        MenuBar importMenuBar = new MenuBar();
        importMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Импорт"), agileDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        MenuItem importItem = importMenuBar.addItem(horizontalLayout);

        importItem.getSubMenu().addItem("Импорт из Excel", e -> {

        });

        importItem.getSubMenu().addItem("Импорт из YML", e -> {

        });

        HorizontalLayout groupImport = new HorizontalLayout();
        groupImport.add(importMenuBar);
        groupImport.setSpacing(false);
        groupImport.setAlignItems(Alignment.CENTER);
        return groupImport;
    }

    private HorizontalLayout getExportMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        MenuBar exportMenuBar = new MenuBar();
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Экспорт"), agileDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        exportMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        MenuItem exportItem = exportMenuBar.addItem(horizontalLayout);

        exportItem.getSubMenu().addItem("Экспорт в Excel", e -> {

        });
        exportItem.getSubMenu().addItem("Экспорт в YML", e -> {

        });

        HorizontalLayout groupImport = new HorizontalLayout();
        groupImport.add(exportMenuBar);
        groupImport.setSpacing(false);
        groupImport.setAlignItems(Alignment.CENTER);
        return groupImport;
    }

    public Long getRootGroupId() {
        return rootGroupId;
    }

    public GoodsGridLayout getGoodsGridLayout() {
        return goodsGridLayout;
    }
}