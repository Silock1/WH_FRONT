package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.warehouse_accounting.components.retail.grids.FileGridLayOut;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.FileDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.FileService;
import lombok.Getter;
import lombok.Setter;


@CssImport(value = "./css/application.css")
@Getter
@Setter
public class BonusTransactionForm extends VerticalLayout {
    private Button closedButton;
    private TypeOperation typeOperation;
    private Button saveButton;
    private IntegerField idInput;
    private IntegerField bonusValueInput;
    private ComboBox<BonusProgramDto> bonusProgram;
    private ComboBox<ContractorDto> contractor;
    private TextArea comment;
    private DatePicker executionDate;
    private DatePicker createdDate;

    private FileService fileService;

    private final EmployeeService employeeService;

    private FileGridLayOut fileGridLayOut;
    private SilverButton silverButton = new SilverButton();
    private Upload fileUpload;
    private Button taskButton;


    public BonusTransactionForm(TypeOperation typeOperation, FileService fileService, EmployeeService employeeService) {
        this.fileService = fileService;
        this.typeOperation = typeOperation;
        this.employeeService = employeeService;

        setSizeFull();
        setVisible(false);

        fileGridLayOut = new FileGridLayOut(this.fileService);
        add(
                buttonLine(),
                titleLine(),
                checkBoxLine(),
                pointsLine(),
                headerCommentLine(),
                commentLine(),
                taskButtonLine(),
                filesButtonLine(),
                filesTableLine()

        );
    }

    public HorizontalLayout buttonLine() {
        HorizontalLayout l = new HorizontalLayout();

        l.add(
                saveButton = new Button("Сохранить"),
                closedButton = new Button("Закрыть"),
                new Button("Изменить", click -> silverButton.greenNotification("ИЗМЕНИТЬ"))//Menubar удалить копировать ЗАГЛУШКА
        );
        return l;

    }

    public HorizontalLayout titleLine() {
        HorizontalLayout l = new HorizontalLayout();
        idInput = new IntegerField();
        idInput.setWidth("40px");


        l.add(
                new Span(String.format("%s бонусных баллов №", typeOperation.getValue())),
                idInput,
                new Span("от"),
                createdDate = new DatePicker()//created

        );

        return l;

    }

    public HorizontalLayout checkBoxLine() {
        HorizontalLayout l = new HorizontalLayout();
        Icon icon = new Icon(VaadinIcon.PENCIL);
        icon.addClickListener(click -> silverButton.greenNotification("РЕДАКТИРОВАНИЕ BonusProgramDto"));
        icon.setSize("10px");
        bonusProgram = new ComboBox<>();
        contractor = new ComboBox<>();

        l.add(
                new Span("Бонусная программа"),
                bonusProgram,//combobox bonusProgram
                icon,
                new Span("Контрагент"),
                contractor//combobox contrAgent
        );

        l.setAlignItems(Alignment.CENTER);
        return l;

    }

    public HorizontalLayout pointsLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span(typeOperation.getValue()),
                bonusValueInput = new IntegerField(),//bonusValue
                new Span("баллов"),
                new Span(typeOperation.dateType()),
                executionDate = new DatePicker()//executionDate дата начисления/списания
        );
        return l;

    }

    public HorizontalLayout headerCommentLine() {
        HorizontalLayout l = new HorizontalLayout();
        Tabs commentHeader = new Tabs();
        Tab homeTab = new Tab("Главная");
        homeTab.setSelected(true);
        Tab documentsTab = new Tab("Связанные документы");
        commentHeader.add(homeTab, documentsTab);

        commentHeader.addSelectedChangeListener(selected -> {
            comment.setVisible(selected.getSelectedTab().getLabel().equals("Главная"));

        });

        l.add(
                commentHeader

        );
        return l;

    }

    public HorizontalLayout commentLine() {
        HorizontalLayout l = new HorizontalLayout();
        comment = new TextArea();
        comment.setWidth("500px");
        comment.setHeight("70px");
        comment.setPlaceholder("Комментарий");


        l.add(comment);

        return l;

    }

    public HorizontalLayout taskButtonLine() {
        HorizontalLayout l = new HorizontalLayout();
        taskButton = silverButton.buttonPLusBlue("Задачи");
        taskButton.addClickListener(click -> silverButton.greenNotification("ЗАДАЧИ"));


        l.add(
                new Span("Задачи"),
                taskButton

        );
        return l;

    }

    public HorizontalLayout filesButtonLine() {
        HorizontalLayout l = new HorizontalLayout();
        MultiFileMemoryBuffer multiBuffer = new MultiFileMemoryBuffer();
        EmployeeDto currentEmployee = employeeService.getPrincipalManually();
        fileUpload = new Upload(multiBuffer);
        fileUpload.setUploadButton(silverButton.buttonPLusBlue("Файлы"));
        fileUpload.setDropAllowed(false);
        fileUpload.addSucceededListener(event ->
        {
            FileDto fileDto = new FileDto();
            fileDto.setSize(round((double) event.getContentLength() / 1_000_000));
            fileDto.setName(event.getFileName());
            fileDto.setEmployeeDto(currentEmployee);
            fileService.create(fileDto);
            fileGridLayOut.updateFileGridColumns();

            System.out.println(fileService.getFilesByTransactionId(1L));
        });

        l.setAlignItems(Alignment.CENTER);
        l.add(
                new Span("Файлы"),
                fileUpload);
        return l;

    }

    private double round(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    public HorizontalLayout filesTableLine() {

        HorizontalLayout l = new HorizontalLayout();


        l.add(fileGridLayOut);
        return l;

    }

    public enum TypeOperation {
        EARNING("Начисление"), SPENDING("Списание");
        private String type;

        TypeOperation(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }

        public String dateType() {
            if (this == EARNING) {
                return "Дата начислен" +
                        "ия";
            } else {
                return "Дата списания";
            }
        }


    }


}
