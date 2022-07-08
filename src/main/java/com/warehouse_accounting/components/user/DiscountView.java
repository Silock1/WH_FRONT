package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.DiscountDto;
import com.warehouse_accounting.services.interfaces.DiscountService;



@PageTitle("Скидки")
@Route(value = "discount", layout = SettingsView.class)
public class DiscountView extends VerticalLayout {

    private Grid<DiscountDto> discountGrid = new Grid<>(DiscountDto.class, false);
    private DiscountService discountService;

    public DiscountView(DiscountService discountService) {
        this.discountService = discountService;
        add(getGroupButtons());
        add(discountGrid());
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

        Span discount = new Span("Скидки");
        discount.getElement().getThemeList().add("badge contrast");

        Button reload = new Button((new Icon("lumo", "reload")));
        reload.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button discount_t = new Button("Скидки", new Icon(VaadinIcon.PLUS));
        discount_t.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button allDiscount = new Button("Все скидки");
        Button activeDiscount = new Button("Только активные");

        Button text = new Button("Активных скидок нет");
        text.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout one = new HorizontalLayout(helpButton, discount, reload, discount_t, allDiscount, activeDiscount);
        HorizontalLayout two = new HorizontalLayout(text);
        verticalLayout.add(one, two);
        return verticalLayout;
    }

    private HorizontalLayout discountGrid() {
        var horizontalLayout = new HorizontalLayout();
        discountGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        discountGrid.setItems(discountService.getAll());
        discountGrid.addColumn(DiscountDto::getActive).setHeader("Активность");
        discountGrid.addColumn(DiscountDto::getName).setHeader("Наименование");
        add(discountGrid);
        return horizontalLayout;
    }
}