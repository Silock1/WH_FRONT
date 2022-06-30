package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.production.forms.ProductionOperationsForm;
import com.warehouse_accounting.components.production.grids.ProductionOperationsGridLayout;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@SpringComponent
@UIScope
@Log4j2
@Route (value = "ProductionOperations", layout = AppView.class)
public class ProductionOperations extends VerticalLayout {
    private ProductionOperationsService productionOperationsService;
    private TextField textField;

    @Getter
    private final ProductionOperationsGridLayout productionOperationsGridLayout;

    @Getter
    @Setter
    private Div pageContent;

    public ProductionOperations(ProductionOperationsService productionOperationsService, ProductionOperationsGridLayout productionOperationsGridLayout) {
        this.productionOperationsGridLayout = productionOperationsGridLayout;
        this.productionOperationsService = productionOperationsService;
        this.pageContent = new Div();
        pageContent.setSizeFull();
        pageContent.add(createTopGroupElements(), mainContent());
    }

    private HorizontalLayout createTopGroupElements () {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        Button helpButton = createHelpButton();
        Text processText = new Text("Технологические операции");
        Button refreshButton = createRefreshButton();
        Button createOperationButton = createNewOperation();
        Button filterButton = createFilterButton();
        TextField inputTextFilter = createTextFilter();
        MenuBar menuBar = createMenuBar();

        topPartLayout.add(helpButton, processText, refreshButton, createOperationButton, filterButton,inputTextFilter, menuBar);

        return topPartLayout;
    }


    private Button createHelpButton() {
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);

        String showText = "Технологические операции позволяют регистрировать в системе операции по сборке и производству. " +
                "В результате списываются материалы и добавляются готовые изделия.";

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

    private Button createNewOperation () {
        Image buttonIcon = new Image("icons/plus.png", "Plus");
        buttonIcon.setWidth("14px");
        Button addProductionOperationButton = new Button("Операция", buttonIcon);
        addProductionOperationButton.addClickListener(buttonClickEvent -> {
            add(new ProductionOperationsForm(this, new ProductionOperationsDto(), productionOperationsService));
        });
        return addProductionOperationButton;
    }

    private Button createRefreshButton() {
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return refreshButton;

        //дописать refresh через update в гриде
    }

    private Button createFilterButton() {
        Button filterButton = new Button("Фильтр");
        filterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filterButton.addClickListener(c -> {

        });

        return filterButton;
    }

    private TextField createTextFilter() {
        TextField textField = new TextField();
        textField.setPlaceholder("Наменование или описание");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");
        return textField;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        menuBar.addItem(count); // Добавим поле с кол-вом

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);
        editVision.setEnabled(false);


        MenuItem edit = menuBar.addItem(editVision);
        edit.onEnabledStateChanged(false);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Поместить в архив").onEnabledStateChanged(false);
        editSubMenu.addItem("Извлечь из архива").onEnabledStateChanged(false);

        return menuBar;
    }


    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER); //

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER); //
        return groupEdit;
    }
    private HorizontalLayout mainContent() {
        productionOperationsGridLayout.getProductionOperationsDtoGrid().addItemClickListener(productionOperationsDtoItemClickEvent -> {
            add(new ProductionOperationsForm(this, productionOperationsDtoItemClickEvent.getItem(), productionOperationsService));
        });
        return productionOperationsGridLayout;
    }
}
