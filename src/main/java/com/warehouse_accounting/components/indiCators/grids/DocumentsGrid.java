package com.warehouse_accounting.components.indiCators.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.indiCators.DocumentsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.DocumentDto;
import com.warehouse_accounting.services.interfaces.DocumentService;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Route(value = "documentsGrid", layout = AppView.class)
public class DocumentsGrid extends HorizontalLayout {

    private final DocumentService<DocumentDto> documentService;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<DocumentDto> documentDtoGrid;

    private DocumentsView parent;

    public DocumentsGrid(DocumentService<DocumentDto> documentService) {
        this.documentService = documentService;
        documentDtoGrid = initGrid();
        documentDtoGrid.setItems(documentService.getAll());
        add(documentDtoGrid, settingButton);
        setSizeFull();
    }

    public void refreshDate() {
        removeAll();
        documentDtoGrid.setItems(documentService.getAll());
        add(documentDtoGrid, settingButton);
        setSizeFull();
    }


    private Grid<DocumentDto> initGrid() {
        Grid<DocumentDto> docGrid = new Grid<>(DocumentDto.class, false);

        Grid.Column<DocumentDto> typeColumn = docGrid.addColumn(DocumentDto::getType)
                .setHeader("Тип документа").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> numberColumn = docGrid.addColumn(DocumentDto::getDocNumber)
                .setHeader("№").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> sumColumn = docGrid.addColumn(DocumentDto::getSum)
                .setHeader("Сумма").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> fromWarehouse = docGrid.addColumn(DocumentDto::getWarehouseFromName)
                .setHeader("Со склада").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> toWarehouse = docGrid.addColumn(DocumentDto::getWarehouseToName)
                .setHeader("На склад").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> companyNameColumn = docGrid.addColumn(DocumentDto::getCompanyName)
                .setHeader("Организация").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> contrAgent = docGrid.addColumn(DocumentDto::getContrAgentName)
                .setHeader("Контрагент").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> projectNameColumn = docGrid.addColumn(DocumentDto::getProjectName)
                .setHeader("Проект").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> chanelSalesColumn = docGrid.addColumn(DocumentDto::getSalesChannelName)
                .setHeader("Канал продаж").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> contractColumn = docGrid.addColumn(DocumentDto::getContractNumber)
                .setHeader("Договор").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> isSharedAccessColumn = docGrid.addColumn(DocumentDto::getIsSharedAccess)
                .setHeader("Общий доступ").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> ownerDepartmentColumn = docGrid.addColumn(DocumentDto::getDepartmentName)
                .setHeader("Владелец отдел").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> ownerEmployeeColumn = docGrid.addColumn(DocumentDto::getEmployeeFirstname)
                .setHeader("Владелец сотрудник").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> isSendColumn = docGrid.addColumn(DocumentDto::getSent)
                .setHeader("Отправлено").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> isPrintColumn = docGrid.addColumn(DocumentDto::getPrint)
                .setHeader("Напечатано").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> commentColumn = docGrid.addColumn(DocumentDto::getComments)
                .setHeader("Комментарий").setSortable(true).setAutoWidth(true);
        Grid.Column<DocumentDto> whoChangeColumn = docGrid.addColumn(DocumentDto::getUpdatedFromEmployeeFirstname)
                .setHeader("Кто изменил").setSortable(true).setAutoWidth(true);

        docGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<DocumentDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Тип документа", typeColumn);
        columnToggleContextMenu.addColumnToggleItem("№", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumColumn);
        columnToggleContextMenu.addColumnToggleItem("Со склада", fromWarehouse);
        columnToggleContextMenu.addColumnToggleItem("На склад", toWarehouse);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contrAgent);
        columnToggleContextMenu.addColumnToggleItem("Проект", projectNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Канал продаж", chanelSalesColumn);
        columnToggleContextMenu.addColumnToggleItem("Договор", contractColumn);
        columnToggleContextMenu.addColumnToggleItem("Общий доступ", isSharedAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец отдел", ownerDepartmentColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец сотрудник", ownerEmployeeColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", isSendColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", isPrintColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", whoChangeColumn);

        docGrid.setSizeFull();
        docGrid.setHeightByRows(true);
        return docGrid;
    }

    public void setParent(DocumentsView parent) {
        this.parent = parent;
    }

    public Grid<DocumentDto> getDocumentDtoGrid() {
        return documentDtoGrid;
    }
}
