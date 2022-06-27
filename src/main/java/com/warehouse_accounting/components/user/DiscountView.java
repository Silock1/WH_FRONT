package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;


@PageTitle("Скидки")
@Route(value = "discount", layout = SettingsView.class)
public class DiscountView extends VerticalLayout {

    public DiscountView() {
        add(getGroupButtons());
    }

    private VerticalLayout getGroupButtons() {
        var verticalLayout = new VerticalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> Notification.show("" +
                "Вы можете устанавливать для контрагентов и на отдельные товары разные типы  скидок: " +
                "специальную цену, персональную или накопительную скидку, округление копеек, бонусные программы. " +
                "Чтобы скидка действовала, необходимо поставить флажок в поле Действует. " +
                "Чтобы выключить скидку, флажок нужно убрать. При этом сама скидка останется в списке, ее можно будет включить позже. " +
                "В разделе Розница → Точки продаж можно ограничить скидки, которые кассир может установить в приложении Касса МойСклад.\n" +
                "Читать инструкцию: Скидки", 7000, Notification.Position.TOP_START));

        Span script = new Span("Скидки");
        script.getElement().getThemeList().add("badge contrast");

        Button reload = new Button((new Icon("lumo", "reload")));
        reload.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button script_template = new Button("Скидки", new Icon(VaadinIcon.PLUS));
        script_template.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button allScript = new Button("Все скидки");
        Button activeScript = new Button("Только активные");

        Button text = new Button("Активных скидок нет");
        text.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout one = new HorizontalLayout(helpButton, script, reload, script_template, allScript, activeScript);
        HorizontalLayout two = new HorizontalLayout(text);
        verticalLayout.add(one, two);
        return verticalLayout;
    }
}