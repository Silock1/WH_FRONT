package com.warehouse_accounting.components.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;

@Route("silverButtons")
@CssImport(value = "./css/application.css")
public class SilverButton {


    public Button buttonPLusBlue(String buttonText) {
        Image image = new Image("icons/plusBlue.jpg", "Plus");
        Button button = new Button(image);

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public Button buttonBlank(String buttonText) {
        Button button = new Button();

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public void greenNotification(String textMessage) {
        Notification notification = Notification
                .show(textMessage);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public Button buttonPlusYellow(String buttonText) {
        Image image = new Image("icons/plusYellow.jpg", "Plus");
        Button button = new Button(image);

        button.setText(buttonText);
        button.setClassName("silverButton");
        return button;

    }

    public Button buttonHelp() {
        Button helpButton = new Button(new Image("icons/helpApp.svg", "help"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        return helpButton;
    }

    public Button buttonSetting() {
        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        settingButton.setClassName("settingButton");
        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return settingButton;
    }


    public Button buttonRefresh() {

        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return refreshButton;

    }

    public Button greenButton(String text) {
        Button button = new Button(text);
        button.setClassName("greenButton");
        return button;
    }

    //TODO: MenuBars


}
