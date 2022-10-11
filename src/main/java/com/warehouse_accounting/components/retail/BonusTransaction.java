package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        setMultiSelect();
        setVisibleChangeSubmenu();
        setSelectItem();
        setCloseButtonCharge();
        setCloseButtonWriteOff();
        setSubMenuCharge();
        setSubMenuWriteOff();
        setRefreshButtonLogic();
        setFormLogic();
        setDeleteLogic();

        add(toolBar, grid, chargeForm, writeOffForm);

    }

    private void setSelectItem() {
        grid.getPointsGrid().addItemDoubleClickListener(event -> {
                    setFormFields(event.getItem());
                    openChargeFormVisible();
                }
        );

    }


    private void setCloseButtonCharge() {
        chargeForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    closeChargeFormVisible();
                    updateGrid();

                }
        );
    }

    private void setCloseButtonWriteOff() {
        writeOffForm.getClosedButton().addClickListener(buttonClickEvent -> {
                    grid.setVisible(true);
                    toolBar.setVisible(true);
                    writeOffForm.setVisible(false);
                    updateGrid();
                }
        );
    }

    private void openChargeFormVisible() {
        grid.setVisible(false);
        toolBar.setVisible(false);
        chargeForm.setVisible(true);
    }

    private void openWriteOffFormVisible() {
        grid.setVisible(false);
        toolBar.setVisible(false);
        writeOffForm.setVisible(true);

    }

    private void closeChargeFormVisible() {
        grid.setVisible(true);
        toolBar.setVisible(true);
        chargeForm.setVisible(false);

    }

    private void closeWriteOffFormVisible() {
        grid.setVisible(false);
        toolBar.setVisible(false);
        chargeForm.setVisible(true);

    }

    private void updateGrid() {
        grid.getPointsGrid().setItems(service.getAll());
    }

    private void setRefreshButtonLogic() {
        toolBar.getRefreshButton().addClickListener(buttonClickEvent -> {
            updateGrid();
        });
    }

    private void setSubMenuCharge() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Начислить",
                menuItemClickEvent -> {
                    setFormFields(new BonusTransactionDto());
                    openChargeFormVisible();
                }
        );
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

    private void setSubMenuWriteOff() {
        toolBar.getMenuBarOperation().getItems().get(0).getSubMenu().addItem("Списать",
                menuItemClickEvent -> {
                    openWriteOffFormVisible();
                }
        );
    }

    private void setFormLogic() {

        chargeForm.getBonusProgram().setItems(getBonusPrograms());
        chargeForm.getBonusProgram().setValue(getBonusPrograms().get(0));
        chargeForm.getContractor().setItems(getContractors());
        chargeForm.getContractor().setValue(getContractors().get(0));
        chargeForm.getContractor().setItemLabelGenerator(ContractorDto::getName);
        chargeForm.getBonusProgram().setItemLabelGenerator(BonusProgramDto::getName);


        chargeForm.getSaveButton().addClickListener(click -> {

            service.create(getDtoForm());

        });


    }


    private void setFormFields(BonusTransactionDto dto) {

        if (dto.getId() == null) {
            chargeForm.getIdInput().setValue(0);
            chargeForm.getComment().setValue("");
            chargeForm.getBonusValueInput().setValue(0);
            chargeForm.getExecutionDate().setValue(LocalDate.now());
            chargeForm.getCreatedDate().setValue(LocalDate.now());
        } else {
            chargeForm.getIdInput().setValue(Math.toIntExact(dto.getId()));
            chargeForm.getComment().setValue(dto.getComment());
            chargeForm.getBonusValueInput().setValue(Math.toIntExact(dto.getBonusValue()));
            chargeForm.getExecutionDate().setValue(dto.getExecutionDate());
            chargeForm.getCreatedDate().setValue(dto.getCreated());

        }
        dto.setBonusProgramDto(chargeForm.getBonusProgram().getValue());
        dto.setContragent(chargeForm.getContractor().getValue());


    }

    private BonusTransactionDto getDtoForm() {
        BonusTransactionDto dto = new BonusTransactionDto();

        dto.setId(Long.valueOf(chargeForm.getIdInput().getValue()));
        dto.setExecutionDate(chargeForm.getExecutionDate().getValue());
        dto.setCreated(chargeForm.getCreatedDate().getValue());
        dto.setComment(chargeForm.getComment().getValue());
        dto.setBonusValue(Long.valueOf(chargeForm.getBonusValueInput().getValue()));
        dto.setBonusProgramDto(chargeForm.getBonusProgram().getValue());
        dto.setContragent(chargeForm.getContractor().getValue());

        System.out.println(dto);

        return dto;
    }

    private void setMultiSelect() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(multiSelectionEvent -> {

            toolBar.getMiniField().setValue(multiSelectionEvent.getAllSelectedItems().size());
        });

    }

    private void setDeleteLogic() {

        grid.getPointsGrid().asMultiSelect().addSelectionListener(event -> {

            toolBar.getDeleteItem().addClickListener(clickDelete -> {

                for (BonusTransactionDto dto : event.getAllSelectedItems()) {
                    service.deleteById(dto.getId());
                }
                updateGrid();
            });


        });


    }

    private List<BonusProgramDto> getBonusPrograms() {
        return programService.getAll();
    }

    private List<ContractorDto> getContractors() {
        return contractorService.getAll();
    }

}


