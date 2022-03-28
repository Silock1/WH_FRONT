package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.ProductionStageDto;

public class ProductionStepsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<ProductionStageDto> productionStageDtoGrid = new Grid<>(ProductionStageDto.class, false);


    public ProductionStepsGridLayout (TextField selectedTextField) {
        this.selectedTextField = selectedTextField;

        // Grid.Column<ProductionStageDto> idColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getId).setHeader("Id");
        Grid.Column<ProductionStageDto> nameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getName).setHeader("Наименование");
        Grid.Column<ProductionStageDto> descriptionColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getDescription).setHeader("Описание");

        Grid.Column<ProductionStageDto> generalAccessColumn = productionStageDtoGrid.addColumn(ProductionStageDto::isGeneralAccess).setHeader("Общий доступ");
        Grid.Column<ProductionStageDto> ownerDepartmentNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getOwnerDepartmentName).setHeader("Владелец-отдел");
        Grid.Column<ProductionStageDto> ownerEmployeeNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getOwnerEmployeeName).setHeader("Владелец-сотрудник");
        Grid.Column<ProductionStageDto> dateOfEditColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getDateOfEdit).setHeader("Когда изменен");
        Grid.Column<ProductionStageDto> editorEmployeeNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getEditorEmployeeName).setHeader("Кто изменил");

        productionStageDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(
                menuButton);
//        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Наименование", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Описание", descriptionColumn);

        columnToggleContextMenu.addColumnToggleItem("Общий доступ", generalAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-отдел", ownerDepartmentNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-сотрудник", ownerEmployeeNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Когда изменен", dateOfEditColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", editorEmployeeNameColumn);

        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(productionStageDtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ProductionStageDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
