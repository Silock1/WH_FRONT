package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.GoodsToRealizeGiveDto;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringComponent
@UIScope
public class GoodsToRealizeGiveSalesGrid extends HorizontalLayout {

    private final Grid<GoodsToRealizeGiveDto> customerGoodsToRealizeDtoGrid = new Grid<>(GoodsToRealizeGiveDto.class, false);

    public GoodsToRealizeGiveSalesGrid(GoodsToRealizeGiveService goodsToRealizeGiveService) {
        customerGoodsToRealizeDtoGrid.setItems(goodsToRealizeGiveService.getAll());
        add(initGrid());
    }

    private Grid<GoodsToRealizeGiveDto> initGrid() {
        customerGoodsToRealizeDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));

        getVisibleColumn().forEach((key, value) -> customerGoodsToRealizeDtoGrid.getColumnByKey(key).setHeader(value));

        customerGoodsToRealizeDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return customerGoodsToRealizeDtoGrid;
    }
    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id", "id");
        fieldNameColumnName.put("productDtoId", "Товар_id");
        fieldNameColumnName.put("productDtoName", "Наименование");
        fieldNameColumnName.put("unitId", "Код_id");
        fieldNameColumnName.put("unitName", "Ед. изм.");
        fieldNameColumnName.put("giveGoods", "Принято");
        fieldNameColumnName.put("quantity", "Кол-во");
        fieldNameColumnName.put("amount", "Сумма");
        fieldNameColumnName.put("arrive", "Вернули");
        fieldNameColumnName.put("remains", "Остаток");

        return fieldNameColumnName;
    }
}
