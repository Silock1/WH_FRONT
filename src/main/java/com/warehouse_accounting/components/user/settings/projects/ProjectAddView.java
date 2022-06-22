package com.warehouse_accounting.components.user.settings.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.interfaces.ProjectService;
import lombok.extern.log4j.Log4j2;


@PageTitle("Добавить проект")
@Route(value = "addProject", layout = SettingsView.class)
@Log4j2
public class ProjectAddView extends VerticalLayout {

    private final transient ProjectService projectService;

    public ProjectAddView(ProjectService projectService) {
        this.projectService = projectService;
        addForm();

    }

        private  void addForm(){

        TextField nameField = new TextField("Наименование");
        TextField codeField = new TextField("Код");
        TextField descriptionField = new TextField("Описание");
        FormLayout formLayout = new FormLayout(
                nameField,
                codeField,
                descriptionField);

        Button create = new Button("Сохранить", event -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setName(nameField.getValue());
            projectDto.setCode(codeField.getValue());
            projectDto.setDescription(descriptionField.getValue());
            projectService.create(projectDto);

            getUI().ifPresent(ui -> ui.navigate("project"));
        });


        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Закрыть");
        cancel.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("project")));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);
        add(formLayout,buttonLayout);
    }
}
