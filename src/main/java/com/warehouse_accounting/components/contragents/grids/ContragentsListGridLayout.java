package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.CallService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.util.HashMap;
import java.util.LinkedHashMap;

/*
Таблица контрагентов
 */
@SpringComponent
@UIScope
public class ContragentsListGridLayout extends HorizontalLayout {
    private final ContractorService contractorService;
    private Button settingButton = new Button(new Icon(VaadinIcon.COG_O));
    private final Grid<ContractorDto> contractorDtoGrid = new Grid<>(ContractorDto.class, false);

    public ContragentsListGridLayout(ContractorService contractorService) {
        this.contractorService = contractorService;
        contractorDtoGrid.setItems(contractorService.getAll());
        add(initGrid(), settingButton);
        setSizeFull();
    }

    private Grid<ContractorDto> initGrid() {
        contractorDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));

        getVisibleColumn().forEach((key, value) -> contractorDtoGrid.getColumnByKey(key).setHeader(value));

        contractorDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return contractorDtoGrid;
    }
/*
Данные для закоментированных полей пока не реализованы.
 */

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("name", "Наименование");
        fieldNameColumnName.put("id", "Код");
//        fieldNameColumnName.put("", "Создан");
        fieldNameColumnName.put("phone", "Телефон");
        fieldNameColumnName.put("fax", "Факс");
        fieldNameColumnName.put("email", "Email");
//        fieldNameColumnName.put("", "Статус");
        fieldNameColumnName.put("numberDiscountCard", "Дисконтная карта");
//        fieldNameColumnName.put("", "Фактический адрес");
        fieldNameColumnName.put("comment", "Комментарий");
        fieldNameColumnName.put("contractorGroupName", "Группы");
        fieldNameColumnName.put("legalDetailTypeOfContractorName", "Тип контрагента");
        fieldNameColumnName.put("legalDetailAddress", "Полное наименование");
        fieldNameColumnName.put("address", "Юридический адрес");
        fieldNameColumnName.put("legalDetailInn", "ИНН");
        fieldNameColumnName.put("legalDetailKpp", "КПП");
//        fieldNameColumnName.put("", "Банк");
//        fieldNameColumnName.put("", "Расчетный счет");
        fieldNameColumnName.put("typeOfPriceName", "Цены");
//        fieldNameColumnName.put("", "Общий доступ");
//        fieldNameColumnName.put("", "Владелец-отдел");
//        fieldNameColumnName.put("", "Владелец-сотрудник");
//        fieldNameColumnName.put("", "Первая продажа");
//        fieldNameColumnName.put("", "Последняя продажа");
//        fieldNameColumnName.put("", "Количество продаж");
//        fieldNameColumnName.put("", "Сумма продаж");
//        fieldNameColumnName.put("", "Средний чек");
//        fieldNameColumnName.put("", "Количество возвратов");
//        fieldNameColumnName.put("", "Сумма возвратов");
//        fieldNameColumnName.put("", "Сумма скидок");
//        fieldNameColumnName.put("", "Баланс");
//        fieldNameColumnName.put("", "Прибыль");
//        fieldNameColumnName.put("", "Дата события (последнее)");
//        fieldNameColumnName.put("", "Текст события (последнее)");
//        fieldNameColumnName.put("", "Когда изменен");
//        fieldNameColumnName.put("", "Кто изменил");

        return fieldNameColumnName;
        }
    }

