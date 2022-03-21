package com.warehouse_accounting.components.goods.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.goods.forms.GoodUpdateForm;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UIScope
@Component
@Route(value = "goodsGridLayout", layout = AppView.class)
public class GoodsGridLayout extends HorizontalLayout {
    private final ProductGroupService productGroupService;
    private final ProductService productService;
    private final Div leftThreeGridDiv = new Div();
    private final Div gridDiv = new Div();
    private final String widthLeftTreeGrid = "25%";
    private final String widthGrid = "75%";
    private TreeGrid<ProductGroupDto> treeGrid;
    private TextField selectedTextField;
    private Grid<ProductDto> productDtoGrid = new Grid<>(ProductDto.class, false);
    private GoodsAndServiceView parentLayer;

    public GoodsGridLayout(ProductGroupService productGroupService,
                           ProductService productService,
                           GoodsAndServiceView parentLayer) {
        this.productGroupService = productGroupService;
        this.productService = productService;
        this.parentLayer = parentLayer;
        this.treeGrid = parentLayer.getTreeGrid();
        this.selectedTextField = parentLayer.getTextFieldGridSelected();
        this.productDtoGrid = parentLayer.getProductDtoGrid();
        initThreeGrid(parentLayer.getRootGroupId());
        setSizeFull();
        add(leftThreeGridDiv, gridDiv);

        Grid.Column<ProductDto> idColumn = productDtoGrid.addColumn(ProductDto::getId).setHeader("Id");
        Grid.Column<ProductDto> nameColumn = productDtoGrid.addColumn(ProductDto::getName).setHeader("Наименование");
        Grid.Column<ProductDto> weightColumn = productDtoGrid.addColumn(ProductDto::getWeight).setHeader("Масса");
        Grid.Column<ProductDto> volumeColumn = productDtoGrid.addColumn(ProductDto::getVolume).setHeader("Объем");
        Grid.Column<ProductDto> descriptionColumn = productDtoGrid.addColumn(ProductDto::getDescription).setHeader("Описание");
        Grid.Column<ProductDto> unitsOfMeasureColumn = productDtoGrid.addColumn(ProductDto::getUnitsOfMeasureDto).setHeader("Единица измерения");
        Grid.Column<ProductDto> archiveColumn = productDtoGrid.addColumn(ProductDto::getArchive).setHeader("В архиве");
        Grid.Column<ProductDto> contractorColumn = productDtoGrid.addColumn(ProductDto::getContractor).setHeader("Подрядчик");
        Grid.Column<ProductDto> taxColumn = productDtoGrid.addColumn(ProductDto::getTaxSystem).setHeader("Налоговая система");
        Grid.Column<ProductDto> purchasePriceColumn = productDtoGrid.addColumn(ProductDto::getPurchasePrice).setHeader("Цена");
        Grid.Column<ProductDto> groupColumn = productDtoGrid.addColumn(ProductDto::getGroup).setHeader("Группа");
        Grid.Column<ProductDto> attributeOfCalculationObjectColumn = productDtoGrid.addColumn(ProductDto::getAttributeOfCalculationObject).setHeader("Объект рассчетов");

        productDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);

        columnToggleContextMenu.addColumnToggleItem("Наименование", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Масса", weightColumn);
        columnToggleContextMenu.addColumnToggleItem("Объем", volumeColumn);
        columnToggleContextMenu.addColumnToggleItem("Описание", descriptionColumn);
        columnToggleContextMenu.addColumnToggleItem("Единица измерения", unitsOfMeasureColumn);
        columnToggleContextMenu.addColumnToggleItem("В архиве", archiveColumn);
        columnToggleContextMenu.addColumnToggleItem("Подрядчик", contractorColumn);
        columnToggleContextMenu.addColumnToggleItem("Налоговая система", taxColumn);
        columnToggleContextMenu.addColumnToggleItem("Цена", purchasePriceColumn);
        columnToggleContextMenu.addColumnToggleItem("Группа", groupColumn);
        columnToggleContextMenu.addColumnToggleItem("Объект рассчетов", attributeOfCalculationObjectColumn);

        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(productDtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(com.vaadin.flow.component.Component target) { //com.vaadin.flow.component.
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ProductDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

    public void initThreeGrid(Long groupId) {
        treeGrid.removeAllColumns();
        /*Конструкция с rootGroup не соответствует дизайну сайта "Moy Sklad", но кода написано довольно много, возможно
        * здесь была какая-то идея о которой я не знаю*/
        ProductGroupDto rootGroup = productGroupService.getById(groupId);
        if (Objects.nonNull(rootGroup)) {

            treeGrid.setItems(Collections.singleton(rootGroup), productGroupDto -> {
                List<ProductGroupDto> allByParentGroupId = productGroupService.getAllByParentGroupId(productGroupDto.getId());
                if (Objects.nonNull(allByParentGroupId)) {
                    return allByParentGroupId;
                } else {
                    return Collections.emptyList();
                }
            });

            treeGrid.addComponentHierarchyColumn(productGroupDto -> {
                HorizontalLayout productGroupLine = new HorizontalLayout();
                Span name = new Span();
                productGroupLine.setPadding(false);
                if (productGroupDto.getId().equals(groupId)) {
                    initGrid(groupId);
                    name.add(productGroupDto.getName());
                    productGroupLine.add(name);
                } else {
                    Icon editIcon = new Icon(VaadinIcon.PENCIL);
                    editIcon.setSize("10px");
                    Span edit = new Span(editIcon);
                    edit.addClickListener(iconClickEvent -> {
                        Div mainLayer = parentLayer.getMainDiv();
                        treeGrid.deselectAll();
                        GroupForm serviceForm = new GroupForm(mainLayer, parentLayer, productGroupService, productGroupDto, true);
                        mainLayer.removeAll();
                        mainLayer.add(serviceForm);
                    });
                    name.add(productGroupDto.getName());
                    productGroupLine.add(edit, name);
                }
                name.addClickListener(spanClickEvent -> treeGrid.select(productGroupDto));
                productGroupLine.getElement().addEventListener("click", e -> {})
                        .addEventData("event.stopPropagation()");
                return productGroupLine;
            });
        }
        treeGrid.addSelectionListener(item -> {
            if (item.getFirstSelectedItem().isPresent()) {
                initGrid(item.getFirstSelectedItem().get().getId());
            }
        });
        treeGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        treeGrid.expand(rootGroup);
        leftThreeGridDiv.add(treeGrid);
        leftThreeGridDiv.setWidth(widthLeftTreeGrid);
    }

    public void initGrid(Long groupId) {
        //productDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        productDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productDtoGrid.setItems(productService.getAll());
        //getVisibleColumn().forEach((key, value) -> productDtoGrid.getColumnByKey(key).setHeader(value));
        productDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        productDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        productDtoGrid.addItemDoubleClickListener(event -> {
            GoodUpdateForm goodUpdateForm = new GoodUpdateForm(parentLayer.getMainDiv(), parentLayer, productService, event.getItem());
            parentLayer.getMainDiv().removeAll();
            parentLayer.getMainDiv().add(goodUpdateForm);
        });
        gridDiv.setWidth(widthGrid);
        gridDiv.add(productDtoGrid);
    }

    public Grid<ProductDto> getProductGrid() {
        return productDtoGrid;
    }
//
//    private HashMap<String, String> getVisibleColumn() {
//        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
//        fieldNameColumnName.put("id", "Id");
//        fieldNameColumnName.put("name", "Наименование");
//        fieldNameColumnName.put("weight", "Масса");
//        fieldNameColumnName.put("volume", "Объем");
//        fieldNameColumnName.put("purchasePrice", "Цена");
//        fieldNameColumnName.put("description", "Описание");
//        fieldNameColumnName.put("unit.shortName", "Единица измерения");
//        fieldNameColumnName.put("archive", "В архиве");
//        fieldNameColumnName.put("contractor.name", "Подрядчик");
//        fieldNameColumnName.put("taxSystem.name", "Налоговая система");
//        fieldNameColumnName.put("productGroup.name", "Группа");
//        fieldNameColumnName.put("attributeOfCalculationObject.name", "Объект расчетов");
//        return fieldNameColumnName;
//    }

}