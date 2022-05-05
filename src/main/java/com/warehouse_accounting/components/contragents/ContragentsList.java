package com.warehouse_accounting.components.contragents;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.form.FormEditCotragent;
import com.warehouse_accounting.components.contragents.form.FormNewContragent;
import com.warehouse_accounting.components.contragents.grids.ContragentsFilterLayout;
import com.warehouse_accounting.components.contragents.grids.ContragentsListGridLayout;
import com.warehouse_accounting.models.dto.ContractorDto;

/*
Контрагенты
 */
@SpringComponent
@UIScope
public class ContragentsList extends VerticalLayout {

    private ContragentsListGridLayout contragentsListGridLayout;
    private ContragentsFilterLayout contragentsFilterLayout;
    private FormNewContragent formNewContragent;
    private HorizontalLayout buttons;
    private FormEditCotragent formEditCotragent;

    public ContragentsList(ContragentsListGridLayout contragentsListGridLayout,
                           ContragentsFilterLayout contragentsFilterLayout,
                           FormNewContragent formNewContragent,
                           FormEditCotragent formEditCotragent ) {

        this.contragentsListGridLayout=contragentsListGridLayout;
        this.contragentsFilterLayout = contragentsFilterLayout;
        this.formNewContragent = formNewContragent;
        this.formEditCotragent = formEditCotragent;
        this.buttons = getGroupButtons();
        this.contragentsListGridLayout.setParent(this);
        this.formEditCotragent.setParent(this);
        this.formNewContragent.setContragentsList(this);
        add(buttons, contragentsFilterLayout, contragentsListGridLayout);
    }

    private HorizontalLayout getGroupButtons() {

        HorizontalLayout controlButton = new HorizontalLayout();

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(c-> Notification.show("В разделе представлен список ваших поставщиков и покупателей." +
                " Для них можно настраивать индивидуальные цены и скидки, также можно им звонить и отправлять " +
                "документы прямо из МоегоСклада.\n" +
                "Список контрагентов можно импортировать и экспортировать.\n" +
                "Читать инструкцию: Контрагенты\n"+
                "Видео: Контрагенты",4000,Notification.Position.TOP_START));

        Label textLabel = new Label("Контрагенты");
        textLabel.setWidth("150px");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(e-> {
            contragentsListGridLayout.refreshDate();
        });
        Button addContragent = new Button(("Контрагент"), new Icon(VaadinIcon.PLUS));
        addContragent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addContragent.addClickListener(e->{
            hideButtonEndGrid();
            formEditCotragent.bild(null);
            add(formEditCotragent);
        });

        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_SMALL);
        filter.addClickListener(e->{
            contragentsFilterLayout.setVisible(!contragentsFilterLayout.isVisible());
        });

        TextField textField = new TextField();
        textField.setPlaceholder("Наим, тел, email, событие, коммент");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        Button mailingLists = new Button("Рассылки");
        Button importButton = new Button("Импорт");
        Button exportButton = new Button("Экспорт");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);
        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        menuBar.addItem(count);

        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);
        MenuItem edit = menuBar.addItem(editVision);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Поместить в архив").onEnabledStateChanged(false);
        editSubMenu.addItem("Извлечь из архива").onEnabledStateChanged(false);


        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);
        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Новый");
        statusSubMenu.addItem("Выслано предложение");
        statusSubMenu.addItem("Переговоры");
        statusSubMenu.addItem("Сделка заключена");
        statusSubMenu.addItem("Сделка не заключена");
        statusSubMenu.addItem("Настроить...");

        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"),caretDownPrint);
        printVision.setSpacing(false);
        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список контрагентов");
        printSubMenu.addItem("Настроить");

        controlButton.add(helpButton, textLabel, refreshButton, addContragent, filter, textField,
                menuBar, mailingLists, importButton, exportButton);
        setSizeFull();
        return controlButton;
    }
    public void showButtonEndGrid(Boolean refreshGrid){
        buttons.setVisible(true);
        if(refreshGrid)contragentsListGridLayout.refreshDate();
        contragentsListGridLayout.setVisible(true);
    }
    public void hideButtonEndGrid(){
        buttons.setVisible(false);
        contragentsListGridLayout.setVisible(false);
        contragentsFilterLayout.setVisible(false);
    }
    public void editFormActivate(ContractorDto contractorDto){
        formEditCotragent.bild(contractorDto);
        hideButtonEndGrid();
        add(formEditCotragent);
    }
}
