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
import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
Таблица контрагентов
 */
@SpringComponent
@UIScope
public class ContragentsListGridLayout extends HorizontalLayout {
    private final ContractorService contractorService;

    private final BankAccountService bankAccountService;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<ContractorDto> contractorDtoGrid;
    private ContragentsList parent;

    public ContragentsListGridLayout(ContractorService contractorService, BankAccountService bankAccountService) {
        this.contractorService = contractorService;
        this.bankAccountService = bankAccountService;
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

        Grid.Column<ContractorDto> name = contractorDtoGrid.addColumn(ContractorDto::getName)
                .setHeader("Наименование").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> code = contractorDtoGrid.addColumn(ContractorDto::getCode)
                .setHeader("Код").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> phone = contractorDtoGrid.addColumn(ContractorDto::getPhone)
                .setHeader("Телефон").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> fax = contractorDtoGrid.addColumn(ContractorDto::getFax)
                .setHeader("Факс").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> email = contractorDtoGrid.addColumn(ContractorDto::getEmail)
                .setHeader("Email").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> status = contractorDtoGrid.addColumn(ContractorDto::getStatus)
                .setHeader("Статус").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> discontCard = contractorDtoGrid.addColumn(ContractorDto::getNumberDiscountCard)
                .setHeader("Дисконтная карта").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> address = contractorDtoGrid.addColumn(ContractorDto::getAddress)
                .setHeader("Фактический адрес").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> comment = contractorDtoGrid.addColumn(ContractorDto::getComment)
                .setHeader("Комментарий").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> groups = contractorDtoGrid.addColumn(ContractorDto::getContractorGroup)
                .setHeader("Группы").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> prices = contractorDtoGrid.addColumn(ContractorDto::getTypeOfPrice)
                .setHeader("Цены").setSortable(true).setAutoWidth(true);

        contractorDtoGrid.addItemDoubleClickListener(event -> parent.editFormActivate(event.getItem()));

//        Grid.Column<ContractorDto> edit = contractorDtoGrid.addColumn(rowEdit()).setHeader("Изменить")
//                .setSortable(true).setAutoWidth(true);
        Grid.Column<ContractorDto> delete = contractorDtoGrid.addColumn(rowDelete()).setHeader("Удалить")
                .setSortable(true).setAutoWidth(true);

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
        columnToggleContextMenu.addColumnToggleItem("Цены", prices);
//        columnToggleContextMenu.addColumnToggleItem("Изменить", edit);
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

        List<BankAccountDto> filteredList = new ArrayList<>();

        List<BankAccountDto> bankAccountDtoList = bankAccountService.getAll();
        bankAccountDtoList.stream()
                .filter(bankAccountDto1 -> {
                    if (bankAccountDto1.getContractor() != null &&
                            Objects.equals(contractorDto.getId(), bankAccountDto1.getContractor().getId())) {
                        filteredList.add(bankAccountDto1);
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("Отмена");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel, delete);

        Dialog dialog = new Dialog();
        dialog.add("Подтвердите удаление");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        if (filteredList.size() == 0) {
            dialog.open();
        }

        delete.addClickListener(event -> {
            contractorService.deleteById(contractorDto.getId());
            refreshDate();
            dialog.close();
        });

        cancel.addClickListener(event -> {
            dialog.close();
        });

        if (filteredList.size() != 0) {
            Button canceled = new Button("Отмена");
            HorizontalLayout horizontalLayout = new HorizontalLayout(canceled);
            Dialog dialog2 = new Dialog();
            dialog2.add("Не удалены привязанные расчетные счета");
            dialog2.add(new VerticalLayout());
            dialog2.add(horizontalLayout);
            canceled.addClickListener(event -> {
                dialog2.close();
            });
            dialog2.open();
        }

    }

    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }

}

