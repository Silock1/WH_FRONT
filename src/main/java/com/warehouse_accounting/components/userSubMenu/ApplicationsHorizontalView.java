package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.ApplicationDto;

import java.util.ArrayList;
import java.util.List;

//@CssImport(value = "./css/common.css")
public class ApplicationsHorizontalView extends HorizontalLayout {

    public static List<HorizontalLayout> horizontalDivFill(List<ApplicationDto> listDto) {
        List<HorizontalLayout> listLayout = new ArrayList<>();
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        int maxHorizontalAppsCount = 4;
        int counter = 0;

        for (ApplicationDto dto : listDto) {
            counter++;
            if(counter % maxHorizontalAppsCount == 0) {
                listLayout.add(layout);
                layout = new HorizontalLayout();
                layout.setSizeFull();
            }
            layout.add(applicationBlockFill(dto));
        }
        listLayout.add(layout);
        return listLayout;
    }

    private static Div applicationBlockFill(ApplicationDto dto) {
        Div appDiv = new Div();
        appDiv.setId("cell");

        Div appHead = new Div();
        appHead.setHeight("55px");
        String logoName = dto.getLogoId().toString() + ".png";
        StreamResource res = new StreamResource(logoName,
                () -> AppView.getImageInputStream(logoName));
        Image image = new Image(res, "AppLogo");
        image.setWidth("48px");
        image.setHeight("48px");
        appHead.add(image);

        Div appBody = new Div();
        appBody.setId("promoText");
        Button name = new Button(dto.getName());
        name.setId("link");
        name.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        name.addClickListener(spanClickEvent -> { System.out.println("Click"); });
        Span about = new Span(" - " + dto.getDescription());
        appBody.add(name, about);

        Div appFoot = new Div();
        appFoot.setHeight("31px");
        Span text = new Span("Установить");
        text.setId("text");
        Button button = new Button(text);
        button.setId("btn");
        button.addClassName("btnBorder");
        button.addClickListener(buttonClickEvent -> { System.out.println("App installed"); });
        appFoot.add(button);

        appDiv.add(appHead,appBody,appFoot);
        return appDiv;
    }
}
