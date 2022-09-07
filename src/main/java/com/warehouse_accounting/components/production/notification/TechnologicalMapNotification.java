package com.warehouse_accounting.components.production.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
public class TechnologicalMapNotification extends Notification {

    public Notification show() {
        // When creating a notification using the constructor,
        // the duration is 0-sec by default which means that
        // the notification does not close automatically.

        Notification notification = new Notification();
        Anchor anchor1 = new Anchor("", "Учет производственных операций");
        Anchor anchor2 = new Anchor("", "Базовый способ");
        Anchor anchor3 = new Anchor("", "Расширенный способ");
        anchor1.setTarget("_blank");
        anchor2.setTarget("_blank");
        anchor3.setTarget("_blank");
        Div text = new Div(
                new Text("\t\n" +
                        "Технологическая карта описывает состав изделия — с комплектующими, сырьем и материалами.\n" +
                        "Технологическая карта может использоваться как в базовом, так и в расширенном способе производства\n" +
                        "\n"),
                new Div(new Text("Читать инструкцию: "), anchor1),
                new Div(new Text("Видео: "), anchor2),
                new Div(new Text("Видео:"), anchor3)

        );
        return closeBtn(text);
    }

    public Notification showArchive() {
        // When creating a notification using the constructor,
        // the duration is 0-sec by default which means that
        // the notification does not close automatically.

        Anchor anchor1 = new Anchor("", "архив");
        anchor1.setTarget("_blank");
        Div text = new Div(
                new Text("Элементы, перемещенные в "),
                anchor1,
                new Text(", не отображаются в справочниках и отчетах. Архив позволяет скрывать неактуальные элементы, не удаляя их.")
        );
        return closeBtn(text);
    }

    private Notification closeBtn(Div text) {
        Notification notification = new Notification();
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
        notification.open();
        notification.setPosition(Position.TOP_START);
        return notification;
    }
}
