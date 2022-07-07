package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.AcceptancesDto;
import com.warehouse_accounting.services.interfaces.AcceptanceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class AcceptancesGridLayout extends HorizontalLayout {
    private final TextField textField;
    private final Button settingButton = new Button(new Icon(VaadinIcon.COG));
    @Getter
    private final Grid<AcceptancesDto> acceptanceProductDtoGrid = new Grid<>(AcceptancesDto.class, false);

    private final AcceptanceService acceptanceService;

    public AcceptancesGridLayout(TextField textField, AcceptanceService acceptanceService) {
        this.textField = textField;
        this.acceptanceService = acceptanceService;
        add(initAcceptanceGrid(), settingButton);
    }

    private Grid<AcceptancesDto> initAcceptanceGrid() {
        acceptanceProductDtoGrid.setColumns(getVisibleAcceptancesColumn().keySet().toArray(String[]::new));
        acceptanceProductDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<AcceptancesDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        getVisibleAcceptancesColumn().forEach((key, value) -> {
            Grid.Column<AcceptancesDto> acceptanceProductDtoColumn = acceptanceProductDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, acceptanceProductDtoColumn);
        });



        List<AcceptancesDto> acceptancesDtos = acceptanceService.getAll();




        acceptancesDtos.sort(Comparator.comparingLong(AcceptancesDto::getId));

        acceptanceProductDtoGrid.setItems(acceptancesDtos);
        acceptanceProductDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });

        acceptanceProductDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        return acceptanceProductDtoGrid;
    }

    private HashMap<String, String> getVisibleAcceptancesColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();

        fieldNameColumnName.put("number", "№");
        fieldNameColumnName.put("time", "Время");
        fieldNameColumnName.put("warehouseName", "На склад");
        fieldNameColumnName.put("contractorName", "Контрагент");
        fieldNameColumnName.put("companyName", "Организация");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("paid", "Оплачено");
        fieldNameColumnName.put("noPaid", "Не оплачено");
        fieldNameColumnName.put("dateIncomingNumber", "Входящая дата");
        fieldNameColumnName.put("incomingNumber", "Входящий номер");
        fieldNameColumnName.put("projectName", "Проект");
        fieldNameColumnName.put("contractNumber", "Договор");
        fieldNameColumnName.put("overHeadCost", "Накладные расходы");
        fieldNameColumnName.put("returnSum", "Сумма возвратов");
        fieldNameColumnName.put("isSharedAccess", "Общий доступ");
        fieldNameColumnName.put("ownerDepartmentName", "Владелец-отдел");
        fieldNameColumnName.put("ownerEmployeeName", "Владелец-сотрудник");
        fieldNameColumnName.put("send", "Отправлено");
        fieldNameColumnName.put("print", "Напечатано");
        fieldNameColumnName.put("comment", "Комментарий");
        fieldNameColumnName.put("whenChanged", "Когда изменен");
        fieldNameColumnName.put("nameWhoChanget", "Кто изменил");
        return fieldNameColumnName;
    }
}
