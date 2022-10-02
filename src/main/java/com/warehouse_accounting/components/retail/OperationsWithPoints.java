package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**Операции с баллами (Розница/Операции с баллами)
**/

@Route("points_operations")
public class OperationsWithPoints extends VerticalLayout {

    public OperationsWithPoints() {

        add(new Span("Hello OperationsWithPoints HELLO WORLD TESTESTESTESTEST"));

    }
}
