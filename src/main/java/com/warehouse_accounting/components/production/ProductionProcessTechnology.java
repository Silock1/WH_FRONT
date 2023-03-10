package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.forms.ProductionProcessTechnologyForm;
import com.warehouse_accounting.components.production.grids.ProductionProcessTechnologyGridLayout;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionProcessTechnologyService;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.Getter;
import lombok.Setter;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@SpringComponent
@UIScope
public class ProductionProcessTechnology extends VerticalLayout {
    @Getter
    private final ProductionProcessTechnologyGridLayout productionProcessTechnologyGridLayout;
    private final ProductionStageService productionStageService;
    private final EmployeeService employeeService;
    private final ProductionProcessTechnologyService productionProcessTechnologyService;
    @Getter
    @Setter
    private Div mainContent;

    public ProductionProcessTechnology(
            ProductionProcessTechnologyGridLayout productionProcessTechnologyGridLayout,
            ProductionStageService productionStageService,
            EmployeeService employeeService,
            ProductionProcessTechnologyService productionProcessTechnologyService
    ) {
        this.productionProcessTechnologyGridLayout = productionProcessTechnologyGridLayout;
        this.productionStageService = productionStageService;
        this.employeeService = employeeService;
        this.productionProcessTechnologyService = productionProcessTechnologyService;

        this.mainContent = new Div();
        mainContent.setSizeFull();
        mainContent.add(createTopGroupElements(), mainContent());
        add(mainContent);
    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);

        Button helpButton = createHelpButton();
        Text processText = new Text("??????????????????????");
        Button refreshButton = createRefreshButton();
        Button addProductionTechnologyButton = createAddProductionTechnologyButton();
        Button filterButton = createFilterButton();
        TextField inputTextFilter = createTextFilter();
        MenuBar menuBar = createMenuBar();

        topPartLayout.add(helpButton, processText, refreshButton, addProductionTechnologyButton, filterButton,inputTextFilter, menuBar);

        return topPartLayout;
    }

    private Button createHelpButton() {
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);

        String showText = "?????????????????????????????? ?????????????? ??? ???????????????????????????????????? ????????????, ?????????????? ???????????????????? ?????????????????? ?????? ?????????????????? ?????????????? ??????????????????.";

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

    private Button createRefreshButton() {
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(c -> {
            productionProcessTechnologyGridLayout.updateGrid();
        });
        return refreshButton;
    }

    private Button createAddProductionTechnologyButton() {
        Image buttonIcon = new Image("icons/plus.png", "Plus");
        buttonIcon.setWidth("14px");
        Button addProductionTechnologyButton = new Button("????????????????????", buttonIcon);

        addProductionTechnologyButton.addClickListener(c -> {
            add(new ProductionProcessTechnologyForm(this, new ProductionProcessTechnologyDto(), productionStageService, employeeService, productionProcessTechnologyService));
        });

        return addProductionTechnologyButton;
    }

    private Button createFilterButton() {
        Button filterButton = new Button("????????????");
        filterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filterButton.addClickListener(c -> {

        });

        return filterButton;
    }

    private TextField createTextFilter() {
        TextField textField = new TextField();
        textField.setPlaceholder("?????????????????????? ?????? ????????????????");
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

        menuBar.addItem(count); // ?????????????? ???????? ?? ??????-??????

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("????????????????"), caretDownEdit);
        editVision.setSpacing(false);
        editVision.setEnabled(false);


        MenuItem edit = menuBar.addItem(editVision);
        edit.onEnabledStateChanged(false);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("??????????????").onEnabledStateChanged(false);
        editSubMenu.addItem("????????????????????").onEnabledStateChanged(false);
        editSubMenu.addItem("???????????????? ????????????????????????????");
        editSubMenu.addItem("?????????????????? ?? ??????????").onEnabledStateChanged(false);
        editSubMenu.addItem("?????????????? ???? ????????????").onEnabledStateChanged(false);

        return menuBar;
    }

    private HorizontalLayout mainContent() {
        productionProcessTechnologyGridLayout.getProductionProcessTechnologyDtoGrid().addItemClickListener(event -> {
            add(new ProductionProcessTechnologyForm(this, event.getItem(), productionStageService, employeeService, productionProcessTechnologyService));
        });
        return productionProcessTechnologyGridLayout;
    }




}
