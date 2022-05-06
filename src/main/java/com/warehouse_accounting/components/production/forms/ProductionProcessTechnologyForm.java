package com.warehouse_accounting.components.production.forms;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.production.ProductionProcessTechnology;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionProcessTechnologyService;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;


@UIScope
@Log4j2
public class ProductionProcessTechnologyForm extends VerticalLayout {
    private final ProductionProcessTechnology productionProcessTechnology;
    private final ProductionStageService productionStageService;
    private final EmployeeService employeeService;
    private final ProductionProcessTechnologyService productionProcessTechnologyService;
    private final Div returnDiv;
    private final VerticalLayout selectStage = new VerticalLayout();
    private final List<ProductionStageDto> productionStageDtoList;
    private Binder<ProductionProcessTechnologyDto> productionProcessTechnologyDtoBinder = new Binder<>(ProductionProcessTechnologyDto.class);
    private ProductionProcessTechnologyDto productionProcessTechnologyDto;


    public ProductionProcessTechnologyForm(
            ProductionProcessTechnology productionProcessTechnology,
            ProductionProcessTechnologyDto productionProcessTechnologyDto,
            ProductionStageService productionStageService,
            EmployeeService employeeService,
            ProductionProcessTechnologyService productionProcessTechnologyService
    ) {
        this.productionProcessTechnology = productionProcessTechnology;
        this.productionProcessTechnologyDto = productionProcessTechnologyDto;
        this.productionStageService = productionStageService;
        this.employeeService = employeeService;
        this.productionProcessTechnologyService = productionProcessTechnologyService;
        this.productionStageDtoList = productionStageService.getAll();
        this.productionStageDtoList.sort(Comparator.comparingLong(ProductionStageDto::getId));
        this.returnDiv = productionProcessTechnology.getMainContent();

        productionProcessTechnology.removeAll();
        add(createTopGroupElements(), createInputFieldForm());

    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        String showTextArchive = "Элементы, перемещенные в архив, не отображаются в справочниках и отчетах. Архив позволяет скрывать неактуальные элементы, не удаляя их.";
        topPartLayout.add(
                saveProductionTechnologyButton(),
                closeProductionTechnologyButton(),
                createEmptyDiv(),
                createHelpButton(showTextArchive),
                createMoveToArchiveButton(),
                createEditButton()
        );
        return topPartLayout;
    }

    private Button saveProductionTechnologyButton() {
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(c -> {
            if (!productionProcessTechnologyDtoBinder.validate().isOk()) {
                return;
            }

            if (selectStage.getComponentCount() < 1) {
                showErrorNotification("Техпроцесс должен включать хотя бы один этап!");
                return;
            }

            Set<Long> selectStageSet = selectStage.getChildren()
                    .map(x ->((Select<String>) x).getValue())
                    .filter(s -> !s.isEmpty())
                    .map(x -> productionStageDtoList.stream().filter(p -> p.getName().equals(x)).findFirst().get())
                    .map(ProductionStageDto::getId)
                    .collect(Collectors.toSet());

            if (selectStageSet.size() == 0) {
                showErrorNotification("Этап не может быть пустым!");
                return;
            }

            EmployeeDto employeeDto = employeeService.getById(1L);
            try {
                productionProcessTechnologyDtoBinder.writeBean(productionProcessTechnologyDto);
                productionProcessTechnologyDto.setUsedProductionStageId(selectStageSet);
                productionProcessTechnologyDto.setEditorEmployeeId(employeeDto.getId());
                productionProcessTechnologyDto.setOwnerEmployeeId(employeeDto.getId());
                productionProcessTechnologyDto.setOwnerDepartmentId(employeeDto.getDepartment().getId());
                productionProcessTechnologyDto.setDateOfEdit(null);

                if (productionProcessTechnologyDto.getId() != null && productionProcessTechnologyDto.getId() > 0) {
                    productionProcessTechnologyService.update(productionProcessTechnologyDto);
                } else {
                    System.out.println(productionProcessTechnologyDto);
                    productionProcessTechnologyService.create(productionProcessTechnologyDto);
                }
                productionProcessTechnology.getProductionProcessTechnologyGridLayout().updateGrid();
                productionProcessTechnology.removeAll();
                productionProcessTechnology.add(returnDiv);

            } catch (ValidationException e) {
                log.error("Ошибка валидации при создании нового этапа", e);
            }
        });
        return saveButton;
    }

    private Button closeProductionTechnologyButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> {
            productionProcessTechnology.removeAll();
            productionProcessTechnology.add(returnDiv);
        });
        return closeButton;

    }

    private HorizontalLayout createEditButton(){
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        boolean visibleItemDelete = true;
        if (productionProcessTechnologyDto.getId() != null && productionProcessTechnologyDto.getId() > 1) {
            visibleItemDelete = false;
        }
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            productionProcessTechnologyService.delete(productionProcessTechnologyDto.getId());
            productionProcessTechnology.getProductionProcessTechnologyGridLayout().updateGrid();
            productionProcessTechnology.removeAll();
            productionProcessTechnology.add(returnDiv);
        }).getElement().setAttribute("disabled", visibleItemDelete);
        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private Button createHelpButton(String showText) {
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);

        Notification helpNotification = new Notification();
        helpNotification.setPosition(Notification.Position.TOP_START);

        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> helpNotification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(new Text(showText),closeBtn);
        layout.setMaxWidth("350px");

        helpNotification.add(layout);

        helpButton.addClickListener(c -> {
            if (helpNotification.isOpened()) {
                helpNotification.close();
            } else {
                helpNotification.open();
            };
        });

        return helpButton;
    }

    private Button createMoveToArchiveButton() {
        Button archiveButton = new Button("Поместить в архив", e -> {

        });
        archiveButton.setEnabled(false);
        return archiveButton;
    }

    private Div createEmptyDiv() {
        Div emptyDiv = new Div();
        emptyDiv.setWidth("200px");
        return emptyDiv;
    }


    private VerticalLayout createInputFieldForm() {


        TextField nameField = new TextField();
        nameField.setLabel("Наименование");
        nameField.setMinWidth("300px");
        productionProcessTechnologyDtoBinder
                .forField(nameField)
                .asRequired("Наименование не должно быть пустым!")
                .bind(ProductionProcessTechnologyDto::getName, ProductionProcessTechnologyDto::setName);
        nameField.setValue(productionProcessTechnologyDto.getName() == null ? "" : productionProcessTechnologyDto.getName());

        TextArea descriptionField = new TextArea();
        descriptionField.setMinWidth("300px");
        descriptionField.setMinHeight("100px");
        descriptionField.setMaxHeight("150px");
        descriptionField.setLabel("Описание");
        productionProcessTechnologyDtoBinder
                .forField(descriptionField)
                .bind(ProductionProcessTechnologyDto::getDescription, ProductionProcessTechnologyDto::setDescription);
        descriptionField.setValue(productionProcessTechnologyDto.getDescription() == null ? "" : productionProcessTechnologyDto.getDescription());

        HorizontalLayout stageProduction = new HorizontalLayout();
        stageProduction.setAlignItems(Alignment.CENTER);
        String showTextStage = "Техпроцессы составляются из этапов. Одни и те же этапы могут быть включены в разные техпроцессы. Новые этапы можно создать в разделе Производство → Этапы.";
        stageProduction.add(createHelpButton(showTextStage), new Text("Этапы производства"));

        HorizontalLayout buttonStageGroup = new HorizontalLayout();
        Button addStageButton = new Button("Добавить этап");
        addStageButton.addClickListener(c -> {
            Select<String> select = new Select<>();
            select.setMinWidth("300px");
            select.setItems(productionStageDtoList.stream().map(ProductionStageDto::getName));
            select.setValue("");
            selectStage.add(select);
        });

        Button delStageButton = new Button("Удалить этап");
        delStageButton.addClickListener(c -> {
            if(selectStage.getComponentCount()-1 > -1) {
                selectStage.remove(selectStage.getComponentAt(selectStage.getComponentCount()-1));
            }
        });
        buttonStageGroup.add(addStageButton, delStageButton);

        if (productionProcessTechnologyDto.getUsedProductionStageId() != null) {
            productionProcessTechnologyDto.getUsedProductionStageId().forEach(id -> {
                Select<String> select = new Select<>();
                select.setMinWidth("300px");
                select.setItems(productionStageDtoList.stream().map(ProductionStageDto::getName));
                productionStageDtoList.forEach(p -> {
                    if (p.getId().equals(id)) {
                        select.setValue(p.getName());
                    }
                });
                selectStage.add(select);
            });
        }


        VerticalLayout returnLayout = new VerticalLayout();
        returnLayout.setAlignItems(Alignment.START);
        returnLayout.add(new Text("Техпроцесс"), nameField, descriptionField, createEmptyDiv(), stageProduction, new Text("Этап"), selectStage, buttonStageGroup);
        return returnLayout;
    }

    private void showErrorNotification(String showText) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Text text = new Text(showText);
        layout.add(icon, text,closeBtn);
        layout.setMaxWidth("350px");

        notification.add(layout);
        notification.open();

    }


}

