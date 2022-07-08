package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;

import java.util.List;
import java.util.stream.Collectors;

@CssImport("./css/marker.css")
public class Marker extends ComboBox {
    public Marker() {
        super();
        List<List<String>> markerText = List.of(List.of("Новый", "dtb-marker-yelow"),
                List.of("Подтверждён", "dtb-marker-blue"),
                List.of("Доставлен", "dtb-marker-green"),
                List.of("Возврат", "dtb-marker-red"));
        List<Span> markers = markerText.stream()
                .map(element -> {
                    Span marker = new Span(element.get(0));
                    marker.addClassName(element.get(1));
                    return marker;
                })
                .collect(Collectors.toList());

        setItems(markerText.stream()
                .map(x -> x.get(0)).collect(Collectors.toList()));

        addValueChangeListener(event ->
                markerText.forEach(e -> {
                    if(event.getValue().equals(e.get(0))) {
                    setClassName(e.get(1));
                }})
        );

        this.setValue("Новый");
    }
}
