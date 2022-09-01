package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.TechnologicalMap;
import com.warehouse_accounting.components.production.dialog.DialogLayoutPrint;
import com.warehouse_accounting.components.production.dialog.NewGoodsDialog;
import com.warehouse_accounting.components.production.grids.NewTechnologicalMapGridLayout;
import com.warehouse_accounting.components.production.notification.TechnologicalMapNotification;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;
import com.warehouse_accounting.models.dto.TechnologicalMapMaterialDto;
import com.warehouse_accounting.services.impl.ProductionProcessTechnologyServiceImpl;
import com.warehouse_accounting.services.impl.ProductionStageServiceImpl;
import com.warehouse_accounting.services.impl.TechnologicalMapGroupServiceImpl;
import com.warehouse_accounting.services.impl.TechnologicalMapMaterialsServiceImpl;
import com.warehouse_accounting.services.impl.TechnologicalMapServiceImpl;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@SpringComponent
@UIScope
@Tag("div")
public class NewTechnologicalMapPanel extends HorizontalLayout {

    private final ProductionProcessTechnologyServiceImpl productionProcessTechnologyService;
    private final TechnologicalMapServiceImpl technologicalMapService;
    private final TechnologicalMapGroupServiceImpl technologicalMapGroupService;
    private final ProductionStageServiceImpl productionStageService;
    private final ProductionProcessTechnologyServiceImpl processTechnologyService;
    private final NewGoodsDialog newGoodsDialog;
    private final NewTechnologicalMapGridLayout newTechnologicalMapGridLayout;
    private final TechnologicalMapNotification notifications;
    private final DialogLayoutPrint dialogLayoutPrint;
    private final TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService;
    private Grid<TechnologicalMapMaterialDto> mapMaterialDtoGrid;
    private HorizontalLayout horizontalToolPanelLayout;
    private Tab details;
    private Tab materials;
    private Tab money;
    private VerticalLayout content;
    private List<ProductionProcessTechnologyDto> productionProcessTechnologyDtoList;
    private List<TechnologicalMapDto> technologicalMapDtoList;
    private List<TechnologicalMapGroupDto> technologicalMapGroupDtoList;
    private List<ProductDto> productDtoList;
    private List<TechnologicalMapMaterialDto> mapMaterialDtos;
    private TechnologicalMapDto technologicalMapDto;
    private MenuItem delete;
    private IntegerField integerField;
    private Select<TechnologicalMapGroupDto> groupTechnologicalMap;
    private Select<String> productionProcessField;
    private TextField nameField;
    private TextField commentField;

    public NewTechnologicalMapPanel(ProductionProcessTechnologyServiceImpl productionProcessTechnologyService, TechnologicalMapServiceImpl technologicalMapService, TechnologicalMapGroupServiceImpl technologicalMapGroupService, ProductionStageServiceImpl productionStageService, ProductionProcessTechnologyServiceImpl processTechnologyService, NewGoodsDialog newGoodsDialog, NewTechnologicalMapGridLayout newTechnologicalMapGridLayout, TechnologicalMapNotification notifications, DialogLayoutPrint dialogLayoutPrint, TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService) {
        this.productionProcessTechnologyService = productionProcessTechnologyService;
        this.technologicalMapService = technologicalMapService;
        this.technologicalMapGroupService = technologicalMapGroupService;
        this.productionStageService = productionStageService;
        this.processTechnologyService = processTechnologyService;
        this.newGoodsDialog = newGoodsDialog;
        this.newTechnologicalMapGridLayout = newTechnologicalMapGridLayout;
        this.notifications = notifications;
        this.dialogLayoutPrint = dialogLayoutPrint;
        this.technologicalMapMaterialsService = technologicalMapMaterialsService;
        productDtoList = new ArrayList<>();
        mapMaterialDtos = new ArrayList<>();
        mapMaterialDtoGrid = new Grid<>(TechnologicalMapMaterialDto.class, false);
    }

    public void getLayout(TechnologicalMap technologicalMap) {
        removeAll();
        configToolPanel(technologicalMap);
        createDateLine();
    }

    public void getLayout(TechnologicalMap technologicalMap, TechnologicalMapDto technologicalMapDto) {
        removeAll();
        this.technologicalMapDto = technologicalMapDto;
        getLayout(technologicalMap);
        // Временно убрано initMaterialsGrid();
    }

    private void createDateLine() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
            helpButton.setEnabled(false);
            Notification notification = notifications.show();
            notification.addDetachListener(detachEvent -> helpButton.setEnabled(true));
        });
        H2 headline = new H2("Технологическая карта");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        nameField = new TextField();
        nameField.setLabel("Наименование");
        nameField.setRequired(true);
        nameField.setRequiredIndicatorVisible(true);
        nameField.setErrorMessage("Наименование не может быть пустым");

        productionProcessField = new Select<>();
        productionProcessTechnologyDtoList = productionProcessTechnologyService.getAll();
        //todo Техпроцесс не реализовано
        productionProcessField.setLabel("Техпроцесс");
        productionProcessField.setItems(productionProcessTechnologyDtoList.stream().map(ProductionProcessTechnologyDto::getName).collect(Collectors.toList()));
        productionProcessField.setWidth("100");
        //  productionProcessField.addValueChangeListener(selectStringComponentValueChangeEvent -> initMenu(selectStringComponentValueChangeEvent.getValue()));
        productionProcessField.setEnabled(false);
        technologicalMapGroupDtoList = technologicalMapGroupService.getAll();
        groupTechnologicalMap = new Select<>();
        groupTechnologicalMap.setLabel("Группа");
        groupTechnologicalMap.setItems(technologicalMapGroupDtoList);
        groupTechnologicalMap.addValueChangeListener(selectTechnologicalMapGroupDtoComponentValueChangeEvent -> {
            selectTechnologicalMapGroupDtoComponentValueChangeEvent.getValue().getId();
        });
        groupTechnologicalMap.setValue(technologicalMapGroupDtoList.get(0));
        commentField = new TextField();
        commentField.setLabel("Комментарий");

        integerField = new IntegerField();
        integerField.setLabel("Затраты");
        integerField.setMin(0);
        integerField.setValue(0);
        integerField.setHasControls(true);
        Label mat = new Label();
        mat.setText("Материалы");
        Button addMaterial = new Button("Добавить из справочника", buttonClickEvent -> {
            newTechnologicalMapGridLayout.open(this);

        });

        if (technologicalMapDto != null) {
            initField(nameField, productionProcessField, groupTechnologicalMap);
        }
        Div div = new Div(helpButton, headline);
        div.setClassName("technoH2");
        div.getStyle().set("display", "flex");
        add(div);
        div = new Div(new Div(nameField),
                new Div(productionProcessField),
                new Div(groupTechnologicalMap),
                new Div(commentField),
                new Div(integerField),
                new Div(mat),
                new Div(addMaterial));
        add(div);
        // initMaterialLayout();
    }

    public void initMaterials() {
        //  initMaterialsGrid();
    }

    private void initMaterialsGrid() {
        remove(mapMaterialDtoGrid);
        mapMaterialDtoGrid = new Grid<>(TechnologicalMapMaterialDto.class, false);
        mapMaterialDtos = new ArrayList<>();
        if (newTechnologicalMapGridLayout.getSelectedProductDto() != null && newTechnologicalMapGridLayout.getSelectedProductDto().size() > 0) {
            productDtoList = newTechnologicalMapGridLayout.getSelectedProductDto();
            for (ProductDto p : productDtoList) {
                //TODO (Что делать с ID? добавить отдельную Модель?)добавлять в бд материалы без привязка к тех карте,а при сохранении тех карты добавлять привязку. ТАкже при удалении с грида удалять из бд
                //TODO необходимо сохранять материал, но пока это невозможно из-зи уникальности поля materialID
                TechnologicalMapMaterialDto t = TechnologicalMapMaterialDto.builder().id(1L).materialId(p.getId()).materialName(p.getName()).count(BigDecimal.ONE).build();
                mapMaterialDtos.add(t);
            }
        } else {
            mapMaterialDtos = technologicalMapDto.getMaterials();
        }

        Grid.Column<TechnologicalMapMaterialDto> idColumn = mapMaterialDtoGrid
                .addColumn(TechnologicalMapMaterialDto::getId).setHeader("Id");
        Grid.Column<TechnologicalMapMaterialDto> nameColumn = mapMaterialDtoGrid
                .addColumn(TechnologicalMapMaterialDto::getMaterialId).setHeader("Код материала");
        Grid.Column<TechnologicalMapMaterialDto> expensesColumn = mapMaterialDtoGrid
                .addColumn(TechnologicalMapMaterialDto::getMaterialName).setHeader("Наименование материала");


        Grid.Column<TechnologicalMapMaterialDto> testColumn = mapMaterialDtoGrid.addComponentColumn(mapMaterialDto -> {
            IntegerField integerField = new IntegerField();
            integerField.setMin(1);
            integerField.setValue(mapMaterialDto.getCount().intValue());
            integerField.setHelperText("шт.");
            integerField.setHasControls(true);
            return integerField;
        }).setHeader("Кол-во").setWidth("150px").setFlexGrow(0);


        Grid.Column<TechnologicalMapMaterialDto> editColumn = mapMaterialDtoGrid.addComponentColumn(mapMaterialDtoGridd -> {
            Button editButton = new Button(new Icon(VaadinIcon.CLIPBOARD_CROSS));
            editButton.addClickListener(e -> {
                mapMaterialDtos.remove(mapMaterialDtoGridd);
                initMaterialsGrid();
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);
        mapMaterialDtoGrid.setItems(mapMaterialDtos);
        add(mapMaterialDtoGrid);
    }

    private void initField(TextField nameField, Select<String> productionProcessField, Select<TechnologicalMapGroupDto> groupTechnologicalMap) {
        nameField.setValue(technologicalMapDto.getName());
        groupTechnologicalMap.setValue(technologicalMapGroupService.getById(technologicalMapDto.getTechnologicalMapGroupId()));
        commentField.setValue(technologicalMapDto.getComment());
        integerField.setValue(technologicalMapDto.getProductionCost().intValue());
        delete.setEnabled(true);
    }

    //  private void initMaterialLayout() {
    //      content.removeAll();
    // newTechnologicalMapGridLayout.init();
    //  content.add(newTechnologicalMapGridLayout);

    //  }
//    private void initMenu(String processTechnology) {
//        removeClassNames("productionProcess");
//        details = new Tab("Продукция");
//        materials = new Tab("Материалы");
//        money = new Tab("Деньги");
//        Tabs tabs = new Tabs(details, materials, money);
//        tabs.addSelectedChangeListener(event ->
//                setContent(event.getSelectedTab(), processTechnology)
//        );
//        content = new VerticalLayout();
//        content.setSpacing(false);
//        setContent(tabs.getSelectedTab(), processTechnology);
//        Div div = new Div(tabs, content);
//        div.setClassName("productionProcess");
//        add(div);
//    }

    private void setContent(Tab tab, String processTechnology) {
        content.removeAll();

        if (tab.equals(details)) {

            content.add(new Paragraph("Заглушка"));
        } else if (tab.equals(materials)) {
            newTechnologicalMapGridLayout.init();
            content.add(newTechnologicalMapGridLayout);
        } else {
            content.add(new Paragraph("This is the Shipping tab"));
        }
    }

    private void configToolPanel(TechnologicalMap technologicalMap) {
        Button save = new Button("Сохранить", buttonClickEvent -> {
            //checkRequired();
//TODO проблемы с сохранением map с материалами, либо расенхронизация генерации id  см строка 196
            if (technologicalMapDto != null && technologicalMapDto.getId() != null) {
                technologicalMapDto.setName(nameField.getValue());
                technologicalMapDto.setTechnologicalMapGroupId(groupTechnologicalMap.getValue().getId());
                technologicalMapDto.setTechnologicalMapGroupName(groupTechnologicalMap.getValue().getName());
                technologicalMapDto.setComment(commentField.getValue());
                technologicalMapDto.setProductionCost(BigDecimal.valueOf(integerField.getValue()));
                technologicalMapService.update(technologicalMapDto);
            } else {
                technologicalMapService.create(TechnologicalMapDto.builder()
                        .name(nameField.getValue())
                        .comment(commentField.getValue())
                        .productionCost(BigDecimal.valueOf(integerField.getValue()))
                        .technologicalMapGroupId(groupTechnologicalMap.getValue().getId())
                        .technologicalMapGroupName(groupTechnologicalMap.getValue().getName())
                        .build());
            }
            //TODO SAVE
            removeAll();
            newTechnologicalMapGridLayout.clearSelectedProductDto();
            technologicalMapDto = null;
            technologicalMap.init();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button close = new Button("Закрыть", buttonClickEvent -> {
            removeAll();
            newTechnologicalMapGridLayout.clearSelectedProductDto();
            technologicalMapDto = null;
            technologicalMap.init();
        });
        close.getStyle().set("margin-inline-end", "auto");

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
            helpButton.setEnabled(false);
            Notification notification = notifications.showArchive();
            notification.addDetachListener(detachEvent -> helpButton.setEnabled(true));
        });

        Button archiveButton = new Button("Поместить в архив");
        archiveButton.getElement().setAttribute("disabled", true);//TODO archive

        MenuBar menuBar = new MenuBar();
        menuBar.setClassName("MenuBar");
        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        delete = changeSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {
            //TODO удалить
            newTechnologicalMapGridLayout.clearSelectedProductDto();
            technologicalMapService.deleteById(technologicalMapDto.getId());
            removeAll();
            technologicalMapDto = null;
            technologicalMap.init();
        });
        delete.getElement().setAttribute("disabled", true);
        MenuItem recover = changeSubMenu.addItem("Копировать");
        recover.addClickListener(event -> {
            //TODO  копировать
        });
        recover.getElement().setAttribute("disable", true);
        MenuItem createDocument = menuBar.addItem("Создать документ");
        createDocument.add(new Icon(VaadinIcon.CARET_DOWN));
        SubMenu createSubMenu = createDocument.getSubMenu();
        MenuItem productionOperation = createSubMenu.addItem("Технологическая операция");
        productionOperation.addClickListener(event -> {
            //TODO Технологическая операция
        });
        MenuItem productionOrders = createSubMenu.addItem("Заказ на производство");
        productionOrders.addClickListener(event -> {
            //TODO Заказ на производство
        });
        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        //модальное окно

        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = dialogLayoutPrint.createDialogLayout(dialog);
        dialog.add(dialogLayout);
        //
        SubMenu printSubMenu = print.getSubMenu();
        MenuItem technologyMap = printSubMenu.addItem("Технологическая карта");
        technologyMap.addClickListener(event -> {
            dialog.open(); //
            //TODO повод поработать этот функционал
        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        //TODO owner?

        //  Button owner = new Button("Owner",new Icon(VaadinIcon.CARET_DOWN));
        //   owner.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        //  owner.getElement().setText();
        Div div = new Div(save, close, helpButton, archiveButton, menuBar);
        div.setClassName("toolPanel");
        div.getStyle().set("display", "flex");
        div.setSizeFull();
        add(div);
    }

    private void checkRequired() {

    }


}
