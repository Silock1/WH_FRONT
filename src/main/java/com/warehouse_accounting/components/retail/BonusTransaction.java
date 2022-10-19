package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.BonusTransactionView;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.FilterForm;
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
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.FileService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
@UIScope
public class BonusTransaction extends VerticalLayout {

    private final BonusTransactionGridLayout grid;
    private final BonusTransactionToolBar toolBar;
    private final BonusTransactionService transactionService;
    private final BonusProgramService programService;
    private final ContractorService contractorService;
    private final FileService fileService;
    private final BonusTransactionView earningForm;
    private final BonusTransactionView spendingForm;
    private final EmployeeService employeeService;

    private final FilterForm filterForm = new FilterForm();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<BonusTransactionDto> selectedItems;
    private SilverButton silverButton = new SilverButton();

    @Autowired
    public BonusTransaction(BonusTransactionGridLayout grid,
                            BonusTransactionToolBar toolBar,
                            BonusTransactionService transactionService,
                            BonusProgramService programService,
                            ContractorService contractorService, FileService fileService, EmployeeService employeeService
    ) {

        this.transactionService = transactionService;
        this.programService = programService;
        this.contractorService = contractorService;
        this.grid = grid;
        this.toolBar = toolBar;
        this.fileService = fileService;
        this.employeeService = employeeService;

        earningForm = new BonusTransactionView(BonusTransactionView.TypeOperation.EARNING, this.fileService, this.employeeService);
        spendingForm = new BonusTransactionView(BonusTransactionView.TypeOperation.SPENDING, this.fileService, this.employeeService);
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
        setFilterButttonLogic();
        setDeleteLogic();
        setCopyLogic();

        add(toolBar, filterForm, grid, earningForm, spendingForm);

    }

    private void clearUpload(BonusTransactionView form) {
       form.getFileUpload().getElement().executeJs("this.files=[]");

    }

    private void setEditLogic() {
        grid.getPointsGrid().addItemDoubleClickListener(event -> {
                    BonusTransactionDto.TransactionType currentType = event.getItem().getTransactionType();
                    if (currentType == BonusTransactionDto.TransactionType.EARNING) {
                        clearUpload(earningForm);
                        setForm(event.getItem(), earningForm);
                        earningForm.setFileList(event.getItem().getId());

                        openForm(earningForm);
                    } else {
                        setForm(event.getItem(), spendingForm);
                        clearUpload(spendingForm);
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

    private void setFilterButttonLogic() {
        toolBar.getFilterButton().addClickListener(event -> {
            filterForm.setVisible(!filterForm.isVisible());
        });
    }

    private void openForm(BonusTransactionView form) {
        grid.setVisible(false);
        toolBar.setVisible(false);
        form.setVisible(true);
    }

    private void closeForm(BonusTransactionView form) {
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
                    clearUpload(earningForm);
                    setForm(new BonusTransactionDto(), earningForm);
                    openForm(earningForm);
                }
        );
    }

    private void setSubMenuMassEdit() {
        toolBar.getMassEdit().addClickListener(event ->
                silverButton.greenNotification("Массовое редактирование"));
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


    private void setSubMenuSpending() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    spendingForm.setFileList(0L);
                    clearUpload(spendingForm);
                    setForm(new BonusTransactionDto(), spendingForm);
                    openForm(spendingForm);
                }
        );
    }

    private void setSaveButtonEarning() {
        earningForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(earningForm, filesMappedFromDB()));

        });
    }

    private List<FileDto> filesMappedFromDB() {
        List<FileDto> files = earningForm.getFilesByIdTrans();
        files = files.stream().map(f ->
                fileService.getById(fileService.createAndGetId(f))
        ).collect(Collectors.toList());
        return files;
    }

    private void setSaveButtonSpending() {
        spendingForm.getSaveButton().addClickListener(click -> {
            transactionService.create(getDataFromForm(spendingForm, filesMappedFromDB()));
        });
    }


    private void setForm(BonusTransactionDto dto, BonusTransactionView form) {
        dto.setBonusProgramDto(form.getBonusProgram().getValue());
        dto.setContractorDto(form.getContractor().getValue());


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

        form.getBonusProgram().setItems(getBonusPrograms());
        form.getBonusProgram().setValue(getBonusPrograms().get(0));
        form.getContractor().setItems(getContractors());
        form.getContractor().setValue(getContractors().get(0));
        form.getContractor().setItemLabelGenerator(ContractorDto::getName);
        form.getBonusProgram().setItemLabelGenerator(BonusProgramDto::getName);
    }

    private BonusTransactionDto getDataFromForm(BonusTransactionView form, List<FileDto> files) {
        BonusTransactionDto dto = new BonusTransactionDto();
        dto.setId(Long.valueOf(form.getIdInput().getValue()));
        dto.setExecutionDate(form.getExecutionDate().getValue());
        dto.setCreated(form.getCreatedDate().getValue());
        dto.setComment(form.getComment().getValue());
        dto.setBonusValue(Long.valueOf(form.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(form.getBonusProgram().getValue());
        dto.setContractorDto(form.getContractor().getValue());
        dto.setOwnerDto(employeeService.getPrincipalManually());
        dto.setOwnerChangedDto(employeeService.getPrincipalManually());
        dto.setFilesDto(files);


//        System.out.println(dto.getFilesDto());
//        System.out.println(form.getFilesByIdTrans());
        // dto.setFilesDto(form.getFilesByIdTrans());

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
            updateGrid();
        });


    }

    private void setCopyLogic() {
        setSelectedItems();

        toolBar.getCopyItem().addClickListener(eventCopy -> {
            for (BonusTransactionDto dto : selectedItems) {
                //set id 0 чтобы он создал новый при create, иначе с тем же id не создает
                dto.setId(0L);
                transactionService.create(dto);
            }
            updateGrid();
        });


    }

    private List<BonusProgramDto> getBonusPrograms() {
        return programService.getAll();
    }

    private List<ContractorDto> getContractors() {
        return contractorService.getAll();
    }

}


