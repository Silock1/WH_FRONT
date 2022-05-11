package com.warehouse_accounting.components.user.settings.projects;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.SettingCountryAddView;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.ProjectService;

import java.util.List;


    @PageTitle("Настройки")
    @Route(value = "project", layout = SettingsView.class)
    public class ProjectsView extends VerticalLayout {

        private final ProjectService projectService;

        public ProjectsView(ProjectService projectService) {
            this.projectService = projectService;
            H2 tableName = new H2("Проекты");
            Button addUnits = new Button("Добавить проект");
            addUnits.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            addUnits.addClickListener(e-> UI.getCurrent().navigate(ProjectAddView.class));
            HorizontalLayout header = new HorizontalLayout(tableName);
            header.setAlignItems(FlexComponent.Alignment.CENTER);
            header.getThemeList().clear();

            Button delete = new Button("Удалить");
            delete.setEnabled(false);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            delete.getStyle().set("margin-inline-start", "auto");

            Grid<ProjectDto> grid = new Grid<>(ProjectDto.class, false);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(ProjectDto::getName).setHeader("Наименование");
            grid.addColumn(ProjectDto::getCode).setHeader("Код");
            grid.addColumn(ProjectDto::getDescription).setHeader("Описание");


            grid.addSelectionListener(selection -> {
                int size = selection.getAllSelectedItems().size();
                boolean isSingleSelection = size == 1;

                delete.setEnabled(size != 0);
            });

            HorizontalLayout footer = new HorizontalLayout(addUnits, delete);
            footer.getStyle().set("flex-wrap", "wrap");

            List<ProjectDto> projectDtos = projectService.getAll();
            grid.setItems(projectDtos);
            setPadding(false);
            setAlignItems(Alignment.STRETCH);
            add(header, grid, footer);
        }
    }
