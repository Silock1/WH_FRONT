package com.warehouse_accounting.components.retail.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.services.impl.PointOfSalesServiceImpl;
import com.warehouse_accounting.services.interfaces.PointOfSalesService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PointOfSalesGridLayout extends HorizontalLayout {

    private TextField textField = new TextField();

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));

    public PointOfSalesGridLayout(){
        add(initPointOfSalesGrid(), settingButton);
        add(settingButton);
    }

    public Grid<PointOfSalesDto> initPointOfSalesGrid() {

        Grid<PointOfSalesDto> pointOfSalesDtoGrid =
                new Grid<>(PointOfSalesDto.class, false);

        pointOfSalesDtoGrid.setColumns(getColumn().keySet().toArray(String[]::new));
        pointOfSalesDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<PointOfSalesDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        getColumn().forEach((key, value) -> {
            Grid.Column<PointOfSalesDto> pointOfSalesDtoColumn = pointOfSalesDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, pointOfSalesDtoColumn);
        });
        pointOfSalesDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });
        pointOfSalesDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //---???????????????? ?????????????? ?? ?????????? ???????????? ??????????????.
        PointOfSalesService pointOfSalesService = new PointOfSalesServiceImpl("/api/retail", retrofit);
        pointOfSalesDtoGrid.setItems(pointOfSalesService.getAll());
        return pointOfSalesDtoGrid;
    }


    private static HashMap<String, String> getColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id", "????????????????");
        fieldNameColumnName.put("name", "????????????????????????");
        fieldNameColumnName.put("activity", "????????????????????");
        fieldNameColumnName.put("type", "??????");
        fieldNameColumnName.put("revenue", "??????????????");
        fieldNameColumnName.put("cheque", "????????");
        fieldNameColumnName.put("average??heck", "?????????????? ??????");
        fieldNameColumnName.put("moneyInTheCashRegister", "?????????? ?? ??????????");
        fieldNameColumnName.put("cashiers", "??????????????");
        fieldNameColumnName.put("synchronization", "??????????????????????????");
        fieldNameColumnName.put("FN", "????");
        fieldNameColumnName.put("validityPeriodFN", "???????? ???????????????? ????");
        return fieldNameColumnName;
    }

}
