package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.purchases.forms.EditInvoiceForm;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.InternalOrderDto;
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

    public Button settingButton = new Button(new Icon(VaadinIcon.COG));
    Grid<SupplierInvoiceDto> supplierInvoiceDtoGrid = new Grid<>(SupplierInvoiceDto.class, false);

    public SupplierInvoiceGridLayout() {
        setSizeFull();
        initSupplierInvoiceGrid();
    }

    public void initSupplierInvoiceGrid(){

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
        //---???????????????? ?????????????? ?? ?????????? ???????????? ??????????????.
        SupplierInvoiceService supplierInvoiceService = new SupplierInvoiceServiceImpl("/api/supplier_invoices",retrofit);

        supplierInvoiceDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //?????????????????? ???????????????? ?? ???????????? ??????????????.
        Grid.Column<SupplierInvoiceDto> id = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getId).setHeader("ID").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> supplierInvoiceDtoColumn = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getInvoiceNumber).setHeader("???????? ???????????????????? ???").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> width = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateInvoiceNumber).setHeader("???? ????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> organization = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getOrganization).setHeader("??????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> warehouse = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getWarehouse).setHeader("??????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> contrAgent = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContrAgent).setHeader("????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> contract = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContract).setHeader("??????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> paymentDate = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDatePay).setHeader("???????? ????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> project =  supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getProject).setHeader("????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> numbr = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getIncomingNumber).setHeader("???????????????? ??????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> withDate = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateIncomingNumber).setHeader("???? ????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> position = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddPosition).setHeader("???????????????????????? ??????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> comment = supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddComment).setHeader("??????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<SupplierInvoiceDto> edit = supplierInvoiceDtoGrid.addColumn(rowEdit(supplierInvoiceService)).setHeader("????????????????").setSortable(true).setAutoWidth(true);;
        Grid.Column<SupplierInvoiceDto> delete = supplierInvoiceDtoGrid.addColumn(rowDelete(supplierInvoiceService)).setHeader("??????????????").setSortable(true).setAutoWidth(true);;

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<SupplierInvoiceDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("ID", id);
        columnToggleContextMenu.addColumnToggleItem("???????? ???????????????????? ???", supplierInvoiceDtoColumn);
        columnToggleContextMenu.addColumnToggleItem("???? ????????", width);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", organization);
        columnToggleContextMenu.addColumnToggleItem("??????????", warehouse);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", contrAgent);
        columnToggleContextMenu.addColumnToggleItem("??????????????", contract);
        columnToggleContextMenu.addColumnToggleItem("???????? ????????????", paymentDate);
        columnToggleContextMenu.addColumnToggleItem("????????????", project);
        columnToggleContextMenu.addColumnToggleItem("???????????????? ??????????", numbr);
        columnToggleContextMenu.addColumnToggleItem("???? ????????", withDate);
        columnToggleContextMenu.addColumnToggleItem("???????????????????????? ??????????????", position);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", comment);
        columnToggleContextMenu.addColumnToggleItem("????????????????", edit);
        columnToggleContextMenu.addColumnToggleItem("??????????????", delete);

        supplierInvoiceDtoGrid.setItems(supplierInvoiceService.getAll()); //???????????????????? ???????????? ???? ????????????????.

        supplierInvoiceDtoGrid.setDetailsVisibleOnClick(false);
        supplierInvoiceDtoGrid.setItemDetailsRenderer(rowEdit(supplierInvoiceService));
        supplierInvoiceDtoGrid.setItemDetailsRenderer(rowDelete(supplierInvoiceService));

        supplierInvoiceDtoGrid.addSelectionListener(selection -> { //?????????????? ????????????????.

            Set<SupplierInvoiceDto> set = selection.getAllSelectedItems();

            Iterator<SupplierInvoiceDto> iterator = set.iterator();

            while(iterator.hasNext()){
                gridEntityId.add(iterator.next().getId());
            }
        });

        add(supplierInvoiceDtoGrid,settingButton);
    }

    private void editEndSave(SupplierInvoiceService supplierInvoiceService,SupplierInvoiceDto person){
        EditInvoiceForm editInvoiceForm = new EditInvoiceForm(parentLayer, returnLayer, person.getId());
        parentLayer.removeAll();
        parentLayer.add(editInvoiceForm);
    }

    private TemplateRenderer<SupplierInvoiceDto> rowEdit(SupplierInvoiceService supplierInvoiceService) {
        return TemplateRenderer.<SupplierInvoiceDto>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">????????????????</vaadin-button>")
                .withEventHandler("handleClick", person ->
                        editEndSave(supplierInvoiceService,person));
    }
    private void deleteAndClose(SupplierInvoiceService supplierInvoiceService,SupplierInvoiceDto person){

        Button delete = new Button("??????????????");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("????????????");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel,delete);

        Dialog dialog = new Dialog();
        dialog.add("?????????????????????? ????????????????");
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
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">??????????????</vaadin-button>")
                .withEventHandler("handleClick", person ->
                        deleteAndClose(supplierInvoiceService,person));

    }

}
