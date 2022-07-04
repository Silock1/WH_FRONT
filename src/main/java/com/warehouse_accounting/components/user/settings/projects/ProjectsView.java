package com.warehouse_accounting.components.user.settings.projects;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.interfaces.ProjectService;

import java.util.List;


@PageTitle("Настройки")
@Route(value = "project", layout = SettingsView.class)
public class ProjectsView extends VerticalLayout {

    private final transient ProjectService projectService;

    public ProjectsView(ProjectService projectService) {
        this.projectService = projectService;
        H2 tableName = new H2("Проекты");
        Button addUnits = new Button("Добавить проект");
        addUnits.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUnits.addClickListener(e -> UI.getCurrent().navigate("addProject"));
        HorizontalLayout header = new HorizontalLayout(tableName);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getThemeList().clear();

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");


        Grid<ProjectDto> grid = new Grid<>(ProjectDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        Grid.Column<ProjectDto> nameColumn = grid.addColumn(ProjectDto::getName).setHeader("Наименование");
        Grid.Column<ProjectDto> codeColumn = grid.addColumn(ProjectDto::getCode).setHeader("Код");
        Grid.Column<ProjectDto> descriptionColumn =
                grid.addColumn(ProjectDto::getDescription).setHeader("Описание");

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ProjectDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Наименование", nameColumn);
        columnToggleContextMenu.addColumnToggleItem("Код", codeColumn);
        columnToggleContextMenu.addColumnToggleItem("Описание", descriptionColumn);

        Binder<ProjectDto> binder = new Binder<>(ProjectDto.class);
        Editor<ProjectDto> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.addCloseListener(e -> {
            this.projectService.update(e.getItem());
            grid.setItems(this.projectService.getAll());
        });

        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .bind(ProjectDto::getName, ProjectDto::setName);
        nameColumn.setEditorComponent(nameField);

        TextField codeField = new TextField();
        codeField.setWidthFull();
        binder.forField(codeField)
                .bind(ProjectDto::getCode, ProjectDto::setCode);
        codeColumn.setEditorComponent(codeField);

        TextField descriptionField = new TextField();
        descriptionField.setWidthFull();
        binder.forField(descriptionField)
                .bind(ProjectDto::getDescription, ProjectDto::setDescription);
        descriptionColumn.setEditorComponent(descriptionField);

        grid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        grid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();

            delete.setEnabled(size != 0);
        });
        delete.addClickListener(event -> {
            for (ProjectDto projectDto :
                    grid.getSelectedItems()) {
                this.projectService.deleteById(projectDto.getId());
            }
            grid.setItems(this.projectService.getAll());
        });

        HorizontalLayout footer = new HorizontalLayout(addUnits, delete);
        footer.getStyle().set("flex-wrap", "wrap");

        List<ProjectDto> projectDtos = this.projectService.getAll();
        grid.setItems(projectDtos);
        setPadding(false);
        setAlignItems(Alignment.STRETCH);

        HorizontalLayout gridAndCog = new HorizontalLayout();
        gridAndCog.add(grid, menuButton);

        add(header, footer, gridAndCog);
    }
}
