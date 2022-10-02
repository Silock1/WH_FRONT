package com.warehouse_accounting.components.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import elemental.css.CSSStyleDeclaration;

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
}
