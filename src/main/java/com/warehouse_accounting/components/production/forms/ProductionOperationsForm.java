package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@UIScope
@Log4j2
public class ProductionOperationsForm extends VerticalLayout {

    private ProductionOperations productionOperations;

    private final Div returnDiv;

    private Binder <ProductionOperationsDto> productionOperationsDtoBinder = new Binder<>(ProductionOperationsDto.class);
    private ProductionOperationsDto productionOperationsDto;
    private ProductionOperationsService productionOperationsService;

    public ProductionOperationsForm(ProductionOperations productionOperations,ProductionOperationsDto productionOperationsDto,
                                    ProductionOperationsService productionOperationsService) {
        this.productionOperationsDto = productionOperationsDto;
        this.productionOperationsService = productionOperationsService;
        this.productionOperations = productionOperations;
        this.returnDiv = productionOperations.getPageContent();
    }


    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setAlignItems(Alignment.CENTER);

        return topLayout;
    }

    private Button saveProductionOperationsButton () {
        Button saveButton = new Button();
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(event -> {
           if (productionOperationsDtoBinder.validate().isOk()) {
               return;
           }
        });
        return saveButton;
    }

    private Button closeProductionTechnologyButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> {
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
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

        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            productionOperationsService.getById(productionOperationsDto.getId());
            productionOperations.getProductionOperationsGridLayout().updateGrid();
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
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
