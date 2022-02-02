package com.warehouse_accounting.components.goods.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private Grid<ProductDto> productDtoGrid;
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
    }

    public void initThreeGrid(Long groupId) {
        initRootProductGroup();
        treeGrid.removeAllColumns();
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
        productDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        productDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productDtoGrid.setItems(productService.getAll());
        getVisibleColumn().forEach((key, value) -> productDtoGrid.getColumnByKey(key).setHeader(value));
        productDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        productDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        gridDiv.setWidth(widthGrid);
        gridDiv.add(productDtoGrid);
    }

    public Grid<ProductDto> getProductGrid() {
        return productDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id", "Id");
        fieldNameColumnName.put("name", "Наименование");
        fieldNameColumnName.put("weight", "Масса");
        fieldNameColumnName.put("volume", "Объем");
        fieldNameColumnName.put("purchasePrice", "Цена");
        fieldNameColumnName.put("description", "Описание");
        fieldNameColumnName.put("unit.shortName", "Единица измерения");
        fieldNameColumnName.put("archive", "В архиве");
        fieldNameColumnName.put("contractor.name", "Подрядчик");
        fieldNameColumnName.put("taxSystem.name", "Налоговая система");
        fieldNameColumnName.put("productGroup.name", "Группа");
        fieldNameColumnName.put("attributeOfCalculationObject.name", "Объект расчетов");
        return fieldNameColumnName;
    }

    //TODO Что это и что мне с этим сделать?
    private void initRootProductGroup() {
        if (productGroupService.getAll().isEmpty()) {
            ProductGroupDto groupDto = ProductGroupDto.builder()
                    .name("Товары и услуги")
                    .sortNumber("1")
                    .build();
            productGroupService.create(groupDto);
        }
    }
}