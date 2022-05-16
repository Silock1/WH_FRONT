package com.warehouse_accounting.components.user.settings.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.impl.ProjectServiceImpl;
import com.warehouse_accounting.services.impl.TasksServiceImpl;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.TasksService;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.format.DateTimeFormatter;


@PageTitle("Добавить проект")
@Route(value = "addProject", layout = SettingsView.class)
@Log4j2
public class ProjectAddView extends VerticalLayout {

    private ProjectsView returnLayer;
    private Div parentLayer = new Div();
    private final ProjectService projectService;
    private final Grid<ProjectDto> grid = new Grid<>();
    private Binder<ProjectDto> projectDtoBinder;
    private ProjectDto projectDto;
    private Notification notification;

    public ProjectAddView(ProjectsView returnLayer
            ,ProjectService projectService) {
        this.returnLayer = returnLayer;
        this.projectService = projectService;
        this.projectDtoBinder = new Binder<>(ProjectDto.class);
        this.projectDto = new ProjectDto();
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

        Button create = new Button("Сохранить", event -> {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://localhost:4446")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();

//                ProjectService projectService = new ProjectService;
//                        ("/api/project", retrofit);
                ProjectDto projectDto = new ProjectDto();
                projectDto.setName(nameField.getValue());
                projectDto.setCode(codeField.getValue());
                projectDto.setDescription(descriptionField.getValue());
                projectService.create(projectDto);

//                log.error("Ошибка создания Проекта");
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//                notification.setPosition(Notification.Position.BOTTOM_STRETCH);

//            parentLayer.removeAll();
//            parentLayer.add(returnLayer);
        });


        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Закрыть");
        cancel.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("project")));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);
        add(formLayout,buttonLayout);
    }
}
