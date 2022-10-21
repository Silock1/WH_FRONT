package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.OperationView;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.FilterForm;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.MassEditView;
import com.warehouse_accounting.components.retail.grids.BonusTransactionGridLayout;
import com.warehouse_accounting.components.retail.toolbars.BonusTransactionToolBar;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.FileDto;
import com.warehouse_accounting.services.interfaces.BonusProgramService;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/


@SpringComponent
@Route(value = "bonus_transaction", layout = AppView.class)
@CssImport(value = "./css/application.css")
@UIScope
public class BonusTransaction extends VerticalLayout {

    private final BonusTransactionGridLayout grid;
    private final BonusTransactionToolBar toolBar;
    private final BonusTransactionService transactionService;
    private final BonusProgramService programService;
    private final ContractorService contractorService;
    private final FileService fileService;
    private final EmployeeService employeeService;
    private final OperationView earningForm;
    private final OperationView spendingForm;
    private final MassEditView massEditView;
    private final FilterForm filterForm = new FilterForm();
    private Set<BonusTransactionDto> selectedItems = new HashSet<>();
    private SilverButton silverButton = new SilverButton();
    private List<FileDto> listCopiedFiles = new ArrayList<>();

    @Autowired
    public BonusTransaction(BonusTransactionService transactionService,
                            BonusProgramService programService,
                            ContractorService contractorService, FileService fileService, EmployeeService employeeService, DepartmentService departmentService) {

        this.transactionService = transactionService;
        this.programService = programService;
        this.contractorService = contractorService;
        this.fileService = fileService;
        this.employeeService = employeeService;

        toolBar = new BonusTransactionToolBar(this.transactionService);
        grid = new BonusTransactionGridLayout(this.transactionService);
        massEditView = new MassEditView(this.employeeService, departmentService);
        earningForm = new OperationView(OperationView.TypeOperation.EARNING, this.fileService, this.employeeService);
        spendingForm = new OperationView(OperationView.TypeOperation.SPENDING, this.fileService, this.employeeService);

        setMiniField();
        setVisibleChangeSubmenu();
        setEditLogic();
        setFilterTextField();
        setCloseButtonEarning();
        setCloseButtonSpending();
        setSaveButtonSpending();
        setSaveButtonEarning();
        setSubMenuEarning();
        setSubMenuSpending();
        setRefreshButton();
        setSubMenuMassEdit();
        setFilterButtonLogic();
        setDeleteLogic();
        setCopyLogic();
        setCloseMassEdit();
        setContinueButtonLogic();


        add(toolBar, filterForm, grid, earningForm, spendingForm, massEditView);
    }


    private void setEditLogic() {
        grid.getPointsGrid().addItemDoubleClickListener(event -> {
                    BonusTransactionDto.TransactionType currentType = event.getItem().getTransactionType();
                    if (currentType == BonusTransactionDto.TransactionType.EARNING) {
                        earningForm.clearUpload();
                        setForm(event.getItem(), earningForm);
                        earningForm.setFileList(event.getItem().getId());
                        openForm(earningForm);
                    } else {
                        setForm(event.getItem(), spendingForm);
                        spendingForm.clearUpload();
                        spendingForm.setFileList(event.getItem().getId());
                        openForm(spendingForm);
                    }
                }
        );

    }


    private void setCloseButtonEarning() {
        earningForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeForm(earningForm);
                    updateGrid();
                }
        );
    }

    private void setFilterTextField() {
        TextField filterField = toolBar.getSearchField();
        filterField.setValueChangeMode(ValueChangeMode.LAZY);

        filterField.addValueChangeListener(
                field ->
                {
                    updateGrid(transactionService.filter(field.getValue()));

                }
        );

    }


    private void setCloseButtonSpending() {
        spendingForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeForm(spendingForm);
                    updateGrid();
                }
        );
    }

    private void setFilterButtonLogic() {
        toolBar.getFilterButton().addClickListener(event -> {
            filterForm.setVisible(!filterForm.isVisible());
        });
    }

    private void openForm(OperationView form) {
        grid.setVisible(false);
        toolBar.setVisible(false);
        form.setVisible(true);
    }

    private void closeForm(OperationView form) {
        grid.setVisible(true);
        toolBar.setVisible(true);
        form.setVisible(false);
    }

    private void updateGrid() {

        grid.getPointsGrid().setItems(transactionService.getAll());
    }

    private void updateGrid(List<BonusTransactionDto> list) {

        grid.getPointsGrid().setItems(list);
    }

    private void setRefreshButton() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            updateGrid();
        });
    }

    private void setSubMenuEarning() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {
                    earningForm.setFileList(0L);
                    earningForm.clearUpload();
                    setForm(new BonusTransactionDto(), earningForm);
                    openForm(earningForm);
                }
        );
    }

    private void setSubMenuSpending() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    spendingForm.setFileList(0L);
                    spendingForm.clearUpload();
                    setForm(new BonusTransactionDto(), spendingForm);
                    openForm(spendingForm);
                }
        );
    }

    private void setSubMenuMassEdit() {
        setSelectedItems();
        toolBar.getMassEdit().addClickListener(event -> {
          //TODO: баги, пока на доработке

            if (selectedItems.size() == 0) {
                selectedItems = new HashSet<>(transactionService.getAll());
            }
            massEditView.getSpanSelectedItems().setText(String.format("Выбрано %d элементов", selectedItems.size()));
            selectedItems = new HashSet<>(); //clear. clear() выкидывает exception

            openMassEdit();
        });
    }

    private void openMassEdit() {
        toolBar.setVisible(false);
        grid.setVisible(false);
        massEditView.setVisible(true);
    }

    private void closeMassEdit() {

        toolBar.setVisible(true);
        grid.setVisible(true);
        massEditView.setVisible(false);

    }

    private void setCloseMassEdit() {
        massEditView.getCloseButton().addClickListener(click ->
        {
            closeMassEdit();
            updateGrid();
        });
    }


    private void setVisibleChangeSubmenu() {
        MenuItem delete = toolBar.getDeleteItem();
        MenuItem copy = toolBar.getCopyItem();
        toolBar.getMenuBarChanged().getItems().get(0).addClickListener(event ->
        {
            if (toolBar.getMiniField().getValue() > 0) {

                delete.getElement().setAttribute("disabled", false);
                copy.getElement().setAttribute("disabled", false);
            } else {
                delete.getElement().setAttribute("disabled", true);
                copy.getElement().setAttribute("disabled", true);
            }
        });

    }


    private void setSaveButtonEarning() {
        earningForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(earningForm, filesMappedByDB(earningForm.getFilesList())));
            silverButton.greenNotification("Сохранено");
        });
    }


    private void setSaveButtonSpending() {
        spendingForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(spendingForm, filesMappedByDB(spendingForm.getFilesList())));
        });
    }

    private List<FileDto> filesMappedByDB(List<FileDto> files) {

        files = files.stream().map(fileService::createWithResponse
        ).collect(Collectors.toList());
        return files;
    }


    private void setForm(BonusTransactionDto dto, OperationView form) {

        if (dto.getId() == null) {
            form.getIdInput().setValue(0);
            form.getComment().setValue("");
            form.getBonusValueInput().setValue(0);
            form.getExecutionDate().setValue(LocalDate.now());
            form.getCreatedDate().setValue(LocalDate.now());
        } else {
            form.getIdInput().setValue(Math.toIntExact(dto.getId()));
            form.getComment().setValue(dto.getComment());
            form.getBonusValueInput().setValue(Math.toIntExact(dto.getBonusValue()));
            form.getExecutionDate().setValue(dto.getExecutionDate());
            form.getCreatedDate().setValue(dto.getCreated());

        }
        form.getSelectBonusProgramDto().setItems(getBonusPrograms());
        form.getSelectBonusProgramDto().setValue(getBonusPrograms().get(0));
        form.getSelectContractorDto().setItems(getContractors());
        form.getSelectContractorDto().setValue(getContractors().get(0));
        form.getSelectContractorDto().setItemLabelGenerator(ContractorDto::getName);
        form.getSelectBonusProgramDto().setItemLabelGenerator(BonusProgramDto::getName);

    }

    private BonusTransactionDto getDataFromForm(OperationView form, List<FileDto> files) {
        BonusTransactionDto dto = new BonusTransactionDto();
        dto.setId(Long.valueOf(form.getIdInput().getValue()));
        dto.setExecutionDate(form.getExecutionDate().getValue());
        dto.setCreated(form.getCreatedDate().getValue());
        dto.setComment(form.getComment().getValue());
        dto.setBonusValue(Long.valueOf(form.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(form.getSelectBonusProgramDto().getValue());
        dto.setContractorDto(form.getSelectContractorDto().getValue());
        dto.setOwnerDto(employeeService.getPrincipalManually());
        dto.setOwnerChangedDto(employeeService.getPrincipalManually());
        dto.setFilesDto(files);

        if (form.equals(earningForm)) {
            dto.setTransactionType(BonusTransactionDto.TransactionType.EARNING);
        } else {
            dto.setTransactionType(BonusTransactionDto.TransactionType.SPENDING);
        }

        dto.setTransactionStatus(BonusTransactionDto.TransactionStatus.COMPLETED);
        return dto;
    }


    private void setMiniField() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(multiSelectionEvent -> {
            toolBar.getMiniField().setValue(multiSelectionEvent.getAllSelectedItems().size());
        });


    }


    private void setSelectedItems() {
        grid.getPointsGrid().asMultiSelect().addSelectionListener(event -> {
            selectedItems = event.getAllSelectedItems();

        });
    }

    private void setDeleteLogic() {

        setSelectedItems();
        toolBar.getDeleteItem().addClickListener(eventDelete -> {
            for (BonusTransactionDto dto : selectedItems) {
                transactionService.deleteById(dto.getId());
            }
            silverButton.greenNotification("Удалено");
            updateGrid();
        });

    }

    private void setContinueButtonLogic() {

        massEditView.getContinueButton().addClickListener(click -> {
            silverButton.greenNotification("ЗАГЛУШКА Далее");

            //Todo разделить колонку отдел и владелец, иначе не изменить. При сейве updat'ить employee выбранным отделом сверху в окошке
//            EmployeeDto employee = massEditView.getEmployeeBox().getValue();
//            DepartmentDto department = massEditView.getDepartmentBox().getValue();
//            System.out.println(employee);
//            System.out.println(department);
//
//
//            for (BonusTransactionDto dto : new ArrayList<>(selectedItems)) {
//                employee.setDepartment(department);
//                dto.setOwnerDto(employeeService.updateWithResponse(employee));
//                transactionService.update(dto);
//
//            }
            closeMassEdit();
            updateGrid();

        });


    }

    private void setCopyLogic() {
        setSelectedItems();

        toolBar.getCopyItem().addClickListener(eventCopy -> {
            for (BonusTransactionDto dto : selectedItems) {
                //set id 0 чтобы он создал новый при create, иначе с тем же id не создает

                //При копировании записей копируются и файлы к каждой.
                //Лист файлов текущей записи создается в базе, setId0 чтобы новые создались
                //Файлы создаются и возвращаются они же, но с id из базы для того. С id null объект не сетнуть в другой.
                dto.getFilesDto().forEach(f -> {

                    f.setId(0L);
                    listCopiedFiles.add((fileService.createWithResponse(f)));
                });
                dto.setFilesDto(listCopiedFiles);
                dto.setId(0L);
                transactionService.create(dto);
                listCopiedFiles.clear();
            }

            updateGrid();
            silverButton.greenNotification("Скопировано");
        });


    }

    private List<BonusProgramDto> getBonusPrograms() {
        return programService.getAll();
    }

    private List<ContractorDto> getContractors() {
        return contractorService.getAll();
    }

}


