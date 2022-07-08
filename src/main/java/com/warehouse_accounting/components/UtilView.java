package com.warehouse_accounting.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

//Утилитный класс для методов, которые создают элементы страницы (таблицы, табы, окна и т.д.)

@UtilityClass
public class UtilView {

// Метод  для создания набора табов из переданного листа.
//
    public static Tabs subMenuTabs(List<String> menuItems) {
        Tabs subMenuTabs = new Tabs();
        for (String item : menuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }
        return subMenuTabs;
    }

    public static Button plusButton(String caption) {
        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        return new Button(caption, image);
    }

    public static HorizontalLayout caretDownButtonLayout(String caption) {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        HorizontalLayout editItem = new HorizontalLayout(new Text(caption), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(FlexComponent.Alignment.CENTER);
        return editItem;
    }

    public static Button createHelpButton(String showText) {
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
            }
        });

        return helpButton;
    }
}
