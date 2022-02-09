package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.QuestionButton;
import com.warehouse_accounting.models.dto.ConnectorSettingsDto;


@PageTitle("Интернет-магазин")
@Route(value = "connectorsettings", layout = SettingsView.class)
public class ConnectorSettingsView extends VerticalLayout {

    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout verticalLayout2 = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public ConnectorSettingsView() {
        verticalLayout.add(synchr());
        add(verticalLayout, verticalLayout2);

        verticalLayout2.add(createGrid());

    }

    private HorizontalLayout synchr() {
        HorizontalLayout synchrLayout = new HorizontalLayout();
        Label synchronization = new Label("Синхронизация");
        
        synchronization.getStyle().set("margin-top","11px");
        synchrLayout.add(buttonQuestion("К МоемуСкладу можно подключить интернет-магазин и настроить " +
                "автоматическую синхронизацию каталога товаров, " +
                "списка заказов и остатков."), synchronization, buttonReload(), SelectBasic());
        return synchrLayout;
    }

    private Button buttonQuestion(String text) {

        return QuestionButton.buttonQuestion(text);
    }

    private Button buttonReload() {

        Button reload = new Button((new Icon("lumo", "reload")));
        reload.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return reload;
    }

    private Select<String> SelectBasic() {
        Select<String> select = new Select<>();
        select.setItems("Магазин", "OZON", "YandexMarket");
        select.setValue("Магазин");
        add(select);
        return select;
    }

    private Grid<ConnectorSettingsDto> createGrid() {

        Grid<ConnectorSettingsDto> grid = new Grid<>(ConnectorSettingsDto.class, false);
        grid.addColumn(ConnectorSettingsDto::getShops).setHeader("Магазин");
        grid.addColumn(ConnectorSettingsDto::getType).setHeader("Тип");
        grid.addColumn(ConnectorSettingsDto::getOrders).setHeader("Заказы");
        grid.addColumn(ConnectorSettingsDto::getLeftovers).setHeader("Остатки");

        return grid;
    }
}