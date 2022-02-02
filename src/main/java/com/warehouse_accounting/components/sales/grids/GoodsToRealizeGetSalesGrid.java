package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.GoodsToRealizeGetDto;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGetService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringComponent
@UIScope
public class GoodsToRealizeGetSalesGrid extends HorizontalLayout {


    private final Grid<GoodsToRealizeGetDto> customerGoodsToRealizeDtoGrid = new Grid<>(GoodsToRealizeGetDto.class, false);

    public GoodsToRealizeGetSalesGrid(GoodsToRealizeGetService goodsToRealizeGetService) {
        customerGoodsToRealizeDtoGrid.setItems(goodsToRealizeGetService.getAll());
        add(initGrid());

    }

    private Grid<GoodsToRealizeGetDto> initGrid() {
        customerGoodsToRealizeDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));

        getVisibleColumn().forEach((key, value) -> customerGoodsToRealizeDtoGrid.getColumnByKey(key).setHeader(value));

        customerGoodsToRealizeDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return customerGoodsToRealizeDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("productDtoName", "Наименование");
        //   fieldNameColumnName.put("number", "Код"); //это поле должно быть также в ProductDto но пока оно отсутствует
        //   fieldNameColumnName.put("article", "Артикул"); //это поле должно быть также в ProductDto но пока оно отсутствует
        fieldNameColumnName.put("unitName", "Ед. изм.");
        fieldNameColumnName.put("getGoods", "Принято");
        fieldNameColumnName.put("quantity", "Кол-во");
        fieldNameColumnName.put("amount", "Сумма");
        fieldNameColumnName.put("arrive", "Вернули");
        fieldNameColumnName.put("remains", "Остаток");
        fieldNameColumnName.put("quantity_report", "Кол-во");
        fieldNameColumnName.put("amount_report", "Сумма");
        fieldNameColumnName.put("quantity_Noreport", "Кол-во");
        fieldNameColumnName.put("amount_Noreport", "Сумма");
        return fieldNameColumnName;
    }
}
