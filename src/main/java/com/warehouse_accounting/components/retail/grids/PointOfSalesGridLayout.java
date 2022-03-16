package com.warehouse_accounting.components.retail.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.services.impl.PointOfSalesServiceImpl;
import com.warehouse_accounting.services.interfaces.PointOfSalesService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PointOfSalesGridLayout extends HorizontalLayout {

    private static TextField textField;


    public PointOfSalesGridLayout(TextField textField){
        this.textField = textField;
        add(initPointOfSalesGrid());
    }

    public static Grid<PointOfSalesDto> initPointOfSalesGrid(){

        Grid<PointOfSalesDto> pointOfSalesDtoGrid =
                new Grid<>(PointOfSalesDto.class, false);

        pointOfSalesDtoGrid.setColumns(getColumn().keySet().toArray(String[]::new));
        pointOfSalesDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getColumn().forEach((key, value)-> pointOfSalesDtoGrid.getColumnByKey(key).setHeader(value));
        pointOfSalesDtoGrid.asMultiSelect().addSelectionListener(listener ->{
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });
        pointOfSalesDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //---Добавить чекбокс в самый первый столбец.
        PointOfSalesService pointOfSalesService = new PointOfSalesServiceImpl("/api/retail",retrofit);
        pointOfSalesDtoGrid.setItems(pointOfSalesService.getAll());
        return pointOfSalesDtoGrid;
    }


    private static HashMap<String, String> getColumn(){
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id","Включена");
        fieldNameColumnName.put("name", "Наименование");
        fieldNameColumnName.put("activity", "Активность");
        fieldNameColumnName.put("type", "Тип");
        fieldNameColumnName.put("revenue", "Выручка");
        fieldNameColumnName.put("cheque", "Чеки");
        fieldNameColumnName.put("averageСheck", "Средний чек");
        fieldNameColumnName.put("moneyInTheCashRegister", "Денег в кассе");
        fieldNameColumnName.put("cashiers", "Кассиры");
        fieldNameColumnName.put("synchronization", "Синхронизация");
        fieldNameColumnName.put("FN", "ФН");
        fieldNameColumnName.put("validityPeriodFN", "Срок действия ФН");
        return fieldNameColumnName;
    }

}
