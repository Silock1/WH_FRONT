package com.warehouse_accounting.components.user.settings.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.ProjectService;


@PageTitle("Добавить проект")
@Route(value = "addProject", layout = SettingsView.class)
public class ProjectAddView extends VerticalLayout {

    private final ProjectService projectService;
    private final Grid<CountryDto> grid = new Grid<>();

    public ProjectAddView(ProjectService projectService) {
        this.projectService = projectService;
        AddForm();

    }

    private  void  AddForm(){

        TextField nameField = new TextField("Наименование");
        TextField codeField = new TextField("Код");
        TextField descriptionField = new TextField("Описание");
        FormLayout formLayout = new FormLayout(
                nameField,
                codeField,
                descriptionField);

        Button create = new Button("Сохранить");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Закрыть");
        cancel.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("project")));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);
        add(formLayout,buttonLayout);
    }
}
