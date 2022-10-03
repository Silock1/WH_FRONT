package com.warehouse_accounting.components.util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("silverButtons")
@CssImport(value = "./css/application.css")
public class SilverButton {


    public Button btnPLusBlue(String buttonText) {
        Image image = new Image("icons/plusBlue.jpg", "Plus");
        Button button = new Button(image);

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public Button btnBlank(String buttonText) {
        Button button = new Button();

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public Button btnPlusYellow(String buttonText) {
        Image image = new Image("icons/plusYellow.jpg", "Plus");
        Button button = new Button(image);

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public Button helpButton() {
        Button helpButton = new Button(new Image("icons/helpApp.svg", "help"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        return helpButton;
    }

    //TODO: Menu button


    public MenuBar menuBarButton() {

        MenuBar myBar = new MenuBar();


        Image image = new Image("icons/operationButton.png", "operation");
        HorizontalLayout imageCont = new HorizontalLayout();

        Icon caretDown = new Icon(VaadinIcon.CARET_DOWN);
        caretDown.setSize("13px");


        imageCont.add(image, caretDown);
        imageCont.setAlignItems(FlexComponent.Alignment.CENTER);

        myBar.addItem(imageCont);


        return myBar;
    }


    public MenuBar menuOperationButton(String textButton) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBar.addItem(menuVision(textButton));

        SubMenu accureSubMenu = operation.getSubMenu();

        accureSubMenu.addItem("Начислить");//.onEnabledStateChanged(false);
        accureSubMenu.addItem("Списать");//.onEnabledStateChanged(false);


        return menuBar;
    }

    public MenuBar menuEditButton(String textButton) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        MenuItem operation = menuBar.addItem(menuVision(textButton));

        SubMenu accureSubMenu = operation.getSubMenu();
        accureSubMenu.addItem("Удалить")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("Копировать")
                .getElement()
                .setAttribute("disabled", true);
        accureSubMenu.addItem("Массовое редактирование");

        return menuBar;
    }

    private HorizontalLayout menuVision(String textButton) {
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");
        Image image = new Image("icons/plusBlue.jpg", "Plus");


        HorizontalLayout menuVision = new HorizontalLayout(image, new Text(textButton), caretDownEdit);
        menuVision.setSpacing(false);
        return menuVision;

    }


    public Button refreshButton() {

        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return refreshButton;

    }


}
