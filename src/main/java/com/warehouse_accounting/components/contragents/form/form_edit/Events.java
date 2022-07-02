package com.warehouse_accounting.components.contragents.form.form_edit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.grids.CallsGridLayout;
import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.CallService;

import java.util.stream.Collectors;

@UIScope
public class Events extends VerticalLayout {

    private final transient CallService callService;

    private ContractorDto contractorDto;

    private final Grid<CallDto> callDtoGrid;

    public Events(CallService callService, ContractorDto contractorDto) {
        this.callService = callService;
        this.contractorDto = contractorDto;
        this.callDtoGrid = initTable();
        add(initTabs(),initNoteWriter(),this.callDtoGrid);
    }

    private Grid<CallDto> initTable() {
        Grid<CallDto> grid = new Grid<>(CallDto.class,false);
        grid.setItems(contractorDto.getCallIds().stream().map(x -> callService.getById(x)).collect(Collectors.toList()));
//        grid.setColumns(CallsGridLayout.getVisibleColumn().keySet().toArray(String[]::new));
//
//        CallsGridLayout.getVisibleColumn().forEach((key, value) -> grid.getColumnByKey(key).setHeader(value));

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return grid;
    }

    private Component initNoteWriter() {
        return new Div();
    }

    private Tabs initTabs() {
        final String allEventsName = "Все события";
        final String notesName = "Заметки";
        final String callsName = "Звонки";
        Tab allEvents = new Tab(allEventsName);
        Tab notes = new Tab(notesName);
        Tab calls = new Tab(callsName);
        
        Tabs tabs = new Tabs(allEvents,notes,calls);
        tabs.addSelectedChangeListener(event -> {
            //В будущем в этом блоке должны включаться/выключаться заметки
            switch (event.getSelectedTab().getLabel()) {
                case allEventsName  : callDtoGrid.setVisible(true); break;
                case notesName: callDtoGrid.setVisible(false); break;
                case callsName: callDtoGrid.setVisible(true); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + event.getSelectedTab().getLabel());
            }
        });

        return tabs;
    }
}
