package com.warehouse_accounting.components.production.grids;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.dialog.NewGoodsDialog;
import com.warehouse_accounting.components.production.forms.NewTechnologicalMapPanel;
import com.warehouse_accounting.components.sales.forms.order.components.DocumentOperationsToolbar;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.TechnologicalMapMaterialDto;
import com.warehouse_accounting.services.impl.ProductServiceImpl;
import com.warehouse_accounting.services.impl.ProductionStageServiceImpl;
import com.warehouse_accounting.services.impl.TechnologicalMapMaterialsServiceImpl;
import com.warehouse_accounting.services.impl.TechnologicalMapServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
@Log4j2
public class NewTechnologicalMapGridLayout extends VerticalLayout {
    private final ProductionStageServiceImpl productionStageService;
    private final TechnologicalMapServiceImpl technologicalMapService;
    private final TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService;
    private final ProductServiceImpl productService;
    private final GoodsGridLayoutForTechnologicalMap goodsGridLayout;
    private final NewGoodsDialog newGoodsDialog;
    private Dialog dialogMaterials;
    private NewTechnologicalMapPanel newTechnologicalMapPanel;
    private VerticalLayout dialogLayout;
    private Grid<TechnologicalMapMaterialDto> grid;
    @Getter
    @Setter
    private ProductDto selectedProductDto;
    private List<TechnologicalMapMaterialDto> technologicalMapMaterialDtoList = new ArrayList<>();

    public NewTechnologicalMapGridLayout(ProductionStageServiceImpl productionStageService, TechnologicalMapServiceImpl technologicalMapService, TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService, ProductServiceImpl productService, GoodsGridLayoutForTechnologicalMap goodsGridLayout, NewGoodsDialog newGoodsDialog) {
        this.productionStageService = productionStageService;
        this.technologicalMapService = technologicalMapService;
        this.technologicalMapMaterialsService = technologicalMapMaterialsService;
        this.productService = productService;
        this.goodsGridLayout = goodsGridLayout;
        this.newGoodsDialog = newGoodsDialog;
        dialogMaterials = new Dialog();
        createDialogLayout(dialogMaterials);
        dialogMaterials.add(dialogLayout);
        dialogMaterials.setSizeFull();
        selectedProductDto = null;
    }

    public void init() {
        //removeAll();
        dialogMaterials = new Dialog();
        createDialogLayout(dialogMaterials);
        dialogMaterials.add(dialogLayout);
        dialogMaterials.setSizeFull();
    }

    public void open(NewTechnologicalMapPanel newTechnologicalMapPanel) {
        this.newTechnologicalMapPanel = newTechnologicalMapPanel;
        dialogMaterials.open();
    }

    public void createDialogLayout(Dialog dialog) {
        goodsGridLayout.initGrid();
        H2 headline = new H2("Выбор товара");
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        MenuBar menuBar = new MenuBar();
        MenuItem create = menuBar.addItem("Создать");
        create.add(new Icon(VaadinIcon.CARET_DOWN));
        SubMenu createSubMenu = create.getSubMenu();

        MenuItem product = createSubMenu.addItem("Товар");
        product.addClickListener(event -> {
            Dialog dialogNewGoods = new Dialog();
            DocumentOperationsToolbar menu = new DocumentOperationsToolbar();
            menu.setCloseHandler(() -> {
                dialogNewGoods.close();
            });
            menu.setSaveHandler(() -> {
                newGoodsDialog.save();
                dialogNewGoods.close();
                goodsGridLayout.initGrid();
            });
            dialogNewGoods.add(menu);
            dialogNewGoods.add(newGoodsDialog);
            dialogNewGoods.setSizeFull();
            dialogNewGoods.open();
        });

        MenuItem group = createSubMenu.addItem("Группу");
        group.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        Button filter = new Button("Фильтр");

        Div divTollBar = new Div(headline, refreshButton, menuBar, filter);
        divTollBar.setClassName("divToolBar");
        divTollBar.getStyle().set("display", "flex");

        Button chooseButton = new Button("Выбрать", buttonClickEvent -> {
            if (goodsGridLayout.getSelected() != null) {
                selectedProductDto = (goodsGridLayout.getSelected());
            }
            if (selectedProductDto != null) {
                if (newTechnologicalMapPanel != null) {
                    newTechnologicalMapPanel.initMaterials();
                }
                init();
                dialog.close();
            }
        });

        chooseButton.setClassName("chooseButton");
        chooseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Отменить", e -> dialog.close());

        Div buttonDiv = new Div(chooseButton, cancelButton);
        buttonDiv.setClassName("buttonDiv");
        buttonDiv.getStyle().set("display", "flex");

        Div gridDiv = new Div(goodsGridLayout);
        gridDiv.setClassName("gridDiv");
        gridDiv.setWidth("100%");
        gridDiv.setHeight("100%");
        dialogLayout = new VerticalLayout(divTollBar, gridDiv, buttonDiv);
    }


    public void clearSelectedProductDto() {
        selectedProductDto = null;
    }

//    public void SelectedProductDtoRemove(int index) {
//        selectedProductDto = null;
//    }

//    public void setSelectedProductFromTechMap(List<TechnologicalMapMaterialDto> materials) {
//        for (TechnologicalMapMaterialDto materialDto : materials) {
//            selectedProductDto.add(productService.getById(materialDto.getId()));
//        }
//    }
}
