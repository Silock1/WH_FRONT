package com.warehouse_accounting.components.production.forms;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.production.ProductionProcessTechnology;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;


@UIScope
public class ProductionProcessTechnologyForm extends VerticalLayout {
    private final ProductionProcessTechnology productionProcessTechnology;
    private final Div returnDiv;
    private Binder<ProductionProcessTechnologyDto> productionProcessTechnologyDtoBinder = new Binder<>(ProductionProcessTechnologyDto.class);


    public ProductionProcessTechnologyForm(ProductionProcessTechnology productionProcessTechnology) {
        this.productionProcessTechnology = productionProcessTechnology;
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
//        boolean visibleItemDelete = true;
//        if (productionStageDto.getId() != null && productionStageDto.getId() > 1) {
//            visibleItemDelete = false;
//        }
//        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
//            productionStageService.delete(productionStageDto.getId());
//            productionStepsGridLayout.updateGrid();
//            parentLayer.removeAll();
//            parentLayer.add(productionStepsGridLayout);
//        }).getElement().setAttribute("disabled", visibleItemDelete);
//        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {
//
//        });

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
        TextArea textArea = new TextArea();
        textArea.setMinHeight("100px");
        textArea.setMaxHeight("150px");
        textArea.setLabel("Описание");

        TextField nameField = new TextField();
        nameField.setLabel("Наименование");
        nameField.setMinWidth("300px");
        //productionStageDtoBinder.forField(nameField).asRequired().bind(ProductionStageDto::getName, ProductionStageDto::setName);
        //nameField.setValue(productionStageDto.getName() == null ? "" : productionStageDto.getName());

        TextArea descriptionField = new TextArea();
        descriptionField.setMinWidth("300px");
        descriptionField.setMinHeight("100px");
        descriptionField.setMaxHeight("150px");
        descriptionField.setLabel("Описание");
        //productionStageDtoBinder.forField(descriptionField).bind(ProductionStageDto::getDescription, ProductionStageDto::setDescription);
        //descriptionField.setValue(productionStageDto.getDescription() == null ? "" : productionStageDto.getDescription());

        HorizontalLayout stageProduction = new HorizontalLayout();
        stageProduction.setAlignItems(Alignment.CENTER);
        String showTextStage = "Техпроцессы составляются из этапов. Одни и те же этапы могут быть включены в разные техпроцессы. Новые этапы можно создать в разделе Производство → Этапы.";
        stageProduction.add(createHelpButton(showTextStage), new Text("Этапы производства"));

        VerticalLayout selectStage = new VerticalLayout();
        Button addStageButton = new Button("Добавить этап");
        addStageButton.addClickListener(c -> {
            Select<String> select = new Select<>();
            select.setItems("Most recent first", "Rating: high to low",
                    "Rating: low to high", "Price: high to low", "Price: low to high");
            select.setValue("Most recent first");
            selectStage.add(select);
        });

        VerticalLayout returnLayout = new VerticalLayout();
        returnLayout.add(new Text("Техпроцесс"), nameField, descriptionField, createEmptyDiv(), stageProduction, new Text("Этап"), selectStage, addStageButton);
        return returnLayout;
    }


}

