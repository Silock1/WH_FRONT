package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.filter.GoodsFilter;
import com.warehouse_accounting.components.goods.forms.ComplectForm;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.components.goods.forms.ServiceForm;
import com.warehouse_accounting.components.goods.grids.GoodsGridLayout;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Optional;

@UIScope
@Component
@Route(value = "goodsAndServiceView", layout = AppView.class)
public class GoodsAndServiceView extends VerticalLayout {
    private final ProductGroupService productGroupService;
    private final ProductService productService;
    private final TreeGrid<ProductGroupDto> treeGrid = new TreeGrid<>();
    private final TextField textFieldGridSelected = new TextField();
    private final Grid<ProductDto> productDtoGrid = new Grid<>(ProductDto.class, false);
    private final Div pageContent;
    private Div mainDiv;
    private GoodsGridLayout goodsGridLayout;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private ContractorService contractorService;
    private CountryService countryService;
    private UnitsOfMeasureService unitsOfMeasureService;
    private TaxSystemService taxSystemService;
    private ImageService imageService;
    private AttributeOfCalculationObjectService attributeService;
    private  UnitService unitService;
    private GoodsFilter goodsFilter;
    private Long rootGroupId = 1L;



    public GoodsAndServiceView(GoodsFilter goodsFilter,
                               ProductService productService,
                               ProductGroupService productGroupService,
                               DepartmentService departmentService,
                               ContractorService contractorService,
                               EmployeeService employeeService,
                               CountryService countryService,
                               UnitsOfMeasureService unitsOfMeasureService,
                               TaxSystemService taxSystemService,
                               ImageService imageService,
                               AttributeOfCalculationObjectService attributeService,
                               UnitService unitService) {
        this.productGroupService = productGroupService;
        this.productService = productService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.contractorService = contractorService;
        this.countryService = countryService;
        this.unitsOfMeasureService = unitsOfMeasureService;
        this.taxSystemService = taxSystemService;
        this.imageService = imageService;
        this.attributeService = attributeService;
        this.unitService = unitService;
        this.goodsFilter = new GoodsFilter(productGroupService, employeeService, departmentService, contractorService);
        goodsGridLayout = new GoodsGridLayout(productGroupService, productService, this);
        Div pageContent = new Div();
        pageContent.add(goodsGridLayout);
        pageContent.setSizeFull();
        this.pageContent = pageContent;
        add(initGroupButtons(), this.goodsFilter, pageContent);
    }

    private HorizontalLayout initGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> {
            Notification.show("" +
                    "В разделе представлены все ваши товары, услуги и комплекты.\n" +
                    "\n" +
                    "Для удобства товары и услуги можно группировать. Различать товары с одинаковым артикулом по характеристикам (например, размеру или цвету) удобно с помощью модификаций. Несколько единиц одного товара можно продавать упаковками. А комплекты позволяют продавать наборы разных товаров и услуг как единое целое.\n" +
                    "\n" +
                    "Каталог товаров можно импортировать и экспортировать.\n" +
                    "\n" +
                    "Читать инструкции:\n" +
                    "\n" +
                    "Товары и услуги\n" +
                    "Импорт и экспорт\n" +
                    "Видео:\n" +
                    "\n" +
                    "Товары и остатки\n" +
                    "Импорт товаров из Excel\n" +
                    "Цены в МоемСкладе", 5000, Notification.Position.TOP_START);
        });

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
            final GoodsForm goodsForm = new GoodsForm(productService, countryService, unitsOfMeasureService,
                    contractorService, taxSystemService, imageService, productGroupService, attributeService,
                    unitService);
            mainDiv.removeAll();
            DocumentOperationsToolbar menu = new DocumentOperationsToolbar();
            menu.setCloseHandler(() -> { goodsForm.close();
                                        mainDiv.removeAll();
                                        mainDiv.add(this);})
                .setSaveHandler(() -> { goodsForm.save();
                                        mainDiv.removeAll();
                                        mainDiv.add(this); });
            mainDiv.add(menu, goodsForm);
        });

        //TODO не сделана. не работает
        Button addServiceButton = new Button("Услуга", new Icon(VaadinIcon.PLUS));
        addServiceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addServiceButton.addClickListener(event -> {
            ServiceForm serviceForm = new ServiceForm(mainDiv, this);
            mainDiv.removeAll();
            mainDiv.add(serviceForm);
        });

        //TODO Моя кнопка для Компонента через Product
        Button addComplectButton = new Button("Комплект", new Icon(VaadinIcon.PLUS));
        addComplectButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addComplectButton.addClickListener(event -> {
            ComplectForm complectForm = new ComplectForm(mainDiv, this, productService, productGroupService);
            mainDiv.removeAll();
            mainDiv.add(complectForm);
        });

        Button addGroupButton = new Button("Группа", new Icon(VaadinIcon.PLUS));
        addGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addGroupButton.addClickListener(buttonClickEvent -> {
            Optional<ProductGroupDto> selectedGroup = treeGrid.getSelectedItems().stream().findFirst();
            ProductGroupDto selectGroup = selectedGroup.orElseGet(() -> productGroupService.getById(rootGroupId));
            GroupForm groupForm = new GroupForm(mainDiv, this, productGroupService, selectGroup, false);
            mainDiv.removeAll();
            mainDiv.add(groupForm);
        });

        Button addFilterButton = new Button("Фильтр", new Icon(VaadinIcon.PLUS));
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> goodsFilter.setVisible(!goodsFilter.isVisible()));

        TextField searchField = new TextField();


        searchField.setPlaceholder("наименование, код или артикул");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
            //grid filter
        });

        HorizontalLayout editMenuBar = initEditMenuBar();
        HorizontalLayout printMenuBar = getPrintMenuBar();
        HorizontalLayout importMenuBar = getImportMenuBar();
        HorizontalLayout exportMenuBar = getExportMenuBar();

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        setting.addClickListener(event -> {

        });

        groupControl.add(helpButton, textProducts, refreshButton, addProductButton, addServiceButton,
                addComplectButton, addGroupButton, addFilterButton, searchField, editMenuBar,
                printMenuBar, importMenuBar, exportMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout initEditMenuBar() {
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
            notification.open(); //TODO реализовать форму подтверждения удаления товара
            //TODO написать метод удаляющий группу продуктов одним запросом
            goodsGridLayout.getProductGrid()
                    .asMultiSelect()
                    .getSelectedItems()
                    .stream().forEach(productDto -> productService.deleteById(productDto.getId()));
            goodsGridLayout.initGrid(rootGroupId); //FixMe TEST
            goodsGridLayout.initThreeGrid(rootGroupId); //FixMe TEST

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

    public void setMainDiv(Div div) {
        this.mainDiv = div;
    }

    public Div getMainDiv() {
        return mainDiv;
    }

    public TextField getTextFieldGridSelected() {
        return textFieldGridSelected;
    }

    public Grid<ProductDto> getProductDtoGrid() {
        return productDtoGrid;
    }

    public TreeGrid<ProductGroupDto> getTreeGrid() {
        return treeGrid;
    }
}