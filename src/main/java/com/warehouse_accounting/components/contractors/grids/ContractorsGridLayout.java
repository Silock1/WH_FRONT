package com.warehouse_accounting.components.contractors.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.services.interfaces.CallService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringComponent
@UIScope
public class ContractorsGridLayout extends HorizontalLayout {
    private final CallService service;
    private Button settingButton = new Button(new Icon(VaadinIcon.COG_O));
    private final Grid<CallDto> callDtoGrid = new Grid<>(CallDto.class, false);

    public ContractorsGridLayout(CallService service) {
        this.service = service;
        callDtoGrid.setItems(service.getAll());
        add(initGrid(), settingButton);
        setSizeFull();
    }

    private Grid<CallDto> initGrid() {
        callDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));

        getVisibleColumn().forEach((key, value) -> callDtoGrid.getColumnByKey(key).setHeader(value));

        callDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return callDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("callTime", "Время");
        fieldNameColumnName.put("type", "Тип");
        fieldNameColumnName.put("number", "Номер");
        fieldNameColumnName.put("contractorName", "Контрагент");
        fieldNameColumnName.put("employeeWhoCalledName", "Сотрудник");
        fieldNameColumnName.put("callDuration", "Разговор, сек");
        fieldNameColumnName.put("comment", "Комментарий");
        fieldNameColumnName.put("callRecord", "Запись");
        fieldNameColumnName.put("whenChanged", "Когда изменен");
        fieldNameColumnName.put("employeeWhoChangedName", "Кто изменил");
        return fieldNameColumnName;
    }
}