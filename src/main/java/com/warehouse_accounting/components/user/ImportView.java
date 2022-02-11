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

@PageTitle("Импорт")
@Route(value = "import", layout = SettingsView.class)

public class ImportView extends VerticalLayout{
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout verticalLayout2 = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public ImportView() {
        verticalLayout.add(importSetting());
        add(verticalLayout, verticalLayout2);

        verticalLayout2.add(createGrid());

    }


    private HorizontalLayout importSetting() {
        HorizontalLayout importLayout = new HorizontalLayout();
        Label importing = new Label("Импорт");

        importing.getStyle().set("margin-top","11px");
        importLayout.add(buttonQuestion("В разделе отображаются результаты всех операций по импорту товаров," +
                " контрагентов и дополнительных справочников, а также результаты действий с ЭДО." +
                "Импорт запускается из разделов Товары → Товары и услуги, Контрагенты → Контрагенты," +
                " а также из документов."), importing, buttonReload(), SelectBasic(),deleteTasks());
        return importLayout;
    }

    private Button buttonQuestion(String text) {

        return QuestionButton.buttonQuestion(text);
    }

    private Button buttonReload() {

        Button reload = new Button((new Icon("lumo", "reload")));
        reload.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return reload;
    }

    private Button deleteTasks() {

        Button delete = new Button("Удалить завершенные задачи");
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return delete;
    }

    private Select<String> SelectBasic() {
        Select<String> select = new Select<>();
        select.setItems("Импортировать", "Товары", "Остатки");
        select.setValue("Импортировать");
        add(select);
        return select;
    }

    private Grid<ConnectorSettingsDto> createGrid() {

        Grid<ConnectorSettingsDto> grid = new Grid<>(ConnectorSettingsDto.class, false);
        grid.addColumn(ConnectorSettingsDto::getShops).setHeader("Задача");
        grid.addColumn(ConnectorSettingsDto::getType).setHeader("Создана");
        grid.addColumn(ConnectorSettingsDto::getOrders).setHeader("Завершена");
        grid.addColumn(ConnectorSettingsDto::getLeftovers).setHeader("Статус");

        return grid;
    }
}
