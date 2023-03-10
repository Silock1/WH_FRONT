package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UIScope
@SpringComponent
public class GoodsGridLayoutForTechnologicalMap extends VerticalLayout {

    private final ProductGroupService productGroupService;
    private final ProductService productService;


    private final Div leftThreeGridDiv = new Div();
    private final Div gridDiv = new Div();
    private final String widthLeftTreeGrid = "10%";
    private final String widthGrid = "90%";
    private TreeGrid<ProductGroupDto> treeGrid;
    private TextField selectedTextField;
    private Grid<ProductDto> productDtoGrid;
    private GoodsAndServiceView parentLayer;
    private ProductDto productDto;


    public GoodsGridLayoutForTechnologicalMap(ProductGroupService productGroupService, ProductService productService) {

        this.productGroupService = productGroupService;
        this.productService = productService;



    }

    public void initGrid() {
        removeAll();
        productDto = null;
       productDtoGrid = new Grid<>(ProductDto.class, false);
       productDtoGrid.setItems(productService.getAll());
        Grid.Column<ProductDto> idColumn = productDtoGrid.addColumn(ProductDto::getId).setHeader("Id");
        Grid.Column<ProductDto> nameColumn = productDtoGrid.addColumn(ProductDto::getName).setHeader("????????????????????????");
        Grid.Column<ProductDto> weightColumn = productDtoGrid.addColumn(ProductDto::getWeight).setHeader("??????????");
        Grid.Column<ProductDto> volumeColumn = productDtoGrid.addColumn(ProductDto::getVolume).setHeader("??????????");
        Grid.Column<ProductDto> descriptionColumn = productDtoGrid.addColumn(ProductDto::getDescription).setHeader("????????????????");
        Grid.Column<ProductDto> unitsOfMeasureColumn = productDtoGrid.addColumn(
                dto -> dto.getUnitsOfMeasureDto().getName()).setHeader("?????????????? ??????????????????");
        Grid.Column<ProductDto> archiveColumn = productDtoGrid.addColumn(ProductDto::getArchive).setHeader("?? ????????????");
        Grid.Column<ProductDto> contractorColumn = productDtoGrid.addColumn(
                dto -> dto.getContractor().getName()).setHeader("??????????????????");
        Grid.Column<ProductDto> taxColumn = productDtoGrid.addColumn(
                dto -> dto.getTaxSystem().getName()).setHeader("?????????????????? ??????????????");
        Grid.Column<ProductDto> purchasePriceColumn = productDtoGrid.addColumn(ProductDto::getPurchasePrice).setHeader("????????");
        Grid.Column<ProductDto> groupColumn = productDtoGrid.addColumn(
                dto -> dto.getProductGroup().getName()).setHeader("????????????");
        Grid.Column<ProductDto> attributeOfCalculationObjectColumn = productDtoGrid.addColumn(
                dto -> dto.getAttributeOfCalculationObject().getName()).setHeader("???????????? ??????????????????");
      productDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
     productDtoGrid.addSelectionListener(selectionEvent -> tempEvent(selectionEvent.getAllSelectedItems()));

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        ColumnToggleContextMenu<ProductDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????????", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", weightColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", volumeColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????", descriptionColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????????? ??????????????????", unitsOfMeasureColumn);
        columnToggleContextMenu.addColumnToggleItem("?? ????????????", archiveColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????", contractorColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????????????? ??????????????", taxColumn);
        columnToggleContextMenu.addColumnToggleItem("????????", purchasePriceColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????", groupColumn);
        columnToggleContextMenu.addColumnToggleItem("???????????? ??????????????????", attributeOfCalculationObjectColumn);

        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);

//        GridMultiSelectionModel<ProductDto> selectionModel =
//                (GridMultiSelectionModel<ProductDto>) productDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
//        //  technologicalMapDtoGrid.addSelectionListener(selectionEvent -> tempEvent(technologicalMapDtoGrid.getSelectedItems()));
//        //technologicalMapDtoGrid.addMu
//        selectionModel.addMultiSelectionListener(multiSelectionEvent -> {
//            tempEvent(multiSelectionEvent.getAddedSelection());
//            tempDeleteEvent(multiSelectionEvent.getRemovedSelection());
//        });
        add(productDtoGrid, headerLayout);
        //productDtoGrid.setSizeFull();
        //add(productDtoGrid);
      //  setSizeFull();
    }

    private void tempDeleteEvent(Set<ProductDto> removedSelection) {
        productDto = null;
    }

    private void tempEvent(Set<ProductDto> addedSelection) {
        productDto = (ProductDto) addedSelection.toArray()[0];
    }

    public ProductDto getSelected() {
        return productDto;
    }
}
