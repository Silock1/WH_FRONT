package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.purchases.forms.EditInvoiceForm;
import com.warehouse_accounting.models.dto.SupplierInvoiceDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.impl.SupplierInvoiceServiceImpl;
import com.warehouse_accounting.services.interfaces.SupplierInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@UIScope
@SpringComponent
public class SupplierInvoiceGridLayout extends HorizontalLayout {

    public List<Long> gridEntityId = new ArrayList<>(); //
    public Div parentLayer; //
    public Component returnLayer; //
    @Autowired
    SupplierInvoiceService supplierInvoiceService;
    Grid<SupplierInvoiceDto> supplierInvoiceDtoGrid = new Grid<>(SupplierInvoiceDto.class, false);

    public SupplierInvoiceGridLayout() {
//        initSupplierInvoiceGrid();
    }

    public Grid<SupplierInvoiceDto> initSupplierInvoiceGrid(){

//        Grid<SupplierInvoiceDto> supplierInvoiceDtoGrid = new Grid<>(SupplierInvoiceDto.class, false);

        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES );
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        supplierInvoiceDtoGrid.setHeightByRows(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //---Добавить чекбокс в самый первый столбец.
        SupplierInvoiceService supplierInvoiceService = new SupplierInvoiceServiceImpl("/api/supplier_invoices",retrofit);

        supplierInvoiceDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //Добавляет чекбоксы в первый столбец.
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getId).setHeader("ID").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getInvoiceNumber).setHeader("Счет поставщика №").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateInvoiceNumber).setHeader("От даты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getOrganization).setHeader("Организация").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getWarehouse).setHeader("Склад").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContrAgent).setHeader("Контрагент").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContract).setHeader("Договор").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDatePay).setHeader("Дата оплаты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getProject).setHeader("Проект").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getIncomingNumber).setHeader("Входящий номер").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateIncomingNumber).setHeader("От даты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddPosition).setHeader("Наименование позиции").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddComment).setHeader("Комментарий").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(rowEdit(supplierInvoiceService)).setHeader("Изменить").setSortable(true).setAutoWidth(true);;
        supplierInvoiceDtoGrid.addColumn(rowDelete(supplierInvoiceService)).setHeader("Удалить").setSortable(true).setAutoWidth(true);;

        supplierInvoiceDtoGrid.setItems(supplierInvoiceService.getAll()); //Отображает данные на странице.

        supplierInvoiceDtoGrid.setDetailsVisibleOnClick(false);
        supplierInvoiceDtoGrid.setItemDetailsRenderer(rowEdit(supplierInvoiceService));
        supplierInvoiceDtoGrid.setItemDetailsRenderer(rowDelete(supplierInvoiceService));

        supplierInvoiceDtoGrid.addSelectionListener(selection -> { //Слушает чекбоксы.

            Set<SupplierInvoiceDto> set = selection.getAllSelectedItems();

            Iterator<SupplierInvoiceDto> iterator = set.iterator();

            while(iterator.hasNext()){
                gridEntityId.add(iterator.next().getId());
            }
        });

        return supplierInvoiceDtoGrid;
    }

    private void editEndSave(SupplierInvoiceService supplierInvoiceService,SupplierInvoiceDto person){
        EditInvoiceForm editInvoiceForm = new EditInvoiceForm(parentLayer, returnLayer, person.getId());
        parentLayer.removeAll();
        parentLayer.add(editInvoiceForm);
    }

    private TemplateRenderer<SupplierInvoiceDto> rowEdit(SupplierInvoiceService supplierInvoiceService) {
        return TemplateRenderer.<SupplierInvoiceDto>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Изменить</vaadin-button>")
                .withEventHandler("handleClick", person ->
                        editEndSave(supplierInvoiceService,person));
    }
    private void deleteAndClose(SupplierInvoiceService supplierInvoiceService,SupplierInvoiceDto person){

        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("Отмена");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel,delete);

        Dialog dialog = new Dialog();
        dialog.add("Подтвердите удаление");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        dialog.open();

        delete.addClickListener(event -> {
            supplierInvoiceService.deleteById(person.getId());
            removeAll();
            supplierInvoiceDtoGrid.setItems(supplierInvoiceService.getAll());
            add(supplierInvoiceDtoGrid);
            setSizeFull();
            dialog.close();
        });

        cancel.addClickListener(event -> {
            dialog.close();
        });
    }

    private TemplateRenderer<SupplierInvoiceDto> rowDelete(SupplierInvoiceService supplierInvoiceService) {

        return TemplateRenderer.<SupplierInvoiceDto>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Удалить</vaadin-button>")
                .withEventHandler("handleClick", person ->
                        deleteAndClose(supplierInvoiceService,person));

    }

}
