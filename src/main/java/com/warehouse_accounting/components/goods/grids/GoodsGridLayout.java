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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SpringComponent
public class GoodsGridLayout extends HorizontalLayout {
    private final ProductGroupService productGroupService;
    private final TreeGrid<ProductGroupDto> treeGrid;
    private final Grid<ProductDto> productDtoGrid;
    private final TextField selectedTextField;
    private final Div leftThreeGridDiv = new Div();
    private final Div gridDiv = new Div();
    private final Div mainLayer;
    private GoodsAndServiceView parentLayer;

    public GoodsGridLayout(
            @Qualifier("mainLayer") Div mainLayer,
            @Qualifier("goodsSelectedTextField") TextField selectedTextField,
            @Qualifier("goodsGrid") Grid<ProductDto> productDtoGrid,
            @Qualifier("goodsTreeGrid") TreeGrid<ProductGroupDto> treeGrid,
            ProductGroupService productGroupService) {
        this.selectedTextField = selectedTextField;
        this.productDtoGrid = productDtoGrid;
        this.treeGrid = treeGrid;
        this.productGroupService = productGroupService;
        this.mainLayer = mainLayer;
        setSizeFull();
        add(leftThreeGridDiv, gridDiv);
    }

    @Autowired
    public void setGoodsAndService(GoodsAndServiceView parentLayer) {
        this.parentLayer = parentLayer;
        initThreeGrid(parentLayer.getRootGroupId());
    }

    public void initThreeGrid(Long groupId) {
        treeGrid.removeAllColumns();
        ProductGroupDto rootGroup = productGroupService.getById(groupId);
        treeGrid.setItems(Collections.singleton(rootGroup),
                productGroupDto -> productGroupService.getAllByParentGroupId(productGroupDto.getId()));
        treeGrid.addComponentHierarchyColumn(productGroupDto -> {
            HorizontalLayout productGroupLine = new HorizontalLayout();
            productGroupLine.setPadding(false);
            if (productGroupDto.getId().equals(groupId)) {
                initGrid(groupId);
                Span name = new Span(productGroupDto.getName());
                productGroupLine.add(name);
            } else {
                Icon editIcon = new Icon(VaadinIcon.PENCIL);
                editIcon.setSize("10px");
                Span edit = new Span(editIcon);
                edit.addClickListener(iconClickEvent -> {
                    GroupForm serviceForm = new GroupForm(mainLayer, parentLayer, productGroupService, productGroupDto, true);
                    mainLayer.removeAll();
                    mainLayer.add(serviceForm);
                });
                Span name = new Span(productGroupDto.getName());
                productGroupLine.add(edit, name);
            }
            productGroupLine.addClickListener(productGroup -> initGrid(productGroupDto.getId()));
            return productGroupLine;
        });

        treeGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        treeGrid.expand(rootGroup);
        leftThreeGridDiv.add(treeGrid);
        leftThreeGridDiv.setWidth("25%");
    }

    public void initGrid(Long groupId) {
        productDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        productDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productDtoGrid.setItems(getTestProductDtos(groupId));
        getVisibleColumn().forEach((key, value) -> productDtoGrid.getColumnByKey(key).setHeader(value));
        productDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        productDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        gridDiv.add(productDtoGrid);
        gridDiv.setWidth("75%");
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
//        fieldNameColumnName.put("unit", "Единица измерения");
//        fieldNameColumnName.put("archive", "В архиве");
//        fieldNameColumnName.put("contractor", "Подрядчик");
//        fieldNameColumnName.put("taxSystem", "Налоговая система");
//        fieldNameColumnName.put("images", "Изображения");
//        fieldNameColumnName.put("productGroup.name", "Группа");
//        fieldNameColumnName.put("attributeOfCalculationObject", "Объекта расчета");
        return fieldNameColumnName;
    }

    private List<ProductDto> getTestProductDtos(Long groupId) {
        if (productGroupService.getAll().isEmpty()) {
            ProductGroupDto groupDto = ProductGroupDto.builder()
                    .name("Товары и услуги")
                    .productGroupDto(ProductGroupDto.builder()
                            .name("Test")
                            .build())
                    .build();
            productGroupService.create(groupDto);
        }

        List<ProductDto> productDtos = new ArrayList<>();
        ProductDto productDto1 = ProductDto.builder()
                .id(1L)
                .name("Test1")
                .weight(new BigDecimal("100.01"))
                .volume(new BigDecimal("20.02"))
                .purchasePrice(new BigDecimal("30.03"))
                .description("Описание1")
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .id(2L)
                .name("Test2")
                .weight(new BigDecimal("121.01"))
                .volume(new BigDecimal("50.02"))
                .purchasePrice(new BigDecimal("660.00"))
                .description("Описание2")
                .build();
        productDtos.add(productDto1);
        productDtos.add(productDto2);
        return productDtos;
    }
}