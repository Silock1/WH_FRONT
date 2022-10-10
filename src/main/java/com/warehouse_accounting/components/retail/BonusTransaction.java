package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.retail.forms.bonus_transaction.BonusTransactionForm;
import com.warehouse_accounting.components.retail.grids.BonusTransactionGridLayout;
import com.warehouse_accounting.components.retail.toolbars.BonusTransactionToolBar;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.BonusProgramService;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Операции с баллами (Розница/Операции с баллами)
 **/
@SpringComponent
@Route("bonus_transaction")
@CssImport(value = "./css/application.css")
@UIScope
public class BonusTransaction extends VerticalLayout {

    private BonusTransactionGridLayout grid;
    private BonusTransactionToolBar toolBar;
    private BonusTransactionService service;
    private BonusProgramService programService;
    private ContractorService contractorService;
    private BonusTransactionForm chargeForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.CHARGE);
    private BonusTransactionForm writeOffForm = new BonusTransactionForm(BonusTransactionForm.TypeOperation.WRITE_OFF);


    public BonusTransaction(BonusTransactionGridLayout grid,

                            BonusTransactionToolBar toolBar,

                            BonusTransactionService service,
                            BonusProgramService programService,
                            ContractorService contractorService) {
        this.service = service;
        this.programService = programService;
        this.contractorService = contractorService;
        this.grid = grid;
        this.toolBar = toolBar;

        setFormFields();
        setSizeFull();
        setCloseButtonCharge();
        setCloseButtonWriteOff();
        setSubMenuCharge();
        setSubMenuWriteOff();
        setRefreshButtonLogic();
        setFormLogic();

        add(toolBar, grid, chargeForm, writeOffForm);

    }

    private void setCloseButtonCharge() {
        chargeForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    grid.setVisible(true);
                    toolBar.setVisible(true);
                    chargeForm.setVisible(false);
                }
        );
    }

    private void setCloseButtonWriteOff() {
        writeOffForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    grid.setVisible(true);
                    toolBar.setVisible(true);
                    writeOffForm.setVisible(false);
                }
        );
    }

    private void setRefreshButtonLogic() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            grid.getPointsGrid().setItems(service.getAll());
        });
    }

    private void setSubMenuCharge() {
        toolBar.getMenuBar().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {
                    toolBar.setVisible(false);
                    grid.setVisible(false);
                    chargeForm.setVisible(true);
                }
        );
    }

    private void setSubMenuWriteOff() {
        toolBar.getMenuBar().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    toolBar.setVisible(false);
                    grid.setVisible(false);
                    writeOffForm.setVisible(true);
                }
        );
    }

    private void setFormLogic() {
        chargeForm.getSaveButton().addClickListener(click -> {
            service.create(getDtoForm());

        });

    }


    private void setFormFields() {
        chargeForm.getIdInput().setValue(0);
        chargeForm.getComment().setValue("");
        chargeForm.getBonusValueInput().setValue(0);
        chargeForm.getExecutionDate().setValue(LocalDate.now());
        chargeForm.getCreatedDate().setValue(LocalDate.now());
        chargeForm.getBonusProgram().setItems(getProgramNames());
        chargeForm.getContractor().setItems(getContractorNames());


    }

    private List<String> getProgramNames() {
        List<String> programList = new ArrayList<>();
        for (BonusProgramDto bonus : programService.getAll()) {
            programList.add(bonus.getName());
        }
        return programList;
    }

    private List<String> getContractorNames() {
        List<String> contractorList = new ArrayList<>();
        for (ContractorDto contractor : contractorService.getAll()) {
            contractorList.add(contractor.getName());
        }

        return contractorList;
    }

    private BonusTransactionDto getDtoForm() {
        BonusTransactionDto dto = new BonusTransactionDto();
        dto.setId(Long.valueOf(chargeForm.getIdInput().getValue()));
        dto.setExecutionDate(chargeForm.getExecutionDate().getValue());
        dto.setCreated(chargeForm.getCreatedDate().getValue());
        dto.setComment(chargeForm.getComment().getValue());
        dto.setBonusValue(Long.valueOf(chargeForm.getBonusValueInput().getValue()));


        String contractorName = chargeForm.getContractor().getValue();
        dto.setContragent(contractorService.findByName(contractorService.getAll(), contractorName));

        String programName = chargeForm.getBonusProgram().getValue();
        dto.setBonusProgramDto(programService.findByName(programService.getAll(), programName));

        return dto;
    }


}


