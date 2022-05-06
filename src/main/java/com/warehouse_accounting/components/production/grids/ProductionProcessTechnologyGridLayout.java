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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.ProductionProcessTechnologyService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UIScope
@SpringComponent
public class ProductionProcessTechnologyGridLayout extends HorizontalLayout {
    private final ProductionProcessTechnologyService productionProcessTechnologyService;
    private List<ProductionProcessTechnologyDto> productionProcessTechnologyDtoList = new ArrayList<>();
    @Getter
    private final Grid<ProductionProcessTechnologyDto> productionProcessTechnologyDtoGrid = new Grid<>(ProductionProcessTechnologyDto.class, false);

    public ProductionProcessTechnologyGridLayout(ProductionProcessTechnologyService productionProcessTechnologyService) {
        this.productionProcessTechnologyService = productionProcessTechnologyService;

        initializingGrid();
    }

    private void initializingGrid() {

        Grid.Column<ProductionProcessTechnologyDto> idColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getId).setHeader("Id");
        idColumn.setVisible(false);
        Grid.Column<ProductionProcessTechnologyDto> nameColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getName).setHeader("Наименование");
        Grid.Column<ProductionProcessTechnologyDto> descriptionColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getDescription).setHeader("Описание");
        Grid.Column<ProductionProcessTechnologyDto> generalAccessColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::isGeneralAccess).setHeader("Общий доступ");
        Grid.Column<ProductionProcessTechnologyDto> ownerDepartmentNameColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getOwnerDepartmentName).setHeader("Владелец-отдел");
        Grid.Column<ProductionProcessTechnologyDto> ownerEmployeeNameColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getOwnerEmployeeName).setHeader("Владелец-сотрудник");
        Grid.Column<ProductionProcessTechnologyDto> dateOfEditColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getDateOfEdit).setHeader("Когда изменен");
        Grid.Column<ProductionProcessTechnologyDto> editorEmployeeNameColumn = productionProcessTechnologyDtoGrid.addColumn(ProductionProcessTechnologyDto::getEditorEmployeeName).setHeader("Кто изменил");

        productionProcessTechnologyDtoList = productionProcessTechnologyService.getAll();
        productionProcessTechnologyDtoList.sort(Comparator.comparingLong(ProductionProcessTechnologyDto::getId));
        productionProcessTechnologyDtoGrid.setItems(productionProcessTechnologyDtoList);

        productionProcessTechnologyDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ProductionProcessTechnologyGridLayout.ColumnToggleContextMenu columnToggleContextMenu = new ProductionProcessTechnologyGridLayout.ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
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

        setSizeFull();
        add(productionProcessTechnologyDtoGrid, headerLayout);
    }

    public void updateGrid() {
        productionProcessTechnologyDtoList = productionProcessTechnologyService.getAll();
        productionProcessTechnologyDtoList.sort(Comparator.comparingLong(ProductionProcessTechnologyDto::getId));
        productionProcessTechnologyDtoGrid.getDataProvider().refreshAll();
        productionProcessTechnologyDtoGrid.setItems(productionProcessTechnologyDtoList);
        //productionProcessTechnologyDtoGrid.getDataProvider().refreshAll();
    }


    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ProductionProcessTechnologyDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }


}
