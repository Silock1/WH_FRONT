package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import com.warehouse_accounting.components.production.forms.ProductionOrdersForm;
import com.warehouse_accounting.components.production.grids.ProductionOrdersGridLayout;
import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.services.interfaces.ProductionOrderService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@SpringComponent
@UIScope
@Log4j2
@Route(value = "ProductionOrders", layout = AppView.class)
public class ProductionOrders extends VerticalLayout {
    private final ProductionOrderService productionOrderService;
    private final TextField textField = new TextField();
    private final HorizontalLayout formLayout;

    private final ProductionOrdersGridLayout productionOrdersGridLayout;
    @Getter
    @Setter
    private Div pageContent;

    public ProductionOrders(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
        this.formLayout = createNewLegalDetail();
        productionOrdersGridLayout = new ProductionOrdersGridLayout(productionOrderService, this);
        pageContent = new Div();
        pageContent.add(productionOrdersGridLayout);
        pageContent.setSizeFull();
        HorizontalLayout horizontalLayout = getGroupButton();
        add(horizontalLayout, pageContent);
    }

    private HorizontalLayout createNewLegalDetail() {
        HorizontalLayout firstLine = new HorizontalLayout();

        Button findLegalDetailButton = new Button("Найти");
        findLegalDetailButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button clearLegalDetailButton = new Button("Очистить");
        clearLegalDetailButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        firstLine.add(findLegalDetailButton, clearLegalDetailButton);

        TextField sortNumberLegalDetail = new TextField("Код");
        TextField innLegalDetail = new TextField("ИНН");
        TextField phoneLegalDetail = new TextField("Телефон");
        TextField emailLegalDetail = new TextField("E-mail");
        TextField addressLegalDetail = new TextField("Адрес");
        TextField showLegalDetail = new TextField("Показывать");
        TextField ownerEmployeeLegalDetail = new TextField("Владелец-сотрудник");
        TextField ownerDepartmentLegalDetail = new TextField("Владелец-отдел");
        TextField generalAccessLegalDetail = new TextField("Общий доступ");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                sortNumberLegalDetail, innLegalDetail,
                phoneLegalDetail, emailLegalDetail, addressLegalDetail,
                showLegalDetail, ownerEmployeeLegalDetail, ownerDepartmentLegalDetail, generalAccessLegalDetail
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));

        HorizontalLayout filterForm = new HorizontalLayout();
        filterForm.add(firstLine, formLayout);

        filterForm.setVisible(false);
        return filterForm;
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("" +
                        "Заказ на производство помогает планировать производственные\n" +
                        "операции и резервировать необходимые материалы,\n" +
                        "комплектующие и сырье. Это позволяет оценивать будущие\n" +
                        "потребности в сырье и материалах и своевременно пополнять\n" +
                        "производственные запасы.\n" +
                        "\n" +
                        "Читать инструкцию\n"
                , 5000, Notification.Position.TOP_START));

        Text textProductionOrders = new Text("Заказы на производство");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            log.info("Перезагрузка, обновленеие списка Orders");
            productionOrdersGridLayout.updateGrid();
        });

        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");

        Button addtextProductionOrdersButton = new Button("Заказ", new Icon(VaadinIcon.PLUS));
        addtextProductionOrdersButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addtextProductionOrdersButton.addClickListener(buttonClickEvent -> {
           add(new ProductionOrdersForm(this, new ProductionOrderDto(), productionOrderService));
        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(buttonClickEvent -> {
            if (formLayout.isVisible()) {
                formLayout.setVisible(false);
            } else {
                formLayout.setVisible(true);
            }
        });

        TextField searchField = new TextField();
        searchField.setPlaceholder("Наименование или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
        });


        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout setting = getSetting();

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"), printIcon, caretDownPrint);
        printVision.setSpacing(false);

        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список производственных заданий");
        printSubMenu.addItem("Производственное задание").onEnabledStateChanged(false);
        printSubMenu.addItem("Комплект").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить");

        horizontalLayout.add(helpButton, textProductionOrders, refreshButton,
                addtextProductionOrdersButton,
                addFilterButton, searchField, editMenuBar, setting);

        return horizontalLayout;

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

    private HorizontalLayout getSetting() {
        Icon cogIcon = new Icon(VaadinIcon.COG);
        Button settingButton = new Button(cogIcon);
        settingButton.addClickListener(buttonClickEvent -> {
        });
        HorizontalLayout setting = new HorizontalLayout();
        setting.add(settingButton);
        return setting;
    }

}

