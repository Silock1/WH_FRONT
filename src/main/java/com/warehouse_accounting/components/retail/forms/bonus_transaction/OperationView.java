package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
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
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.FileDto;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.FileService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@CssImport(value = "./css/application.css")
@Getter
@Setter
public class OperationView extends VerticalLayout {
    private Button closedButton;
    private TypeOperation typeOperation;
    private Button saveButton;
    private IntegerField idInput = new IntegerField();
    private IntegerField bonusValueInput;
    private Select<BonusProgramDto> selectBonusProgramDto = new Select<>();
    private Select<ContractorDto> selectContractorDto = new Select<>();
    private TextArea comment;
    private DatePicker executionDate;
    private DatePicker createdDate;
    private FileService fileService;
    private final EmployeeService employeeService;
    private FileGridLayOut fileGridLayOut;
    private SilverButton silverButton = new SilverButton();
    private Upload fileUpload;
    private Button taskButton;
    private List<FileDto> filesList = new ArrayList<>();

    private BonusTransactionService bonusTransactionService;
    private DepartmentService departmentService;
    private Button changeButton;
    private EmployeeDto employeeFromDialog;
    private DepartmentDto departmentFromDialog;
    private Boolean accessBoxFromDialog = false;
    Span departmentSpan;
    private Span employeeSpan;

    public OperationView(TypeOperation typeOperation, FileService fileService, EmployeeService employeeService, DepartmentService departmentService) {
        this.fileService = fileService;
        this.typeOperation = typeOperation;
        this.employeeService = employeeService;
        this.departmentService = departmentService;

        fileGridLayOut = new FileGridLayOut(this.fileService);
        employeeFromDialog = employeeService.getPrincipalManually();
        departmentFromDialog = employeeFromDialog.getDepartment();
        setSizeFull();
        setVisible(false);

        add(
                buttonLine(),
                headerForm(),
                mainFormLine(),
                headerCommentLine(),
                commentLine(),
                taskButtonLine(),
                filesButtonLine(),
                filesTableLine()
        );
    }

    private HorizontalLayout headerForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(Alignment.CENTER);
        idInput.setWidth("50px");
        idInput.setClassName("miniTitle");

        Span spanPercent = new Span(String.format("%s ???????????????? ???????????? ???", typeOperation.getValue()));
        spanPercent.setClassName("miniTitle");

        Span from = new Span("????");
        from.setClassName("miniTitle");
        createdDate = new DatePicker();

        header.add(
                spanPercent,
                idInput,
                from,
                createdDate
        );
        return header;
    }

    private HorizontalLayout mainFormLine() {
        HorizontalLayout mainFormLine = new HorizontalLayout();

        VerticalLayout leftData = new VerticalLayout();
        HorizontalLayout leftLineTwo = new HorizontalLayout();
        leftLineTwo.setAlignItems(Alignment.CENTER);
        Span spanBonusProgram = new Span("???????????????? ??????????????????");
        spanBonusProgram.setClassName("blackColor");
        spanBonusProgram.setWidth("150px");
        leftLineTwo.add(spanBonusProgram,
                selectBonusProgramDto,
                getPencil());

        HorizontalLayout leftLineThree = new HorizontalLayout();
        leftLineThree.setAlignItems(Alignment.CENTER);
        Span spanBonusValue = new Span(typeOperation.getValue());
        spanBonusValue.setClassName("blackColor");
        spanBonusValue.setWidth("150px");

        Span spanBalls = new Span("????????????");
        spanBalls.setClassName("blackColor");
        leftLineThree.add(spanBonusValue,
                bonusValueInput = new IntegerField(),//bonusValue
                spanBalls);

        leftData.add(
                leftLineTwo, leftLineThree
        );

        VerticalLayout rightData = new VerticalLayout();
        HorizontalLayout rightLineTwo = new HorizontalLayout();
        rightLineTwo.setAlignItems(Alignment.CENTER);
        Span spanContractor = new Span("????????????????????");
        spanContractor.setClassName("blackColor");
        spanContractor.setWidth("120px");
        rightLineTwo.add(spanContractor,
                selectContractorDto);

        HorizontalLayout rightLineThree = new HorizontalLayout();
        rightLineThree.setAlignItems(Alignment.CENTER);
        Span spanDate = new Span(typeOperation.dateType());
        spanDate.setClassName("blackColor");
        spanDate.setWidth("120px");
        rightLineThree.add(spanDate,
                executionDate = new DatePicker());

        rightData.add(rightLineTwo, rightLineThree);
        mainFormLine.add(leftData, rightData);
        return mainFormLine;
    }

    public HorizontalLayout buttonLine() {
        HorizontalLayout buttonLine = new HorizontalLayout();
        buttonLine.setAlignItems(Alignment.CENTER);
        changeButton = silverButton.buttonBlank("????????????????");
        changeButton.addClickListener(click -> silverButton.greenNotification("????????????????"));
        Div space = new Div();
        space.setWidth("70px");

        VerticalLayout employeeDialogLabel = new VerticalLayout();
        employeeDialogLabel.setSpacing(false);
        HorizontalLayout employeeDown = new HorizontalLayout();

        employeeSpan = new Span(employeeFromDialog.getFirstName());

        employeeSpan.setClassName("employeeName");
        employeeSpan.addClickListener(click -> getEmployeeMiniDialog().open());

        Icon icon = new Icon(VaadinIcon.CARET_DOWN);
        icon.getStyle().set("color", "#186999");
        icon.setSize("10px");
        employeeDown.add(employeeSpan, icon);
        employeeDown.setSpacing(false);
        employeeDown.setAlignItems(Alignment.CENTER);

        departmentSpan = new Span(departmentFromDialog.getName());
        departmentSpan.setClassName("employeeDialogLabel");
        employeeDialogLabel.add(employeeDown, departmentSpan);
        buttonLine.add(
                saveButton = silverButton.greenButton("??????????????????"),
                closedButton = silverButton.buttonBlank("??????????????"),
                changeButton,
                space,
                new HorizontalLayout(employeeDialogLabel)


        );
        return buttonLine;

    }


    private Dialog getEmployeeMiniDialog() {
        Dialog employeeDialog = new Dialog();
        Span spanOwner = new Span("????????????????");

        HorizontalLayout ownerLine = new HorizontalLayout();
        Span ownerLabel = new Span("??????????????????");
        ownerLabel.setWidth("75px");
        Select<EmployeeDto> employeeDtoSelect = new Select<>();
        employeeDtoSelect.addValueChangeListener(event -> {
                    employeeFromDialog = event.getValue();
                    departmentFromDialog = employeeFromDialog.getDepartment();
                    employeeSpan.setText(employeeFromDialog.getFirstName());
                }
        );

        employeeDtoSelect.setItems(employeeService.getAll());
        employeeDtoSelect.setValue(employeeService.getPrincipalManually());
        employeeDtoSelect.setItemLabelGenerator(EmployeeDto::getFirstName);

        ownerLine.add(ownerLabel, employeeDtoSelect);

        HorizontalLayout departmentLine = new HorizontalLayout();
        Span departmentLabel = new Span("??????????");
        departmentLabel.setWidth("75px");
        Select<DepartmentDto> departmentDtoSelect = new Select<>();
        departmentDtoSelect.addValueChangeListener(event -> {
                    departmentFromDialog = event.getValue();
                    departmentSpan.setText(departmentFromDialog.getName());
                }

        );
        departmentDtoSelect.setItems(departmentService.getAll());
        departmentDtoSelect.setValue(employeeService.getPrincipalManually().getDepartment());
        departmentDtoSelect.setItemLabelGenerator(DepartmentDto::getName);

        departmentLine.add(departmentLabel, departmentDtoSelect);

        Checkbox accessBox = new Checkbox("?????????? ????????????");
        accessBox.addValueChangeListener(event -> accessBoxFromDialog = event.getValue());
        employeeDialog.add(
                spanOwner,
                ownerLine,
                departmentLine,
                accessBox
        );


        return employeeDialog;
    }

    public void clearUpload() {
        fileUpload.getElement().executeJs("this.files=[]");
    }


    public void setFileList(Long id) {
        if (idInput.getValue() == null) {
            idInput.setValue(0);
        }

        filesList = fileService.getFilesByTransactionId(id);
        updateFileData();

    }


    public HorizontalLayout headerCommentLine() {
        HorizontalLayout l = new HorizontalLayout();
        Tabs commentHeader = new Tabs();
        Tab homeTab = new Tab("??????????????");
        homeTab.setSelected(true);
        Tab documentsTab = new Tab("?????????????????? ??????????????????");
        commentHeader.add(homeTab, documentsTab);

        commentHeader.addSelectedChangeListener(selected -> {
            comment.setVisible(selected.getSelectedTab().getLabel().equals("??????????????"));

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
        comment.setPlaceholder("??????????????????????");


        l.add(comment);

        return l;

    }

    public HorizontalLayout taskButtonLine() {
        HorizontalLayout l = new HorizontalLayout();
        taskButton = silverButton.buttonPLusBlue("????????????");
        taskButton.addClickListener(click -> silverButton.greenNotification("????????????"));

        Span task = new Span("????????????");
        task.setClassName("blackColor");
        l.add(
                task,
                taskButton

        );
        return l;

    }

    public HorizontalLayout filesButtonLine() {
        HorizontalLayout l = new HorizontalLayout();
        MultiFileMemoryBuffer multiBuffer = new MultiFileMemoryBuffer();
        EmployeeDto currentEmployee = employeeService.getPrincipalManually();
        fileUpload = new Upload(multiBuffer);
        fileUpload.setUploadButton(silverButton.buttonPLusBlue("??????????"));
        fileUpload.setDropAllowed(false);

        fileUpload.addSucceededListener(event ->
        {
            FileDto fileDto = new FileDto();
            fileDto.setSize(round((double) event.getContentLength() / 1_000_000));
            fileDto.setName(event.getFileName());
            fileDto.setEmployeeDto(currentEmployee);

            filesList.add(fileDto);
            updateFileData();

        });
        Span files = new Span("??????????");
        files.setClassName("blackColor");
        l.setAlignItems(Alignment.CENTER);
        l.add(
                files,
                fileUpload);
        return l;

    }

    private Icon getPencil() {
        Icon icon = new Icon(VaadinIcon.PENCIL);
        icon.addClickListener(click -> silverButton.greenNotification("???????????????????????????? BonusProgramDto"));
        icon.setSize("15px");
        return icon;
    }

    private void updateFileData() {
        fileGridLayOut.getFileGrid().setItems(filesList);
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
        EARNING("????????????????????"), SPENDING("????????????????");
        private String type;

        TypeOperation(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }

        public String dateType() {
            if (this == EARNING) {
                return "???????? ????????????????" +
                        "????";
            } else {
                return "???????? ????????????????";
            }
        }


    }


}
