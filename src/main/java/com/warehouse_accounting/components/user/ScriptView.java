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
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ScriptDto;
import com.warehouse_accounting.services.interfaces.ScriptService;

@PageTitle("Сценарии")
@Route(value = "scripttemplate", layout = SettingsView.class)
public class ScriptView extends VerticalLayout {

    private Grid<ScriptDto> scriptGreed = new Grid<>(ScriptDto.class, false);
    private ScriptService scriptService;

    public ScriptView(ScriptService scriptService) {
        this.scriptService = scriptService;
        add(getGroupButtons());
        add(scriptGrid());
    }

    private VerticalLayout getGroupButtons() {
        var verticalLayout = new VerticalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> Notification.show("" +
                "Сценарии — это цепочки действий, которые при включении выполняются автоматически, " +
                "без участия пользователя. " +
                "Чтобы создать сценарий, нужно указать, какое событие его запускает, " +
                "что именно выполняется по сценарию и при каких условиях.\n" +
                "Читать инструкцию: Сценарии", 5000, Notification.Position.TOP_START));

        Span script = new Span("Сценарии");
        script.getElement().getThemeList().add("badge contrast");

        Button reload = new Button((new Icon("lumo", "reload")));
        reload.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button script_template = new Button("Сценарии", new Icon(VaadinIcon.PLUS));
        script_template.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button allScript = new Button("Все сценарии");
        Button activeScript = new Button("Только активные");

        HorizontalLayout one = new HorizontalLayout(helpButton, script, reload, script_template, allScript, activeScript);
        verticalLayout.add(one);
        return verticalLayout;
    }

    private HorizontalLayout scriptGrid() {
        var horizontalLayout = new HorizontalLayout();
        scriptGreed.setSelectionMode(Grid.SelectionMode.MULTI);
        scriptGreed.setItems(scriptService.getAll());

        Grid.Column<ScriptDto> activ = scriptGreed.addColumn(ScriptDto::getActivity).setHeader("Активность").setAutoWidth(true);
        Grid.Column<ScriptDto> name = scriptGreed.addColumn(ScriptDto::getName).setHeader("Наименование").setAutoWidth(true);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ScriptDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Активность", activ);
        columnToggleContextMenu.addColumnToggleItem("Наименование", name);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setAlignItems(Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        scriptGreed.setHeightByRows(true);
        headerLayout.setWidthFull();
        headerLayout.add(scriptGreed,  menuButton);
        setSizeFull();


        add(headerLayout);
        return horizontalLayout;
    }
}
