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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;


@SpringComponent
@UIScope
@Log4j2
public class ProductionStepsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField = new TextField();
    private final ProductionStageService productionStageService;
    private final Grid<ProductionStageDto> productionStageDtoGrid = new Grid<>(ProductionStageDto.class, false);


    public ProductionStepsGridLayout (ProductionStageService productionStageService) {
        this.productionStageService = productionStageService;


        // Grid.Column<ProductionStageDto> idColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getId).setHeader("Id");
        Grid.Column<ProductionStageDto> nameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getName).setHeader("Наименование");
        Grid.Column<ProductionStageDto> descriptionColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getDescription).setHeader("Описание");

        Grid.Column<ProductionStageDto> generalAccessColumn = productionStageDtoGrid.addColumn(ProductionStageDto::isGeneralAccess).setHeader("Общий доступ");
        Grid.Column<ProductionStageDto> ownerDepartmentNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getOwnerDepartmentName).setHeader("Владелец-отдел");
        Grid.Column<ProductionStageDto> ownerEmployeeNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getOwnerEmployeeName).setHeader("Владелец-сотрудник");
        Grid.Column<ProductionStageDto> dateOfEditColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getDateOfEdit).setHeader("Когда изменен");
        Grid.Column<ProductionStageDto> editorEmployeeNameColumn = productionStageDtoGrid.addColumn(ProductionStageDto::getEditorEmployeeName).setHeader("Кто изменил");

        productionStageDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

       List<ProductionStageDto> productionStageDtoList = productionStageService.getAll();
       productionStageDtoGrid.setItems(productionStageDtoList);

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
