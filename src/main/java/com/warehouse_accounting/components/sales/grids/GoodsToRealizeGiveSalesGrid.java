package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.GoodsToRealizeGiveDto;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringComponent
@UIScope
public class GoodsToRealizeGiveSalesGrid extends HorizontalLayout {

    private final Grid<GoodsToRealizeGiveDto> customerGoodsToRealizeDtoGrid = new Grid<>(GoodsToRealizeGiveDto.class, false);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));

    public GoodsToRealizeGiveSalesGrid(GoodsToRealizeGiveService goodsToRealizeGiveService) {
        customerGoodsToRealizeDtoGrid.setItems(goodsToRealizeGiveService.getAll());
        add(initGrid(), settingButton);
    }

    private Grid<GoodsToRealizeGiveDto> initGrid() {
        customerGoodsToRealizeDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<GoodsToRealizeGiveDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        getVisibleColumn().forEach((key, value) -> {
            Grid.Column<GoodsToRealizeGiveDto> goodsToRealizeGiveDtoColumn = customerGoodsToRealizeDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, goodsToRealizeGiveDtoColumn);
        });

        customerGoodsToRealizeDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return customerGoodsToRealizeDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("productDtoName", "????????????????????????");
        //   fieldNameColumnName.put("number", "??????"); //?????? ???????? ???????????? ???????? ?????????? ?? ProductDto ???? ???????? ?????? ??????????????????????
        //   fieldNameColumnName.put("article", "??????????????"); //?????? ???????? ???????????? ???????? ?????????? ?? ProductDto ???? ???????? ?????? ??????????????????????
        fieldNameColumnName.put("unitName", "????. ??????.");
        fieldNameColumnName.put("giveGoods", "????????????????");
        fieldNameColumnName.put("quantity", "??????-????");
        fieldNameColumnName.put("amount", "??????????");
        fieldNameColumnName.put("arrive", "??????????????");
        fieldNameColumnName.put("remains", "??????????????");

        return fieldNameColumnName;
    }
}
