package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.ContractorService;

/*
Таблица контрагентов
 */
@SpringComponent
@UIScope
public class ContragentsListGridLayout extends HorizontalLayout {
    private final ContractorService contractorService;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<ContractorDto> contractorDtoGrid;
    private ContragentsList parent;

    public ContragentsListGridLayout(ContractorService contractorService) {
        this.contractorService = contractorService;
        contractorDtoGrid = initGrid();
        contractorDtoGrid.setItems(contractorService.getAll());
        add(contractorDtoGrid, settingButton);
        setSizeFull();
    }

    public void refreshDate() {
        removeAll();
        contractorDtoGrid.setItems(contractorService.getAll());
        add(contractorDtoGrid, settingButton);
        setSizeFull();
    }

    private Grid<ContractorDto> initGrid() {
        Grid<ContractorDto> contractorDtoGrid = new Grid<>(ContractorDto.class, false);
        contractorDtoGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        contractorDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        contractorDtoGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        contractorDtoGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        contractorDtoGrid.setHeightByRows(true);

        contractorDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Grid.Column<ContractorDto> name = contractorDtoGrid.addColumn(ContractorDto::getName).setHeader("Наименование").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> code = contractorDtoGrid.addColumn(ContractorDto::getCode).setHeader("Код").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> phone = contractorDtoGrid.addColumn(ContractorDto::getPhone).setHeader("Телефон").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> fax = contractorDtoGrid.addColumn(ContractorDto::getFax).setHeader("Факс").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> email = contractorDtoGrid.addColumn(ContractorDto::getEmail).setHeader("Email").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> status = contractorDtoGrid.addColumn(ContractorDto::getStatus).setHeader("Статус").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> discontCard = contractorDtoGrid.addColumn(ContractorDto::getNumberDiscountCard).setHeader("Дисконтная карта").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> address = contractorDtoGrid.addColumn(ContractorDto::getAddress).setHeader("Фактический адрес").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> comment = contractorDtoGrid.addColumn(ContractorDto::getComment).setHeader("Комментарий").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> groups = contractorDtoGrid.addColumn(ContractorDto::getContractorGroup).setHeader("Группы").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractorDto> contractorType = contractorDtoGrid.addColumn(ContractorDto::getLegalDetailTypeOfContractorName).setHeader("Тип Контрагента").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> fullName = contractorDtoGrid.addColumn(ContractorDto::getName).setHeader("Полное наименование").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractorDto> legalAddress = contractorDtoGrid.addColumn(ContractorDto::getLegalDetailAddress).setHeader("Юридический Адрес").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractorDto> inn = contractorDtoGrid.addColumn(ContractorDto::getLegalDetailInn).setHeader("ИНН").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractorDto> kpp = contractorDtoGrid.addColumn(ContractorDto::getLegalDetailKpp).setHeader("КПП").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> prices = contractorDtoGrid.addColumn(ContractorDto::getTypeOfPrice).setHeader("Цены").setSortable(true).setAutoWidth(true);

        contractorDtoGrid.addItemDoubleClickListener(event -> parent.editFormActivate(event.getItem()));

        Grid.Column<ContractorDto> edit = contractorDtoGrid.addColumn(rowEdit()).setHeader("Изменить").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> delete = contractorDtoGrid.addColumn(rowDelete()).setHeader("Удалить").setSortable(true).setAutoWidth(true);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ContractorDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Наименование", name);
        columnToggleContextMenu.addColumnToggleItem("Код", code);
        columnToggleContextMenu.addColumnToggleItem("Телефон", phone);
        columnToggleContextMenu.addColumnToggleItem("Факс", fax);
        columnToggleContextMenu.addColumnToggleItem("Email", email);
        columnToggleContextMenu.addColumnToggleItem("Статус", status);
        columnToggleContextMenu.addColumnToggleItem("Дисконтная карта", discontCard);
        columnToggleContextMenu.addColumnToggleItem("Фактический адрес", address);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", comment);
        columnToggleContextMenu.addColumnToggleItem("Группы", groups);
//        columnToggleContextMenu.addColumnToggleItem("Тип Контрагента", contractorType);
        columnToggleContextMenu.addColumnToggleItem("Полное наименование", fullName);
//        columnToggleContextMenu.addColumnToggleItem("Юридический Адрес", legalAddress);
//        columnToggleContextMenu.addColumnToggleItem("ИНН", inn);
//        columnToggleContextMenu.addColumnToggleItem("КПП", kpp);
        columnToggleContextMenu.addColumnToggleItem("Цены", prices);
        columnToggleContextMenu.addColumnToggleItem("Изменить", edit);
        columnToggleContextMenu.addColumnToggleItem("Удалить", delete);
        return contractorDtoGrid;
    }

    private TemplateRenderer<ContractorDto> rowEdit() {
        return TemplateRenderer.<ContractorDto>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Изменить</vaadin-button>")
                .withEventHandler("handleClick", contractor ->
                        editEndClose(contractor));
    }

    private TemplateRenderer<ContractorDto> rowDelete() {
        return TemplateRenderer.<ContractorDto>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Удалить</vaadin-button>")
                .withEventHandler("handleClick", contractor ->
                        deleteAndClose(contractor));
    }

    private void editEndClose(ContractorDto contractorDto) {
        parent.editFormActivate(contractorDto);
    }

    private void deleteAndClose(ContractorDto contractorDto) {

        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("Отмена");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel, delete);

        Dialog dialog = new Dialog();
        dialog.add("Подтвердите удаление");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        dialog.open();

        delete.addClickListener(event -> {
            contractorService.deleteById(contractorDto.getId());
            refreshDate();
            dialog.close();
        });

        cancel.addClickListener(event -> {
            dialog.close();
        });
    }

    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }

}

