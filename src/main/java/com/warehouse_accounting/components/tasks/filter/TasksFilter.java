package com.warehouse_accounting.components.tasks.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.tasks.Tasks;
import com.warehouse_accounting.components.tasks.grids.TasksGrid;
import com.warehouse_accounting.components.util.DateConvertor;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class TasksFilter extends VerticalLayout {
    private TasksGrid tasksGrid;
    private EmployeeService employeeService;
    @Setter
    private Tasks tasks;


    Button find = new Button("Найти");
    Button clear = new Button("Очистить");

    Button settingFilter = new Button(new Icon(VaadinIcon.COG));
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));


    TextField description = new TextField();
    DatePicker dateBefore = new DatePicker();
    DatePicker dateAfter = new DatePicker();
    Select<Boolean> isDone = new Select<>(true, false);
    Select<String> contragent = new Select<>(); //сделать


    public TasksFilter(TasksGrid tasksGrid, EmployeeService employeeService) {
        this.tasksGrid = tasksGrid;
        this.employeeService = employeeService;

        description.setLabel("Описание");
        dateBefore.setLabel("Срок от");
        dateAfter.setLabel("Срок до");
        isDone.setLabel("Выполнена");
        contragent.setLabel("Исполнитель");

        contragent.setItems(employeeService.getAll().stream()
                .map(employeeDto -> employeeDto.getFirstName()).collect(Collectors.toList())
        );
        HorizontalLayout layout_one = new HorizontalLayout(find, clear, settingFilter, bookmarks);
        HorizontalLayout layout_two = new HorizontalLayout(description, dateBefore, dateAfter, isDone, contragent);
        add(layout_one, layout_two);
        activatedFindButton();
        clearFields();

        setVisible(false);
    }

    public void activatedFindButton() {
        find.addClickListener(event -> {
            filteringTasks(description.getValue(), dateBefore.getValue(), dateAfter.getValue(), isDone.getValue(), contragent.getValue());
            tasks.hideTasksGrid(false);

        });

    }

    public void filteringTasks(String description, LocalDate dateBefore, LocalDate dateAfter, Boolean isDone, String contragent) {

        List<TasksDto> temp =
                tasksGrid.getTasksDto()
                        .stream()
                        .filter(tasksDto -> ((tasksDto.getDescription().toLowerCase().contains(description.toLowerCase())) || (tasksDto.getDescription().equals(""))))
                        .filter(tasksDto -> {
//                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                            if (dateBefore == null && dateAfter == null) return true;
                            else if (dateBefore == null) {
//                                return (tasksDto.getDeadline().compareTo(dtf.format(dateAfter)) <= 0);
                                return (tasksDto.getDeadline().compareTo(DateConvertor.asText(dateAfter)) <= 0);
                            } else if (dateAfter == null) {
//                                return tasksDto.getDeadline().compareTo((dtf.format(dateBefore))) >= 0;
                                return tasksDto.getDeadline().compareTo((DateConvertor.asText(dateBefore))) >= 0;
                            } else
//                            return (tasksDto.getDeadline().compareTo((dtf.format(dateBefore))) >= 0 && tasksDto.getDeadline().compareTo(dtf.format(dateAfter)) <= 0);
                            return (tasksDto.getDeadline().compareTo((DateConvertor.asText(dateBefore))) >= 0 && tasksDto.getDeadline().compareTo(DateConvertor.asText(dateAfter)) <= 0);
                        })
                        .filter(tasksDto -> {
                            if (isDone == null) {
                                return true;
                            } else return isDone == tasksDto.getIsDone();
                        })
                        .filter(tasksDto -> {
                            if (contragent == null) {
                                return true;
                            } else if (tasksDto.getEmployeeName() == null) {
                                return false;
                            } else return tasksDto.getEmployeeName().equals(contragent);
                        })
                        .collect(Collectors.toList());

        tasksGrid.getTaskDtoGrid().setItems(temp);
        add(tasksGrid.getTaskDtoGrid());
    }

    public void clearFields() {
        clear.addClickListener(event -> {
            description.clear();
            dateBefore.clear();
            dateAfter.clear();
            isDone.clear();
            contragent.clear();
        });
    }
}
