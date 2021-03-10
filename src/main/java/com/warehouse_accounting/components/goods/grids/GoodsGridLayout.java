package com.warehouse_accounting.components.goods.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GoodsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<ProductDto> productDtoGrid = new Grid<>(ProductDto.class, false);

    public GoodsGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        H5 goodsLeftMenu = new H5("Товары и услуги");
        add(goodsLeftMenu, initGrid());
    }

    private Grid<ProductDto> initGrid() {
        productDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        productDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productDtoGrid.setItems(getTestProductDtos());
        getVisibleColumn().forEach((key, value) -> productDtoGrid.getColumnByKey(key).setHeader(value));
        productDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        productDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return productDtoGrid;
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
        fieldNameColumnName.put("unit", "Единица измерения");
//        fieldNameColumnName.put("archive");
//        fieldNameColumnName.put("contractor");
//        fieldNameColumnName.put("taxSystem");
//        fieldNameColumnName.put("images");
//        fieldNameColumnName.put("productGroup");
//        fieldNameColumnName.put("attributeOfCalculationObject");
        return fieldNameColumnName;
    }

    private List<ProductDto> getTestProductDtos() {
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
