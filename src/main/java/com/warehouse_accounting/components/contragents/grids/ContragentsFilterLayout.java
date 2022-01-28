package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ContragentsFilterLayout extends VerticalLayout {
    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingButton = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));

    public ContragentsFilterLayout() {

        HorizontalLayout horizontalLayout_one = new HorizontalLayout(find, clear, settingButton, bookmarks);

        add(horizontalLayout_one);


    }

}
