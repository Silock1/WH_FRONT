package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.tasks.Tasks;
import com.warehouse_accounting.components.tasks.filter.TasksFilter;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.TasksService;

import java.util.Objects;

@Route(value = "tasks", layout = AppView.class)
@PageTitle("Задачи")
public class TasksMenuView extends VerticalLayout {

    private final TasksFilter tasksFilter;
    private Tasks tasks;
    private final Div pageContent = new Div();
    private final TasksService tasksService;
    private final EmployeeService employeeService;

    private final ContractorService contractorService;

    public TasksMenuView(TasksService tasksService, TasksFilter tasksFilter, EmployeeService employeeService
            , ContractorService contractorService) {
        this.tasksService = tasksService;
        this.employeeService = employeeService;
        this.tasksFilter = tasksFilter;
        this.contractorService = contractorService;
        pageContent.removeAll();
        pageContent.add(initTasks(pageContent));
        pageContent.setSizeFull();
        add(pageContent);
    }

    private Tasks initTasks(Div pageContent) {
        if (Objects.isNull(tasks)) {
            tasks = new Tasks(pageContent, tasksService, tasksFilter
                    , employeeService,contractorService);
        }
        return tasks;
    }

}
