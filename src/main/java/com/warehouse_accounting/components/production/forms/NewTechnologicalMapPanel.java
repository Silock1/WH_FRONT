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
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@SpringComponent
@UIScope
@Tag("div")
public class NewTechnologicalMapPanel extends HorizontalLayout {

    private final DialogLayoutPrint dialogLayoutPrint;
    private final NewTechnologicalMapGridLayout newTechnologicalMapGridLayout;
    private final ProductionProcessTechnologyServiceImpl processTechnologyService;
    private final ProductionProcessTechnologyServiceImpl productionProcessTechnologyService;
    private final ProductionStageServiceImpl productionStageService;
    private final TechnologicalMapGroupServiceImpl technologicalMapGroupService;
    private final TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService;
    private final TechnologicalMapNotification notifications;
    private final TechnologicalMapServiceImpl technologicalMapService;
    private Grid<TechnologicalMapMaterialDto> mapMaterialDtoGrid;
    private List<Long> index;
    private List<ProductionProcessTechnologyDto> productionProcessTechnologyDtoList;
    private List<TechnologicalMapGroupDto> technologicalMapGroupDtoList;
    private Map<Long, TechnologicalMapMaterialDto> mapMaterialDtos;
    //   private List<TechnologicalMapMaterialDto> mapMaterialDtos;
    private ProductDto productDto;
    private TechnologicalMapDto technologicalMapDto;
    private Select<String> productionProcessField;
    private Select<TechnologicalMapGroupDto> groupTechnologicalMap;
    private MenuItem delete;
    private IntegerField integerField;
    private TextField commentField;
    private TextField nameField;
    private long n;

    public NewTechnologicalMapPanel(ProductionProcessTechnologyServiceImpl productionProcessTechnologyService, TechnologicalMapServiceImpl technologicalMapService, TechnologicalMapGroupServiceImpl technologicalMapGroupService, ProductionStageServiceImpl productionStageService, ProductionProcessTechnologyServiceImpl processTechnologyService, NewGoodsDialog newGoodsDialog, NewTechnologicalMapGridLayout newTechnologicalMapGridLayout, TechnologicalMapNotification notifications, DialogLayoutPrint dialogLayoutPrint, TechnologicalMapMaterialsServiceImpl technologicalMapMaterialsService) {
        this.productionProcessTechnologyService = productionProcessTechnologyService;
        this.technologicalMapService = technologicalMapService;
        this.technologicalMapGroupService = technologicalMapGroupService;
        this.productionStageService = productionStageService;
        this.processTechnologyService = processTechnologyService;
        this.newTechnologicalMapGridLayout = newTechnologicalMapGridLayout;
        this.notifications = notifications;
        this.dialogLayoutPrint = dialogLayoutPrint;
        this.technologicalMapMaterialsService = technologicalMapMaterialsService;
        productDto = null;
        mapMaterialDtos = new HashMap<>();//new ArrayList<>();
        mapMaterialDtoGrid = new Grid<>(TechnologicalMapMaterialDto.class, false);
        /*
        для присваивания индексов материалам в таблице, если не присваивать, то в таблице будут как один объект
         и поле добавления кол-ва будет одно на всех
        */
        n = 1L;
        //сохранение индексов материалов для удаления
        index = new ArrayList<>();
    }

    //Для новой тех. карты
    public void getLayout(TechnologicalMap technologicalMap) {
        removeAll();
        configToolPanel(technologicalMap);
        createDateLine();
    }

    //Для редактируемой тех. карты
    public void getLayout(TechnologicalMap technologicalMap, TechnologicalMapDto technologicalMapDto) {
        removeAll();
        this.technologicalMapDto = technologicalMapDto;
        getLayout(technologicalMap);
        initMaterialsGrid();
    }

    //Создание основных полей тех. карты
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
        // productionProcessField.addValueChangeListener(selectStringComponentValueChangeEvent -> initNavBar(selectStringComponentValueChangeEvent.getValue()));
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
        //TODO реализовать бары продукция, материалы и деньги как на сайте мой склад

        /*
         Добавление материалов на данный момент реализовано вне табов и не зависит от того, выбран-ли
         productionProcessField или нет (как на мой склад)
         Задумка такая: если тех карта новая, то по умолчанию выбирается основной тех. процесс productionProcessField,
         на productionProcessField стоит Listener, при смене значения должны появляться табы и, либо выбирается какой-то
         их них по умолчанию, либо они просто появляются и уже пользователь открывает тот, который нужен
         те самые табы https://vaadin.com/docs/v14/ds/components/tabs
         При выборе того или иного таба, должна отобразится соответствующая таблица (remove(контент) add(контент))
         В дальнейшем необходимо привязать материалы и затраты к этапам как на сайте(если это действительно необходимо)
         */
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
    }

    //инициализация материалов после выбора продукта
    public void initMaterials() {
        initMaterialsGrid();
    }

    //инициализация таблицы материалов
    private void initMaterialsGrid() {
        remove(mapMaterialDtoGrid);
        mapMaterialDtoGrid = new Grid<>(TechnologicalMapMaterialDto.class, false);
        if (technologicalMapDto != null && technologicalMapDto.getMaterials() != null) {
            mapMaterialDtos.putAll(technologicalMapDto.getMaterials()
                    .stream().collect(Collectors.toMap(TechnologicalMapMaterialDto::getId, technologicalMapMaterialDto -> technologicalMapMaterialDto)));

        }
        if (newTechnologicalMapGridLayout.getSelectedProductDto() != null) {
            productDto = newTechnologicalMapGridLayout.getSelectedProductDto();
            TechnologicalMapMaterialDto t = TechnologicalMapMaterialDto.builder()
                    .id(count())
                    .materialId(productDto.getId())
                    .materialName(productDto.getName())
                    .count(BigDecimal.ONE)
                    .build();
            mapMaterialDtos.put(t.getId(), t);
            newTechnologicalMapGridLayout.clearSelectedProductDto();
        }
        Grid.Column<TechnologicalMapMaterialDto> nameColumn = mapMaterialDtoGrid
                .addColumn(TechnologicalMapMaterialDto::getMaterialId).setHeader("Код материала");
        Grid.Column<TechnologicalMapMaterialDto> expensesColumn = mapMaterialDtoGrid
                .addColumn(TechnologicalMapMaterialDto::getMaterialName).setHeader("Наименование материала");

        Grid.Column<TechnologicalMapMaterialDto> quantityColumn = mapMaterialDtoGrid.addComponentColumn(mapMaterialDto -> {
            IntegerField integerField = new IntegerField();
            integerField.setMin(1);
            integerField.setValue(mapMaterialDto.getCount().intValue());
            integerField.setHelperText("шт.");
            integerField.setHasControls(true);
            integerField.addValueChangeListener(mapMaterial ->
            {
                mapMaterialDtos.get(mapMaterialDto.getId()).setCount(BigDecimal.valueOf(mapMaterial.getValue()));
            });
            return integerField;
        }).setHeader("Кол-во").setWidth("150px").setFlexGrow(0);

        Grid.Column<TechnologicalMapMaterialDto> editColumn = mapMaterialDtoGrid.addComponentColumn(mapMaterialDtoGridd -> {
            Button editButton = new Button(new Icon(VaadinIcon.CLOSE));
            editButton.addClickListener(e -> {

                mapMaterialDtos.remove(mapMaterialDtoGridd.getId(), mapMaterialDtoGridd);
                if (technologicalMapDto != null) {
                    technologicalMapDto.setMaterials(mapMaterialDtos.values().stream().collect(Collectors.toList()));
                }
                index.add(mapMaterialDtoGridd.getId());
                initMaterialsGrid();
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);
        mapMaterialDtoGrid.setItems(mapMaterialDtos.values());
        add(mapMaterialDtoGrid);
    }

    //инициализация полей для редактируемой тех. карты
    private void initField(TextField nameField, Select<String> productionProcessField, Select<TechnologicalMapGroupDto> groupTechnologicalMap) {
        nameField.setValue(technologicalMapDto.getName());
        groupTechnologicalMap.setValue(technologicalMapGroupService.getById(technologicalMapDto.getTechnologicalMapGroupId()));
        commentField.setValue(technologicalMapDto.getComment());
        integerField.setValue(technologicalMapDto.getProductionCost().intValue());
        delete.setEnabled(true);
    }

    //Верхние кнопки
    private void configToolPanel(TechnologicalMap technologicalMap) {
        Button save = new Button("Сохранить", buttonClickEvent -> {
            //TODO проверка обязательных полей
            //checkRequired();

 /*
Реализация бэка подводит к тому, чтбы солхранить тех карту,
а только потом материалы и соопуствующие объекты (TechnologicalMapProduct один из таких)
*/
            if (technologicalMapDto != null && technologicalMapDto.getId() != null) {
                update();
            } else {
                createNew();
            }
            reset(technologicalMap);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button close = new Button("Закрыть", buttonClickEvent -> {
            reset(technologicalMap);
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
            newTechnologicalMapGridLayout.clearSelectedProductDto();
            technologicalMapService.deleteById(technologicalMapDto.getId());
            reset(technologicalMap);
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

    private void update() {
        technologicalMapDto.setName(nameField.getValue());
        technologicalMapDto.setTechnologicalMapGroupId(groupTechnologicalMap.getValue().getId());
        technologicalMapDto.setTechnologicalMapGroupName(groupTechnologicalMap.getValue().getName());
        technologicalMapDto.setComment(commentField.getValue());
        technologicalMapDto.setProductionCost(BigDecimal.valueOf(integerField.getValue()));
        technologicalMapDto.setMaterials(null);
        if (index != null || index.size() != 0) {
            for (Long l : index) {
                technologicalMapMaterialsService.deleteById(l);
            }
        }
        technologicalMapService.update(technologicalMapDto);
        TechnologicalMapDto techMapDto = technologicalMapService.getById(technologicalMapDto.getId());
        for (TechnologicalMapMaterialDto dto : mapMaterialDtos.values()) {
            dto.setTechnologicalMapDto(techMapDto);
            technologicalMapMaterialsService.create(dto);
        }
    }

    private void createNew() {
        technologicalMapService.create(TechnologicalMapDto.builder()
                .name(nameField.getValue())
                .comment(commentField.getValue())
                .productionCost(BigDecimal.valueOf(integerField.getValue()))
                .technologicalMapGroupId(groupTechnologicalMap.getValue().getId())
                .technologicalMapGroupName(groupTechnologicalMap.getValue().getName())
                .isArchived(false)
                .isDeleted(false)
                .materials(null)
                .finishedProducts(null)
                .build());
        List<TechnologicalMapDto> temp = technologicalMapService.getAll();
        TechnologicalMapDto techMapDto = technologicalMapService.getById(temp.get(temp.size() - 1).getId());
        temp = null;
        for (TechnologicalMapMaterialDto dto : mapMaterialDtos.values()) {
            dto.setId(null);
            dto.setTechnologicalMapDto(techMapDto);
            technologicalMapMaterialsService.create(dto);
        }
    }

    private void checkRequired() {
        //TODO реализовать проверку заполнения обязательных полей
    }

    private long count() {
        int i = technologicalMapMaterialsService.getAll().size();
        long l = 0;
        if (i != 0) {
            l = technologicalMapMaterialsService.getAll().get(i - 1).getId() + n;
        }
        n++;
        return l;
    }

    private void reset(TechnologicalMap technologicalMap) {
        n = 1L;
        index = new ArrayList<>();
        removeAll();
        newTechnologicalMapGridLayout.clearSelectedProductDto();
        mapMaterialDtos.clear();
        technologicalMapDto = null;
        technologicalMap.init();
    }
}


